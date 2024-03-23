package org.timemates.app.authorization.dependencies.screens

import com.arkivanov.decompose.ComponentContext
import org.koin.core.annotation.Factory
import org.koin.core.annotation.Module
import org.timemates.app.authorization.ui.initial_authorization.mvi.InitialAuthorizationComponent

@Module
class InitialAuthorizationModule {
    @Factory
    fun mviComponent(componentContext: ComponentContext): InitialAuthorizationComponent {
        return InitialAuthorizationComponent(
            componentContext = componentContext,
        )
    }
}