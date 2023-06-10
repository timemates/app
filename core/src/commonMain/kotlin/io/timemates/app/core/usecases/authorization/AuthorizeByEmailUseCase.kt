package io.timemates.app.core.usecases.authorization

import io.timemates.app.core.repositories.AuthorizationRepository
import io.timemates.sdk.authorization.email.types.value.VerificationHash
import io.timemates.sdk.users.profile.types.value.EmailAddress

class AuthorizeByEmailUseCase(
    private val authorizationRepository: AuthorizationRepository,
) {
    suspend fun execute(emailAddress: EmailAddress): Result<VerificationHash> {
        return authorizationRepository.authorize(emailAddress)
    }
}