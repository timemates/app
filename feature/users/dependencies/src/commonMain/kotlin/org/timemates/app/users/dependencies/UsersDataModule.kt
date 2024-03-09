package org.timemates.app.users.dependencies

import app.cash.sqldelight.db.SqlDriver
import org.timemates.app.foundation.time.TimeProvider
import org.timemates.app.users.data.CachedUsersDataSource
import org.timemates.app.users.data.UsersRepository
import org.timemates.app.users.data.database.TimeMatesUsers
import org.timemates.sdk.common.engine.TimeMatesRequestsEngine
import org.timemates.sdk.common.providers.AccessHashProvider
import org.timemates.sdk.users.UserApi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import org.koin.core.annotation.Factory
import org.koin.core.annotation.Module
import org.koin.core.annotation.Named

@Module
class UsersDataModule {
    @Factory
    fun usersDatabase(@Named("users") sqlDriver: SqlDriver): TimeMatesUsers {
        TimeMatesUsers.Schema.create(sqlDriver)
        return TimeMatesUsers(sqlDriver)
    }

    @Factory
    fun usersApi(
        grpcEngine: TimeMatesRequestsEngine,
        tokenProvider: AccessHashProvider,
    ): UserApi = UserApi(grpcEngine, tokenProvider)

    @Factory
    fun cachedUsersDataSource(usersDb: TimeMatesUsers): CachedUsersDataSource {
        return CachedUsersDataSource(usersDb.cachedUsersQueries)
    }

    @Factory
    fun usersRepository(
        userApi: UserApi,
        cachedUsersDataSource: CachedUsersDataSource,
        timeProvider: TimeProvider,
    ): UsersRepository {
        return UsersRepository(
            userApi = userApi,
            cachedUsersDataSource = cachedUsersDataSource,
            timeProvider = timeProvider,
            CoroutineScope(Dispatchers.IO),
        )
    }
}