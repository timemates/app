package org.timemates.app.users.usecases

import org.timemates.app.users.repositories.TimersRepository
import io.timemates.sdk.timers.types.TimerSettings
import io.timemates.sdk.timers.types.value.TimerDescription
import io.timemates.sdk.timers.types.value.TimerId
import io.timemates.sdk.timers.types.value.TimerName

class TimerSettingsUseCase(
    private val timers: TimersRepository,
) {
    suspend fun execute(
        timerId: TimerId,
        newName: TimerName?,
        newDescription: TimerDescription?,
        settings: TimerSettings.Patch?,
    ): Result {
        return timers.editTimer(
            timerId = timerId,
            newName = newName,
            newDescription = newDescription,
            settings = settings
        )
            .map { Result.Success }
            .getOrElse { throwable -> Result.Failure(throwable) }
    }

    sealed class Result {
        object Success : Result()

        data class Failure(val exception: Throwable) : Result()
    }
}
