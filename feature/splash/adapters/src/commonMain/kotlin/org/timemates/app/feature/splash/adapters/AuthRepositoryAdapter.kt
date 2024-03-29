package org.timemates.app.feature.splash.adapters

import org.timemates.app.authorization.repositories.AuthorizationsRepository
import org.timemates.app.feature.splash.repositories.AuthRepository

class AuthRepositoryAdapter(
    private val authorizationsRepository: AuthorizationsRepository
) : AuthRepository {
    override suspend fun isAuthorized(): Boolean {
        return authorizationsRepository.getCurrentAuthorization() != null
    }
}