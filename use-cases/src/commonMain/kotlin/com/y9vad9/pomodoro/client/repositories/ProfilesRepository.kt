package com.y9vad9.pomodoro.client.repositories

import com.y9vad9.pomodoro.client.exceptions.UnauthorizedException
import com.y9vad9.pomodoro.client.types.Profile
import com.y9vad9.pomodoro.client.types.value.AvatarUrl
import com.y9vad9.pomodoro.client.types.value.UserId
import kotlinx.coroutines.flow.Flow
import java.io.ByteArrayInputStream

interface ProfilesRepository {
    /**
     * Gets current authorized user profile.
     *
     * First collected profile is from cache, then actual one.
     * @throws UnauthorizedException if user isn't authorized.
     * @return [Profile] or `null` if user isn't authorized.
     */
    @Throws(UnauthorizedException::class)
    suspend fun getMe(): Flow<Profile>

    /**
     * Marks is current user authorized.
     */
    suspend fun isAuthorized(): Boolean

    /**
     * Sets new profile picture for current authorized user.
     * @param stream stream of bytes with avatar.
     *
     * @return new [AvatarUrl] or `null` if uploading is failed.
     */
    @Throws(UnauthorizedException::class)
    suspend fun setProfilePicture(stream: ByteArrayInputStream): AvatarUrl?

    /**
     * Gets user profiles by [ids].
     *
     * First collected profiles is from cache, then actual profiles.
     * List may not contain some users if they are not exist (or not cached).
     */
    suspend fun getProfiles(
        ids: Collection<UserId>
    ): Flow<Collection<Profile>>
}