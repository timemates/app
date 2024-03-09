package org.timemates.app.users.usecases

import org.timemates.app.users.repositories.TimersRepository
import org.timemates.sdk.timers.types.Timer
import org.timemates.sdk.timers.types.value.TimerId

class GetTimerUseCase(
    private val timers: TimersRepository,
) {
    suspend fun execute(timerId: TimerId): Result {
        return timers.getTimer(timerId)
            .map { timer -> Result.Success(timer) }
            .getOrElse { throwable -> Result.Failure(throwable) }
    }

    sealed class Result {
        data class Success(val timer: Timer) : Result()

        object NotFound : Result()

        data class Failure(val exception: Throwable) : Result()
    }
}