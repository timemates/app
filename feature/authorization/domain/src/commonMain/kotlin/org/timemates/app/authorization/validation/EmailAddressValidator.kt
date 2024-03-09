package org.timemates.app.authorization.validation

import org.timemates.app.foundation.validation.Validator
import org.timemates.app.foundation.validation.unknownValidationFailure
import org.timemates.sdk.common.constructor.CreationFailure
import org.timemates.sdk.users.profile.types.value.EmailAddress

/**
 * A validator for email addresses.
 */
class EmailAddressValidator : Validator<String, EmailAddressValidator.Result> {

    /**
     * Validates the given email address.
     *
     * @param input The email address to be validated.
     * @return The validation result.
     */
    override fun validate(input: String): Result {
        return EmailAddress.create(input)
            .map { Result.Success }
            .getOrElse {
                when (it) {
                    is CreationFailure.SizeRangeFailure -> Result.SizeViolation
                    is CreationFailure.PatternFailure -> Result.PatternDoesNotMatch
                    else -> unknownValidationFailure(it)
                }
            }
    }

    /**
     * The possible validation results for an email address.
     */
    sealed class Result {
        object Success : Result()

        object SizeViolation : Result()

        object PatternDoesNotMatch : Result()
    }
}