package org.timemates.app.feature.system.dependencies

import org.timemates.app.authorization.repositories.AuthorizationsRepository
import org.timemates.app.feature.system.adapters.AuthRepositoryAdapter
import org.timemates.app.feature.system.repositories.AuthRepository
import org.koin.core.annotation.Module
import org.koin.core.annotation.Singleton

@Module
class SystemDataModule {
    @Singleton
    fun authRepository(
        origin: AuthorizationsRepository,
    ): AuthRepository {
        return AuthRepositoryAdapter(origin)
    }
}