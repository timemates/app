package io.timemates.app.authorization.data

import io.timemates.app.authorization.data.database.AccountDatabaseQueries
import io.timemates.sdk.authorization.email.EmailAuthorizationApi
import io.timemates.sdk.authorization.email.requests.ConfirmAuthorizationRequest
import io.timemates.sdk.authorization.email.types.value.VerificationHash
import io.timemates.sdk.authorization.sessions.AuthorizedSessionsApi
import io.timemates.sdk.authorization.sessions.types.Authorization
import io.timemates.sdk.authorization.sessions.types.value.ConfirmationCode
import io.timemates.sdk.common.exceptions.UnsupportedException
import io.timemates.sdk.users.profile.types.value.EmailAddress
import kotlin.concurrent.thread
import io.timemates.app.authorization.repositories.AuthorizationsRepository as AuthorizationRepositoryContract

class AuthorizationsRepository(
    private val emailAuthApi: EmailAuthorizationApi,
    private val sessionsApi: AuthorizedSessionsApi,
    private val localQueries: AccountDatabaseQueries,
    private val mapper: DbAuthorizationMapper,
) : AuthorizationRepositoryContract {
    override suspend fun getCurrentAuthorization(): Authorization? {
        return localQueries.getCurrent().executeAsOneOrNull()
            ?.let { mapper.dbToSdkAuthorization(it) }
    }

    override suspend fun tryAuthorization(): Result<Authorization> {
        // TODO
        return Result.failure(UnsupportedException("Try authorization is not supported on server yet."))
    }

    override suspend fun authorize(emailAddress: EmailAddress): Result<VerificationHash> {
        return emailAuthApi.authorize(emailAddress)
    }

    override suspend fun confirm(verificationHash: VerificationHash, code: ConfirmationCode): Result<ConfirmAuthorizationRequest.Response> {
        return emailAuthApi.confirm(verificationHash, code)
    }
}