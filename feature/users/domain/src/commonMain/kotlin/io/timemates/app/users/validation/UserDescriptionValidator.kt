package io.timemates.app.users.validation

import io.timemates.app.foundation.validation.Validator
import io.timemates.app.foundation.validation.unknownValidationFailure
import io.timemates.app.users.validation.UserDescriptionValidator.Result
import io.timemates.sdk.common.constructor.CreationFailure
import io.timemates.sdk.users.profile.types.value.UserDescription

class UserDescriptionValidator : Validator<String, Result> {
    override fun validate(input: String): Result {
        return UserDescription.create(input)
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