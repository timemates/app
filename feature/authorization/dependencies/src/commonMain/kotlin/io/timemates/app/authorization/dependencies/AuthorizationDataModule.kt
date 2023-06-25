package io.timemates.app.authorization.dependencies

import io.timemates.app.authorization.repositories.AuthorizationsRepository
import io.timemates.sdk.authorization.email.requests.ConfirmAuthorizationRequest
import io.timemates.sdk.authorization.email.types.value.VerificationHash
import io.timemates.sdk.authorization.sessions.types.Authorization
import io.timemates.sdk.authorization.sessions.types.value.ConfirmationCode
import io.timemates.sdk.users.profile.types.value.EmailAddress
import org.koin.core.annotation.Module
import org.koin.core.annotation.Singleton

@Module
class AuthorizationDataModule {
    @Singleton
    fun authorizationRepository(): AuthorizationsRepository {
        return object : AuthorizationsRepository {
            override suspend fun getCurrentAuthorization(): Authorization? {
                TODO("Not yet implemented")
            }

            override suspend fun tryAuthorization(): Result<Authorization> {
                TODO("Not yet implemented")
            }

            override suspend fun authorize(emailAddress: EmailAddress): Result<VerificationHash> {
                TODO("Not yet implemented")
            }

            override suspend fun confirm(verificationHash: VerificationHash, code: ConfirmationCode): Result<ConfirmAuthorizationRequest.Response> {
                TODO("Not yet implemented")
            }
        }
    }
}