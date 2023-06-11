package io.timemates.app.core.validation

import org.koin.core.annotation.Singleton

/**
 * A validator for confirmation codes.
 */
@Singleton
class ConfirmationCodeValidator : Validator<String, ConfirmationCodeValidator.Result> {

    companion object {
        const val SIZE = 6
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
