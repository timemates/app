package com.y9vad9.pomodoro.client.usecases.timers.local

import com.y9vad9.pomodoro.client.repositories.LocalTimersRepository
import com.y9vad9.pomodoro.client.types.LocalTimer
import com.y9vad9.pomodoro.client.types.value.TimerName

class CreateLocalTimerUseCase(
    private val localTimersRepository: LocalTimersRepository
) {
    suspend operator fun invoke(
        name: TimerName,
        settings: LocalTimer.Settings
    ) = localTimersRepository.createTimer(name, settings)
}