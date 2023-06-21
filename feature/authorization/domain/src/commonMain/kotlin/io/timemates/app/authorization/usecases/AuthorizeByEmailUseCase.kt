package io.timemates.app.authorization.usecases

import io.timemates.app.authorization.repositories.AuthorizationRepository
import io.timemates.sdk.authorization.email.types.value.VerificationHash
import io.timemates.sdk.common.exceptions.TooManyRequestsException
import io.timemates.sdk.users.profile.types.value.EmailAddress

class AuthorizeByEmailUseCase(
    private val authorizationRepository: AuthorizationRepository,
) {
    suspend fun execute(emailAddress: EmailAddress): Result {
        return authorizationRepository.authorize(emailAddress)
            .map { Result.Success(it) }
            .getOrElse { throwable ->
                when (throwable) {
                    is TooManyRequestsException -> Result.TooManyRequests
                    else -> Result.Failure(throwable)
                }
            }
    }

    sealed class Result {
        data class Success(val verificationHash: VerificationHash) : Result()

        object TooManyRequests : Result()

        data class Failure(val throwable: Throwable) : Result()
    }
}