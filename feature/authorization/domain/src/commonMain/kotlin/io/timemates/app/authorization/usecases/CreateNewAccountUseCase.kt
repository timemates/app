package io.timemates.app.authorization.usecases

import io.timemates.app.authorization.repositories.AuthorizationsRepository
import io.timemates.sdk.authorization.email.types.value.VerificationHash
import io.timemates.sdk.authorization.sessions.types.Authorization
import io.timemates.sdk.users.profile.types.value.UserDescription
import io.timemates.sdk.users.profile.types.value.UserName

class CreateNewAccountUseCase(
    private val authorizationsRepository: AuthorizationsRepository,
) {
    suspend fun execute(
        verificationHash: VerificationHash,
        name: UserName,
        description: UserDescription,
    ): Result {
        return authorizationsRepository.createNewAccount(verificationHash, name, description)
            .map { success -> Result.Success(success.authorization) }
            .getOrElse { exception -> Result.Failure(exception) }
    }

    sealed class Result {

        data class Failure(val exception: Throwable) : Result()

        data class Success(val authorization: Authorization) : Result()
    }
}