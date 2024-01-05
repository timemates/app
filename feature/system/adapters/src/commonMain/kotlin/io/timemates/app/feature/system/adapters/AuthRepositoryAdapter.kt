package io.timemates.app.feature.system.adapters

import io.timemates.app.authorization.repositories.AuthorizationsRepository
import io.timemates.app.feature.system.repositories.AuthRepository

class AuthRepositoryAdapter(
    private val authorizationsRepository: AuthorizationsRepository
) : AuthRepository {
    override suspend fun isAuthorized(): Boolean {
        return authorizationsRepository.getCurrentAuthorization() != null
    }
}