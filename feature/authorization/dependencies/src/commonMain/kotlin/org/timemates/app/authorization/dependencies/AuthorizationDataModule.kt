package org.timemates.app.authorization.dependencies

import app.cash.sqldelight.db.SqlDriver
import org.timemates.app.authorization.data.DatabaseAccessHashProvider
import org.timemates.app.authorization.data.DbAuthorizationMapper
import org.timemates.app.authorization.repositories.AuthorizationsRepository
import org.timemates.credentials.CredentialsStorage
import io.timemates.data.database.TimeMatesAuthorizations
import org.timemates.sdk.authorization.email.EmailAuthorizationApi
import org.timemates.sdk.authorization.sessions.AuthorizedSessionsApi
import org.timemates.sdk.common.engine.TimeMatesRequestsEngine
import org.timemates.sdk.common.providers.AccessHashProvider
import org.koin.core.annotation.Factory
import org.koin.core.annotation.Module
import org.koin.core.annotation.Named
import org.timemates.app.authorization.data.AuthorizationsRepository as AuthorizationsRepositoryImpl

@Module
class AuthorizationDataModule {

    @Factory
    fun accountsDatabase(@Named("authorization") sqlDriver: SqlDriver): TimeMatesAuthorizations {
        return TimeMatesAuthorizations(sqlDriver)
    }

    @Factory
    fun accessHashProvider(
        dbAuthorizations: TimeMatesAuthorizations,
        credentialsStorage: CredentialsStorage,
    ): AccessHashProvider {
        return DatabaseAccessHashProvider(dbAuthorizations.accountDatabaseQueries, credentialsStorage)
    }

    @Factory
    fun authorizationRepository(
        requestsEngine: TimeMatesRequestsEngine,
        accessHashProvider: AccessHashProvider,
        dbAuthorizations: TimeMatesAuthorizations,
        credentialsStorage: CredentialsStorage,
    ): AuthorizationsRepository {
        return AuthorizationsRepositoryImpl(
            emailAuthApi = EmailAuthorizationApi(requestsEngine),
            sessionsApi = AuthorizedSessionsApi(requestsEngine, accessHashProvider),
            localQueries = dbAuthorizations.accountDatabaseQueries,
            mapper = DbAuthorizationMapper(credentialsStorage),
            credentialsStorage = credentialsStorage,
        )
    }
}