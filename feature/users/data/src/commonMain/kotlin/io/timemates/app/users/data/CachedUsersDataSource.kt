package io.timemates.app.users.data

import io.timemates.app.users.data.database.CachedUsersQueries
import io.timemates.sdk.common.constructor.createOrNull
import io.timemates.sdk.common.constructor.createOrThrow
import io.timemates.sdk.files.types.value.FileId
import io.timemates.sdk.users.profile.types.Avatar
import io.timemates.sdk.users.profile.types.User
import io.timemates.sdk.users.profile.types.value.EmailAddress
import io.timemates.sdk.users.profile.types.value.UserDescription
import io.timemates.sdk.users.profile.types.value.UserId
import io.timemates.sdk.users.profile.types.value.UserName
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
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
            description = user.description.string,
            avatarFileId = (user.avatar as? Avatar.FileId)?.string,
            gravatarId = (user.avatar as? Avatar.FileId)?.string,
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
        val user = withContext(Dispatchers.IO) { cachedUsersQueries.get(id.long).executeAsOneOrNull() } ?: return null

        val name = UserName.createOrNull(user.name) ?: return null
        val description = UserDescription.createOrNull(user.description) ?: return null
        val avatar = user.avatarFileId?.let { Avatar.FileId.createOrThrow(it) }
            ?: user.gravatarId?.let { Avatar.GravatarId.createOrThrow(it) }
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