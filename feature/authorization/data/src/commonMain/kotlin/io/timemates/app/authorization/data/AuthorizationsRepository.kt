package io.timemates.app.authorization.data

import io.timemates.sdk.authorization.email.EmailAuthorizationApi
import io.timemates.sdk.authorization.email.requests.ConfirmAuthorizationRequest
import io.timemates.sdk.authorization.email.types.value.VerificationHash
import io.timemates.sdk.authorization.sessions.AuthorizedSessionsApi
import io.timemates.sdk.authorization.sessions.types.Authorization
import io.timemates.sdk.authorization.sessions.types.value.ConfirmationCode
import io.timemates.sdk.users.profile.types.value.EmailAddress
import io.timemates.app.authorization.repositories.AuthorizationsRepository as AuthorizationRepositoryContract

class AuthorizationsRepository(
    private val emailAuthApi: EmailAuthorizationApi,
    private val sessionsApi: AuthorizedSessionsApi,
    private val store: AuthorizationStore,
) : AuthorizationRepositoryContract {
    override suspend fun getCurrentAuthorization(): Authorization? {
        TODO("Not yet implemented")
    }

    override suspend fun tryAuthorization(): Result<Authorization> {
        TODO("Not yet implemented")
    }

    override suspend fun authorize(emailAddress: EmailAddress): Result<VerificationHash> {
        return emailAuthApi.authorize(emailAddress)
            // todo save to [AuthorizationStore]
            .onSuccess { }
    }

    override suspend fun confirm(verificationHash: VerificationHash, code: ConfirmationCode): Result<ConfirmAuthorizationRequest.Response> {
        TODO("Not yet implemented")
    }

}