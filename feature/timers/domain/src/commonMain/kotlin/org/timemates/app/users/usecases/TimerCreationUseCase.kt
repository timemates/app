package org.timemates.app.users.usecases

import org.timemates.app.users.repositories.TimersRepository
import io.timemates.sdk.timers.types.TimerSettings
import io.timemates.sdk.timers.types.value.TimerDescription
import io.timemates.sdk.timers.types.value.TimerName

class TimerCreationUseCase(
    private val timersRepository: TimersRepository,
) {
    suspend fun execute(
        name: TimerName,
        description: TimerDescription,
        settings: TimerSettings,
    ): Result {
        return timersRepository.createTimer(name, description, settings)
            .map { Result.Success }
            .getOrElse { exception -> Result.Failure(exception) }
    }

    sealed class Result {
        data class Failure(val exception: Throwable) : Result()

        data object Success : Result()
    }
}
