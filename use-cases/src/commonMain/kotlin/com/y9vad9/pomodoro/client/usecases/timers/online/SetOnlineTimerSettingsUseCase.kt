package com.y9vad9.pomodoro.client.usecases.timers.online

import com.y9vad9.pomodoro.client.exceptions.UnauthorizedException
import com.y9vad9.pomodoro.client.repositories.AuthorizationRepository
import com.y9vad9.pomodoro.client.repositories.OnlineTimersRepository
import com.y9vad9.pomodoro.client.types.OnlineTimer
import com.y9vad9.pomodoro.client.types.value.TimerId

class SetOnlineTimerSettingsUseCase(
    private val onlineTimersRepository: OnlineTimersRepository,
    private val authorizationRepository: AuthorizationRepository
) {
    suspend operator fun invoke(
        timerId: TimerId, settings: OnlineTimer.Settings.Patch
    ): Result<Unit> = runCatching {
        if(authorizationRepository.isAuthorizationPresent())
            throw UnauthorizedException()

        onlineTimersRepository.setTimerSettings(timerId, settings)
    }
}