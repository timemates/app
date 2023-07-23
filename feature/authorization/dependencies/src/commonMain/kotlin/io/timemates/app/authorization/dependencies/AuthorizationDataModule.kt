package io.timemates.app.authorization.dependencies

import app.cash.sqldelight.db.SqlDriver
import io.timemates.app.authorization.data.DatabaseAccessHashProvider
import io.timemates.app.authorization.data.DbAuthorizationMapper
import io.timemates.app.authorization.data.database.AccountDatabaseQueries
import io.timemates.app.authorization.repositories.AuthorizationsRepository
import io.timemates.data.database.TimeMatesAuthorizations
import io.timemates.sdk.authorization.email.EmailAuthorizationApi
import io.timemates.sdk.authorization.sessions.AuthorizedSessionsApi
import io.timemates.sdk.common.engine.TimeMatesRequestsEngine
import io.timemates.sdk.common.providers.AccessHashProvider
import org.koin.core.annotation.Factory
import org.koin.core.annotation.Module
import org.koin.core.annotation.Named
import org.koin.core.annotation.Singleton
import io.timemates.app.authorization.data.AuthorizationsRepository as AuthorizationsRepositoryImpl

@Module
class AuthorizationDataModule {

    @Factory
    fun accountsDatabase(@Named("authorization") sqlDriver: SqlDriver): TimeMatesAuthorizations {
        return TimeMatesAuthorizations(sqlDriver)
    }

    @Factory
    fun accessHashProvider(dbAuthorizations: TimeMatesAuthorizations): AccessHashProvider {
        return DatabaseAccessHashProvider(dbAuthorizations.accountDatabaseQueries)
    }

    @Factory
    fun authorizationRepository(
        requestsEngine: TimeMatesRequestsEngine,
        accessHashProvider: AccessHashProvider,
        dbAuthorizations: TimeMatesAuthorizations,
    ): AuthorizationsRepository {
        return AuthorizationsRepositoryImpl(
            emailAuthApi = EmailAuthorizationApi(requestsEngine),
            sessionsApi = AuthorizedSessionsApi(requestsEngine, accessHashProvider),
            localQueries = dbAuthorizations.accountDatabaseQueries,
            mapper = DbAuthorizationMapper(),
        )
    }
}