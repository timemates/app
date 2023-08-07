package io.timemates.app.users.usecases

import io.timemates.app.users.repositories.TimersRepository
import io.timemates.sdk.common.pagination.PagesIterator
import io.timemates.sdk.common.pagination.map
import io.timemates.sdk.timers.types.Timer
import io.timemates.sdk.timers.types.value.TimerId

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