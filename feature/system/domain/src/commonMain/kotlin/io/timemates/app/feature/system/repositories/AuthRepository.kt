package io.timemates.app.feature.system.repositories

interface AuthRepository {
    suspend fun isAuthorized(): Boolean
}