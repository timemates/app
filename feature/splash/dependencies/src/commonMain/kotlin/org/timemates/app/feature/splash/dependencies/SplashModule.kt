package org.timemates.app.feature.splash.dependencies

import org.koin.core.annotation.Module
import org.timemates.app.feature.splash.dependencies.screens.StartupModule

@Module(
    includes = [
        SplashDataModule::class,
        // Screen-related
        StartupModule::class,
    ]
)
class SplashModule