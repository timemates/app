package com.y9vad9.pomodoro.client.repositories

import com.y9vad9.pomodoro.client.exceptions.NoAccessException
import com.y9vad9.pomodoro.client.exceptions.UnauthorizedException
import com.y9vad9.pomodoro.client.types.OnlineTimer
import com.y9vad9.pomodoro.client.types.value.Count
import com.y9vad9.pomodoro.client.types.value.TimerId
import com.y9vad9.pomodoro.client.types.value.TimerName
import kotlinx.coroutines.flow.Flow
import kotlin.jvm.Throws

interface OnlineTimersRepository {
    /**
     * Gets user's timers.
     *
     * @return [List]
     */
    @Throws(UnauthorizedException::class)
    suspend fun getTimers(
        from: TimerId?, count: Count
    ): List<OnlineTimer>

    @Throws(UnauthorizedException::class)
    suspend fun createTimer(
        name: TimerName,
        settings: OnlineTimer.Settings
    ): Boolean

    /**
     * Changes timer settings with [id].
     */
    @Throws(UnauthorizedException::class, NoAccessException::class)
    suspend fun setTimerSettings(
        id: TimerId,
        patch: OnlineTimer.Settings.Patch
    )

    /**
     * Removes timer with [id].
     */
    @Throws(UnauthorizedException::class, NoAccessException::class)
    suspend fun removeTimer(id: TimerId)
}