package org.timemates.app.users.data

import org.timemates.sdk.common.types.Empty
import org.timemates.sdk.users.UserApi
import org.timemates.sdk.users.profile.types.User
import org.timemates.sdk.users.profile.types.value.UserDescription
import org.timemates.sdk.users.profile.types.value.UserId
import org.timemates.sdk.users.profile.types.value.UserName
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import org.timemates.app.foundation.time.TimeProvider
import org.timemates.app.users.repositories.UsersRepository as UserRepositoryContract

class UsersRepository(
    private val userApi: UserApi,
    private val cachedUsersDataSource: CachedUsersDataSource,
    private val timeProvider: TimeProvider,
    coroutineScope: CoroutineScope,
) : UserRepositoryContract {

    init {
        coroutineScope.launch {
            cachedUsersDataSource.clear(timeProvider.provide().toEpochMilliseconds())
        }
    }

    override suspend fun getUser(id: UserId): Flow<Result<User>> = flow {
        // Emit local saved data (if have)
        if (cachedUsersDataSource.isHaveUser(id))
            cachedUsersDataSource.getUser(id)?.let { emit(Result.success(it)) }

        // Load actual data
        userApi.profile.getProfiles(listOf(id))
            .map { users ->
                if (users.isEmpty())
                    Result.success(users.first())
                else
                    Result.success(users.first())
            }
            .getOrElse { throwable -> Result.failure(throwable) }
            .let {
                emit(it)
                it.onSuccess { user -> cachedUsersDataSource.saveUser(user, timeProvider.provide().toEpochMilliseconds()) }
            }
    }

    override suspend fun getUsers(ids: List<UserId>): Flow<Result<List<User>>> = flow {
        // Emit local saved data (if have)
        val cachedUsers = cachedUsersDataSource.getUsers(ids)
        if (cachedUsers.isNotEmpty())
            emit(Result.success(cachedUsers))

        // Load actual data
        userApi.profile.getProfiles(ids)
            .map { users ->
                if (users.isEmpty())
                    Result.success(users)
                else
                    Result.success(users)
            }
            .getOrElse { throwable -> Result.failure(throwable) }
            .let {
                emit(it)
                it.onSuccess { users -> cachedUsersDataSource.saveUsers(users, timeProvider.provide().toEpochMilliseconds()) }
            }
    }

    override suspend fun editUser(name: UserName?, description: UserDescription?): Result<Empty> {
        return userApi.profile.editProfile(name, description)
    }
}