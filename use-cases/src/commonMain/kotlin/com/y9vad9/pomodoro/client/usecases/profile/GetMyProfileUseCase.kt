package com.y9vad9.pomodoro.client.usecases.profile

import com.y9vad9.pomodoro.client.repositories.ProfilesRepository
import com.y9vad9.pomodoro.client.types.Profile
import kotlinx.coroutines.flow.Flow

class GetMyProfileUseCase(
    private val profilesRepository: ProfilesRepository
) {
    suspend operator fun invoke(): Result<Flow<Profile>> = runCatching {
        profilesRepository.getMe()
    }
}