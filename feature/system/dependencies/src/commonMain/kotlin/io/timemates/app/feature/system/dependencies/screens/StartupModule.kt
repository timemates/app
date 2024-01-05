package io.timemates.app.feature.system.dependencies.screens

import io.timemates.app.feature.common.startup.mvi.StartupStateMachine
import io.timemates.app.feature.system.dependencies.SystemDataModule
import io.timemates.app.feature.system.repositories.AuthRepository
import org.koin.core.annotation.Factory
import org.koin.core.annotation.Module

@Module(includes = [SystemDataModule::class])
class StartupModule {
    @Factory
    fun stateMachine(authRepository: AuthRepository): StartupStateMachine {
        return StartupStateMachine(authRepository)
    }
}