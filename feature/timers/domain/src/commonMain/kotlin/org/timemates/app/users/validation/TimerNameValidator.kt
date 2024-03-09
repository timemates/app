package org.timemates.app.users.validation

import org.timemates.app.foundation.validation.Validator
import org.timemates.app.foundation.validation.unknownValidationFailure
import org.timemates.app.users.validation.TimerNameValidator.Result
import org.timemates.sdk.common.constructor.CreationFailure
import org.timemates.sdk.timers.types.value.TimerName

class TimerNameValidator : Validator<String, Result> {

    override fun validate(input: String): Result {
        return TimerName.create(input)
            .map { Result.Success }
            .getOrElse { throwable ->
                when (throwable) {
                    is CreationFailure.SizeRangeFailure -> Result.SizeViolation
                    else -> unknownValidationFailure(throwable)
                }
            }
    }

    sealed class Result {
        object Success : Result()

        object SizeViolation : Result()
    }
}