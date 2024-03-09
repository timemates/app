package org.timemates.app.feature.splash.dependencies

import org.timemates.app.authorization.repositories.AuthorizationsRepository
import org.timemates.app.feature.splash.adapters.AuthRepositoryAdapter
import org.timemates.app.feature.splash.repositories.AuthRepository
import org.koin.core.annotation.Module
import org.koin.core.annotation.Singleton

@Module
class SplashDataModule {
    @Singleton
    fun authRepository(
        origin: AuthorizationsRepository,
    ): AuthRepository {
        return AuthRepositoryAdapter(origin)
    }
}