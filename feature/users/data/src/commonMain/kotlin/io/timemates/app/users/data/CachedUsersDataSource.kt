package io.timemates.app.users.data

import io.timemates.app.users.data.database.CachedUsersQueries
import io.timemates.sdk.common.constructor.createOrNull
import io.timemates.sdk.files.types.value.FileId
import io.timemates.sdk.users.profile.types.User
import io.timemates.sdk.users.profile.types.value.EmailAddress
import io.timemates.sdk.users.profile.types.value.UserDescription
import io.timemates.sdk.users.profile.types.value.UserId
import io.timemates.sdk.users.profile.types.value.UserName
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CachedUsersDataSource(
    private val cachedUsersQueries: CachedUsersQueries
) {
    init {
        // TODO: Move it to other place
        CoroutineScope(Dispatchers.Default).launch {
            clear()
        }
    }

    private suspend fun clear() {
        cachedUsersQueries.clear()
    }

    suspend fun saveUser(user: User) {
        cachedUsersQueries.insert(
            id = user.id.long,
            name = user.name.string,
            description = user.description.string,
            avatarFileId = user.avatarFileId.string,
            emailAddress = user.emailAddress?.string,
        )
    }

    suspend fun saveUsers(users: List<User>) {
        users.forEach { saveUser(it) }
    }

    suspend fun isHaveUser(id: UserId): Boolean {
        return cachedUsersQueries.get(id.long).executeAsOneOrNull() != null
    }

    suspend fun getUser(id: UserId): User? {
        val it = cachedUsersQueries.get(id.long).executeAsOneOrNull() ?: return null

        val name = UserName.createOrNull(it.name ?: return null) ?: return null
        val description = UserDescription.createOrNull(it.description ?: return null) ?: return null
        val avatarFileId = FileId.createOrNull(it.avatarFileId ?: return null) ?: return null
        val emailAddress = it.emailAddress?.let { emailAddress -> EmailAddress.createOrNull(emailAddress) }

        return User(
            id = id,
            name = name,
            description = description,
            avatarFileId = avatarFileId,
            emailAddress = emailAddress,
        )
    }

    suspend fun getUsers(ids: List<UserId>): List<User> {
        return ids.mapNotNull { getUser(it) }
    }
}