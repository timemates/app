package io.timemates.app.users.repositories

import io.timemates.sdk.common.pagination.PageToken
import io.timemates.sdk.common.pagination.PagesIterator
import io.timemates.sdk.timers.types.Timer
import io.timemates.sdk.timers.types.value.TimerId
import kotlinx.coroutines.flow.Flow

interface TimersRepository {
    suspend fun getUserTimers(pageToken: PageToken? = null): Result<PagesIterator<Timer>>

    suspend fun getTimer(id: TimerId): Result<Timer>

    suspend fun getTimerState(id: TimerId): Flow<Timer>
}