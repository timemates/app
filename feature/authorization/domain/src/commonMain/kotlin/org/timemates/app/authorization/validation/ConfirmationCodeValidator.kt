package org.timemates.app.authorization.validation

import io.timemates.sdk.authorization.sessions.types.value.ConfirmationCode
import io.timemates.sdk.common.constructor.CreationFailure
import org.timemates.app.foundation.validation.Validator
import org.timemates.app.foundation.validation.unknownValidationFailure

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
                    is CreationFailure.SizeExactFailure, is CreationFailure.BlankValueFailure ->
                        Result.SizeIsInvalid
                    is CreationFailure.PatternFailure -> Result.PatternFailure
                    else -> unknownValidationFailure(it)
                }
            }
    }

    /**
     * The possible validation results for a confirmation code.
     */
    sealed class Result {
        data object SizeIsInvalid : Result()

        data object PatternFailure : Result()

        data object Success : Result()
    }
}
