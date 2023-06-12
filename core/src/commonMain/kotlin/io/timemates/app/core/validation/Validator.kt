package io.timemates.app.core.validation

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