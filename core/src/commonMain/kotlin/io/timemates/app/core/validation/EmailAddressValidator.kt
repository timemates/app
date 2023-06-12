package io.timemates.app.core.validation

/**
 * A validator for email addresses.
 */
class EmailAddressValidator : Validator<String, EmailAddressValidator.Result> {

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

    /**
     * Validates the given email address.
     *
     * @param input The email address to be validated.
     * @return The validation result.
     */
    override fun validate(input: String): Result {
        return when {
            input.length !in SIZE -> Result.SizeViolation
            input.matches(PATTERN) -> Result.PatternDoesNotMatch
            else -> Result.Success
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