package io.timemates.app.core.usecases.authorization.validators

import io.timemates.sdk.users.profile.types.value.EmailAddress

class ValidateEmailUseCase {

    companion object {
        val SIZE: IntRange = 5..200
        val PATTERN = Regex(
            "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" +
                "\\@" +
                "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +
                "(" +
                "\\." +
                "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" +
                ")+"
        )
    }

    fun validate(email: String): Result {
        return when {
            email.length !in SIZE -> Result.SizeViolation
            email.matches(PATTERN) -> Result.PatternDoesNotMatch
            else -> Result.Success
        }
    }

    sealed class Result {
        object Success : Result()

        object SizeViolation : Result()

        object PatternDoesNotMatch : Result()
    }
}