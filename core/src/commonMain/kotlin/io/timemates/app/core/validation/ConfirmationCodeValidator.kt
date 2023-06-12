package io.timemates.app.core.validation

import io.timemates.sdk.authorization.sessions.types.value.ConfirmationCode

/**
 * A validator for confirmation codes.
 */
class ConfirmationCodeValidator : Validator<String, ConfirmationCodeValidator.Result> {

    companion object {
        const val SIZE = ConfirmationCode.SIZE
    }

    /**
     * Validates the given confirmation code.
     *
     * @param input The confirmation code to be validated.
     * @return The validation result.
     */
    override fun validate(input: String): Result {
        return when {
            input.length != SIZE -> Result.SizeIsInvalid
            else -> Result.Success
        }
    }

    /**
     * The possible validation results for a confirmation code.
     */
    sealed class Result {
        object SizeIsInvalid : Result()

        object Success : Result()
    }
}
