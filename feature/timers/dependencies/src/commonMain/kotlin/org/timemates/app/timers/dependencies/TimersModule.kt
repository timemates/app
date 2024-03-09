package org.timemates.app.timers.dependencies

import org.koin.core.annotation.Module
import org.timemates.app.timers.dependencies.screens.TimerCreationModule
import org.timemates.app.timers.dependencies.screens.TimerSettingsModule
import org.timemates.app.timers.dependencies.screens.TimersListModule

@Module(
    includes = [
        TimersDataModule::class,
        // Screen-related
        TimerCreationModule::class,
        TimerSettingsModule::class,
        TimersListModule::class,
    ]
)
class TimersModule