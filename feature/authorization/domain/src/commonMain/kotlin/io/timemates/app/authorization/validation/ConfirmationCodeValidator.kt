package io.timemates.app.authorization.validation

import io.timemates.app.foundation.validation.Validator
import io.timemates.app.foundation.validation.unknownValidationFailure
import io.timemates.sdk.authorization.sessions.types.value.ConfirmationCode
import io.timemates.sdk.common.constructor.CreationFailure

/**
 * A validator for confirmation codes.
 */
class ConfirmationCodeValidator : Validator<String, ConfirmationCodeValidator.Result> {
    /**
     * Validates the given confirmation code.
     *
     * @param input The confirmation code to be validated.
     * @return The validation result.
     */
    override fun validate(input: String): Result {
        return ConfirmationCode.create(input)
            .map { Result.Success }
            .getOrElse {
                when (it) {
                    is CreationFailure.SizeExactFailure -> Result.SizeIsInvalid
                    is CreationFailure.PatternFailure -> Result.PatternFailure
                    else -> unknownValidationFailure(it)
                }
            }
    }

    /**
     * The possible validation results for a confirmation code.
     */
    sealed class Result {
        object SizeIsInvalid : Result()

        object PatternFailure : Result()

        object Success : Result()
    }
}
