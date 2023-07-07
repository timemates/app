package io.timemates.app.authorization.usecases

import io.timemates.app.authorization.repositories.AuthorizationsRepository
import io.timemates.sdk.authorization.email.types.value.VerificationHash
import io.timemates.sdk.authorization.sessions.types.Authorization
import io.timemates.sdk.authorization.sessions.types.value.ConfirmationCode
import io.timemates.sdk.common.exceptions.TooManyRequestsException

class ConfirmEmailAuthorizationUseCase(
    private val authorizations: AuthorizationsRepository
) {
    suspend fun execute(
        verificationHash: VerificationHash,
        code: ConfirmationCode
    ): Result {
        return authorizations.confirm(verificationHash, code)
            .map { result -> Result.Success(result.authorization, result.isNewAccount) }
            .getOrElse { exception ->
                when (exception) {
                    is TooManyRequestsException -> Result.AttemptsExceeded
                    else -> Result.Failure(exception)
                }
            }
    }

    sealed class Result {
        data class Success(
            val authorization: Authorization?,
            val isNewAccount: Boolean,
        ) : Result()

        object AttemptsExceeded : Result()

        data class Failure(val exception: Throwable) : Result()
    }
}