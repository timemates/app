package com.y9vad9.pomodoro.client.repositories

import com.y9vad9.pomodoro.client.types.value.DateTime
import com.y9vad9.pomodoro.client.types.value.TimerId
import kotlinx.coroutines.flow.Flow

interface LocalSessionsRepository {
    suspend fun startSession(timerId: TimerId)
    suspend fun updatesOf(timerId: TimerId): Flow<TimerUpdate>
    suspend fun sendUpdate(timerId: TimerId, update: TimerUpdate)
    suspend fun stopSession(timerId: TimerId)

    suspend fun createConfirmation(timerId: TimerId)
    suspend fun isConfirmationAvailable(timerId: TimerId): Boolean
    suspend fun confirm(timerId: TimerId)

    sealed interface TimerUpdate {
        object Confirmation : TimerUpdate
        class TimerStarted(val endsAt: DateTime) : TimerUpdate
        class TimerStopped(val startsAt: DateTime?) : TimerUpdate
        object TimerFinished : TimerUpdate
    }
}