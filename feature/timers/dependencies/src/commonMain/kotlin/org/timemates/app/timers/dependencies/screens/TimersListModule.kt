package org.timemates.app.timers.dependencies.screens

import com.arkivanov.decompose.ComponentContext
import org.koin.core.annotation.Factory
import org.koin.core.annotation.Module
import org.timemates.app.timers.dependencies.TimersDataModule
import org.timemates.app.timers.ui.timers_list.mvi.TimersListScreenComponent
import org.timemates.app.users.repositories.TimersRepository

@Module(includes = [TimersDataModule::class])
class TimersListModule {

    @Factory
    fun mvi(
        componentContext: ComponentContext,
        timersRepository: TimersRepository,
    ): TimersListScreenComponent {
        return TimersListScreenComponent(
            componentContext = componentContext,
            timersRepository = timersRepository,
        )
    }
}