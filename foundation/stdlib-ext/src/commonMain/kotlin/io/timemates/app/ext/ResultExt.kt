package io.timemates.app.ext

/**
 * Flat maps the result using the provided transformation function.
 *
 * @param transform The transformation function to apply to the result value.
 * @return A new Result object after applying the transformation function.
 */
inline fun <T, R> Result<T>.flatMap(transform: (T) -> Result<R>): Result<R> {
    return map { transform(it) }.flatten()
}

/**
 * Flattens a Result of Result to a Result of the inner value.
 *
 * @return A new Result object containing the inner value if successful, or an error otherwise.
 */
fun <T> Result<Result<T>>.flatten(): Result<T> {
    return when (val value = getOrNull()) {
        null -> Result.failure(exceptionOrThrow())
        else -> {
            if (value.isSuccess) {
                Result.success(value.getOrThrow())
            } else {
                Result.failure(value.exceptionOrThrow())
            }
        }
    }
}

/**
 * Retrieves the exception from the Result or throws an error if the Result is not at failure state.
 *
 * @return The exception associated with the Result, or throws an error if the Result is not at failure state.
 */
private fun <T> Result<T>.exceptionOrThrow(): Throwable =
    exceptionOrNull() ?: error("Result is not at failure state.")