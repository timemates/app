package org.timemates.app.feature.splash.repositories

interface AuthRepository {
    suspend fun isAuthorized(): Boolean
}