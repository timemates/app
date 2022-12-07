package com.y9vad9.pomodoro.client.usecases.timers.local

import com.y9vad9.pomodoro.client.repositories.LocalTimersRepository
import com.y9vad9.pomodoro.client.types.value.TimerId

class RemoveLocalTimerUseCase(
    private val localTimersRepository: LocalTimersRepository
) {
    suspend operator fun invoke(timerId: TimerId) =
        localTimersRepository.removeTimer(timerId)
}