package io.timemates.app.dependencies.authorization

import io.timemates.app.core.repositories.AuthorizationRepository
import org.koin.core.annotation.Module
import org.koin.core.annotation.Singleton

@Module
class AuthorizationDataModule {
    @Singleton
    fun authorizationRepository(): AuthorizationRepository = TODO()
}