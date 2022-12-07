package com.y9vad9.pomodoro.client.repositories

import com.y9vad9.pomodoro.client.types.OnlineTimer
import com.y9vad9.pomodoro.client.types.value.DateTime
import com.y9vad9.pomodoro.client.types.value.TimerId
import kotlinx.coroutines.flow.Flow

interface OnlineSessionsRepository {
    suspend fun startTimer(timerId: TimerId)
    suspend fun updatesOf(timerId: TimerId): Flow<TimerUpdate>
    suspend fun stopTimer(timerId: TimerId)

    sealed interface TimerUpdate {
        object Confirmation : TimerUpdate

        @JvmInline
        value class NewSettings(
            val patch: OnlineTimer.Settings.Patch
        ) : TimerUpdate

        class TimerStarted(val endsAt: DateTime) : TimerUpdate
        class TimerStopped(val startsAt: DateTime?) : TimerUpdate
        object SessionFailed : TimerUpdate
        object SessionFinished : TimerUpdate
    }
}