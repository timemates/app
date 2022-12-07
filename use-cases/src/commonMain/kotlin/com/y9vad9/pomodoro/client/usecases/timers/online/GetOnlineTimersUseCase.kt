package com.y9vad9.pomodoro.client.usecases.timers.online

import com.y9vad9.pomodoro.client.exceptions.UnauthorizedException
import com.y9vad9.pomodoro.client.repositories.AuthorizationRepository
import com.y9vad9.pomodoro.client.repositories.OnlineTimersRepository
import com.y9vad9.pomodoro.client.types.OnlineTimer
import com.y9vad9.pomodoro.client.types.value.Count
import com.y9vad9.pomodoro.client.types.value.TimerId

class GetOnlineTimersUseCase(
    private val onlineTimersRepository: OnlineTimersRepository,
    private val authorizationRepository: AuthorizationRepository
) {
    suspend operator fun invoke(
        from: TimerId?, count: Count
    ): Result<List<OnlineTimer>> = runCatching {
        if(authorizationRepository.isAuthorizationPresent())
            throw UnauthorizedException()

        onlineTimersRepository.getTimers(from, count)
    }
}