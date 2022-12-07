package com.y9vad9.pomodoro.client.usecases.profile

import com.y9vad9.pomodoro.client.repositories.ProfilesRepository
import com.y9vad9.pomodoro.client.types.value.UserId

class GetProfilesUseCase(
    private val profilesRepository: ProfilesRepository
) {
    suspend operator fun invoke(ids: Collection<UserId>) = runCatching {
        profilesRepository.getProfiles(ids)
    }
}