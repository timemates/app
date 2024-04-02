package org.timemates.app.users.repositories

import app.cash.paging.PagingData
import kotlinx.coroutines.flow.Flow
import org.timemates.remote.value.list.LazyListContainer
import org.timemates.sdk.common.pagination.PageToken
import org.timemates.sdk.common.types.Empty
import org.timemates.sdk.timers.types.Timer
import org.timemates.sdk.timers.types.TimerSettings
import org.timemates.sdk.timers.types.value.TimerDescription
import org.timemates.sdk.timers.types.value.TimerId
import org.timemates.sdk.timers.types.value.TimerName

interface TimersRepository {
    fun getUserTimers(pageToken: PageToken? = null): Flow<PagingData<Timer>>

    suspend fun getTimer(id: TimerId): Flow<Timer>

    suspend fun getTimerState(id: TimerId): Result<Flow<Timer.State>>

    suspend fun createTimer(
        name: TimerName,
        description: TimerDescription,
        settings: TimerSettings,
    ): Result<TimerId>

    suspend fun removeTimer(timerId: TimerId): Result<Empty>

    suspend fun editTimer(
        timerId: TimerId,
        newName: TimerName? = null,
        newDescription: TimerDescription? = null,
        settings: TimerSettings.Patch? = null,
    ): Result<Empty>
}