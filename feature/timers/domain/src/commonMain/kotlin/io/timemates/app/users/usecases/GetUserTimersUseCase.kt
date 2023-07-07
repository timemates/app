package io.timemates.app.users.usecases

import io.timemates.app.users.repositories.TimersRepository
import io.timemates.sdk.common.pagination.PagesIterator
import io.timemates.sdk.timers.types.Timer
import io.timemates.sdk.timers.types.value.TimerId

class GetUserTimersUseCase(
    private val timers: TimersRepository,
) {
    suspend fun execute(timerId: TimerId): Result {
        return timers.getUserTimers()
            .map { pages -> Result.Success(pages) }
            .getOrElse { Result.Failure(it) }
    }

    sealed class Result {
        data class Success(val list: PagesIterator<Timer>) : Result()

        data class Failure(val exception: Throwable) : Result()
    }
}