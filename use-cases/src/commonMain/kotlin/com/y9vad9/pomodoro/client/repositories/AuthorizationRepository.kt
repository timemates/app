package com.y9vad9.pomodoro.client.repositories

interface AuthorizationRepository {
    /**
     * Says is authorization done on client side. It doesn't check
     * its actuality on server.
     */
    suspend fun isAuthorizationPresent(): Boolean

    suspend fun renewAuthorization(): Boolean
    suspend fun removeAuthorization()
}