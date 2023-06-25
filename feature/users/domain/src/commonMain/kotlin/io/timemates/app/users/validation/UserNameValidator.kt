package io.timemates.app.users.validation

import io.timemates.app.foundation.validation.Validator
import io.timemates.app.foundation.validation.unknownValidationFailure
import io.timemates.app.users.validation.UserNameValidator.Result
import io.timemates.sdk.common.constructor.CreationFailure
import io.timemates.sdk.users.profile.types.value.UserName

class UserNameValidator : Validator<String, Result> {
    override fun validate(input: String): Result {
        return UserName.create(input)
            .map { Result.Success }
            .getOrElse { throwable ->
                when (throwable) {
                    is CreationFailure.SizeRangeFailure ->
                        Result.SizeViolation

                    else -> unknownValidationFailure(throwable)
                }
            }
    }

    sealed class Result {
        object Success : Result()

        object SizeViolation : Result()
    }
}