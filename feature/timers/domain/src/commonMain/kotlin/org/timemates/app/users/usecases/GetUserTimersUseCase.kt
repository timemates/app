package org.timemates.app.users.usecases

import org.timemates.app.users.repositories.TimersRepository
import io.timemates.sdk.common.pagination.PagesIterator
import io.timemates.sdk.timers.types.Timer

class GetUserTimersUseCase(
    private val timers: TimersRepository,
) {
    suspend fun execute(): Result {
        return Result.Success(timers.getUserTimers())
    }

    sealed class Result {
        data class Success(val list: PagesIterator<Timer>) : Result()
    }
}