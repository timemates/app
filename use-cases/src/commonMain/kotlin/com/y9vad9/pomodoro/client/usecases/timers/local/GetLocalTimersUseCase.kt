package com.y9vad9.pomodoro.client.usecases.timers.local

import com.y9vad9.pomodoro.client.repositories.LocalTimersRepository

class GetLocalTimersUseCase(
    private val localTimersRepository: LocalTimersRepository
) {
    suspend operator fun invoke() =
        localTimersRepository.getTimers()
}