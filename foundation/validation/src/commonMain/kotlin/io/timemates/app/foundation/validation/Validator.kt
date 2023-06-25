package io.timemates.app.foundation.validation

/**
 * A generic interface for validators.
 *
 * @param TInput The type of input to be validated.
 * @param TResult The type of the validation result.
 */
interface Validator<TInput, TResult> {

    /**
     * Validates the given input and returns the validation result.
     *
     * @param input The input to be validated.
     * @return The result of the validation.
     */
    fun validate(input: TInput): TResult
}

/**
 * Throws [IllegalStateException] with message that contains name of
 * the type recognized from [failure].
 *
 * Should be used in cases when some kind of throwable cannot be correctly
 * interpreted by validator.
 */
fun unknownValidationFailure(failure: Throwable): Nothing =
    error("Unknown failure type ${failure::class.qualifiedName}")