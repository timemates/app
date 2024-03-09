package org.timemates.app.users.data

import org.timemates.app.users.data.database.CachedUsersQueries
import org.timemates.sdk.common.constructor.createOrNull
import org.timemates.sdk.common.constructor.createOrThrow
import org.timemates.sdk.users.profile.types.Avatar
import org.timemates.sdk.users.profile.types.User
import org.timemates.sdk.users.profile.types.value.EmailAddress
import org.timemates.sdk.users.profile.types.value.UserDescription
import org.timemates.sdk.users.profile.types.value.UserId
import org.timemates.sdk.users.profile.types.value.UserName
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class CachedUsersDataSource(
    private val cachedUsersQueries: CachedUsersQueries,
) {
    suspend fun clear(lastQueryTime: Long) = withContext(Dispatchers.IO) {
        cachedUsersQueries.clear(lastQueryTime)
    }

    suspend fun saveUser(user: User, lastQueryTime: Long) = withContext(Dispatchers.IO) {
        cachedUsersQueries.insert(
            id = user.id.long,
            name = user.name.string,
            description = user.description?.string.orEmpty(),
            gravatarId = (user.avatar as? Avatar.GravatarId)?.string,
            emailAddress = user.emailAddress?.string,
            lastQueryTime = lastQueryTime,
        )
    }

    suspend fun saveUsers(users: List<User>, queryTime: Long) = withContext(Dispatchers.IO) {
        users.forEach { saveUser(it, queryTime) }
    }

    suspend fun isHaveUser(id: UserId): Boolean = withContext(Dispatchers.IO) {
        cachedUsersQueries.get(id.long).executeAsOneOrNull() != null
    }

    suspend fun getUser(id: UserId): User? {
        val user = withContext(Dispatchers.IO) { cachedUsersQueries.get(id.long).executeAsOneOrNull() }
            ?: return null

        val name = UserName.createOrNull(user.name) ?: return null
        val description = UserDescription.createOrNull(user.description) ?: return null
        val avatar = user.gravatarId?.let { Avatar.GravatarId.createOrThrow(it) }
        val emailAddress = user.emailAddress?.let { emailAddress -> EmailAddress.createOrNull(emailAddress) }

        return User(
            id = id,
            name = name,
            description = description,
            avatar = avatar,
            emailAddress = emailAddress,
        )
    }

    suspend fun getUsers(ids: List<UserId>): List<User> = withContext(Dispatchers.IO) {
        ids.mapNotNull { getUser(it) }
    }
}