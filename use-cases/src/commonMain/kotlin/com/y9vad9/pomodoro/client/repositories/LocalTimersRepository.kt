package com.y9vad9.pomodoro.client.repositories

import com.y9vad9.pomodoro.client.types.LocalTimer
import com.y9vad9.pomodoro.client.types.value.DateTime
import com.y9vad9.pomodoro.client.types.value.TimerId
import com.y9vad9.pomodoro.client.types.value.TimerName
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

interface LocalTimersRepository {
    suspend fun createTimer(
        name: TimerName,
        settings: LocalTimer.Settings,
        creationTime: DateTime
    )
    suspend fun getTimers(): List<LocalTimer>
    suspend fun setLastTimeUsage(timerId: TimerId, dateTime: DateTime)
    suspend fun setTimerSettings(
        timerId: TimerId,
        patch: LocalTimer.Settings.Patch
    )
    suspend fun removeTimer(timerId: TimerId)

    sealed interface Event {
        @JvmInline
        value class TimerAdded(val timerId: LocalTimer) : Event
        @JvmInline
        value class TimerRemoved(val timerId: TimerId) : Event
    }
}