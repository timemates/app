package org.timemates.app.users.repositories

import org.timemates.sdk.users.profile.types.value.UserId

interface AuthorizationsRepository {
    /**
     * Gets current authorized user id.
     */
    suspend fun getMe(): Result<UserId>

    /**
     * Logs out of current account.
     */
    suspend fun logout()
}