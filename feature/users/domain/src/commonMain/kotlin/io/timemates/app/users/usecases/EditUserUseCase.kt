package io.timemates.app.users.usecases

import io.timemates.app.users.repositories.AuthorizationsRepository
import io.timemates.app.users.repositories.UsersRepository
import io.timemates.sdk.users.profile.types.value.UserDescription
import io.timemates.sdk.users.profile.types.value.UserName

/**
 * Use case class for editing a user's information.
 *
 * @param repository The repository for user operations.
 * @param authorizations The adapter for authorization operations.
 */
class EditUserUseCase(
    private val repository: UsersRepository,
    private val authorizations: AuthorizationsRepository,
) {
    /**
     * Executes the use case to edit a user's information.
     *
     * @param name The new name for the user.
     * @param description The new description for the user.
     * @return The [Result] of the operation.
     */
    suspend fun execute(name: UserName?, description: UserDescription?): Result {
        return repository.editUser(name, description)
            .map { Result.Success }
            // usually, should always return positive result, except of when authorization
            // is missing or is not actual
            .getOrElse { exception -> Result.Failure(exception) }
    }

    /**
     * Sealed class representing the result of the use case.
     */
    sealed class Result {
        /**
         * Represents a successful result.
         */
        object Success : Result()

        /**
         * Represents a failure result with the associated throwable.
         *
         * @param throwable The throwable indicating the failure.
         */
        data class Failure(val throwable: Throwable) : Result()
    }
}
