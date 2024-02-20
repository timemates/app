package org.timemates.app.authorization.usecases

import org.timemates.app.authorization.repositories.AuthorizationsRepository
import io.timemates.sdk.authorization.email.types.value.VerificationHash
import io.timemates.sdk.common.exceptions.TooManyRequestsException
import io.timemates.sdk.users.profile.types.value.EmailAddress

class AuthorizeByEmailUseCase(
    private val authorizationsRepository: AuthorizationsRepository,
) {
    suspend fun execute(emailAddress: EmailAddress): Result {
        return authorizationsRepository.authorize(emailAddress)
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