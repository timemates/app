package org.timemates.app.feature.splash.dependencies.screens

import com.arkivanov.decompose.ComponentContext
import org.koin.core.annotation.Factory
import org.koin.core.annotation.Module
import org.timemates.app.feature.common.startup.mvi.StartupScreenMVIComponent
import org.timemates.app.feature.splash.dependencies.SplashDataModule
import org.timemates.app.feature.splash.repositories.AuthRepository

@Module(includes = [SplashDataModule::class])
class StartupModule {
    @Factory
    fun mvi(componentContext: ComponentContext, authRepository: AuthRepository): StartupScreenMVIComponent {
        return StartupScreenMVIComponent(componentContext, authRepository)
    }
}