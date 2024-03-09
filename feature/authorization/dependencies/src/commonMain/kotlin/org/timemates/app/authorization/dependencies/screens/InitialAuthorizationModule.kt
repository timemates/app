package org.timemates.app.authorization.dependencies.screens

import com.arkivanov.decompose.ComponentContext
import org.koin.core.annotation.Factory
import org.koin.core.annotation.Module
import org.timemates.app.authorization.ui.initial_authorization.mvi.InitialAuthorizationReducer
import org.timemates.app.authorization.ui.initial_authorization.mvi.InitialAuthorizationScreenComponent

@Module
class InitialAuthorizationModule {
    @Factory
    fun mviComponent(componentContext: ComponentContext): InitialAuthorizationScreenComponent {
        return InitialAuthorizationScreenComponent(
            componentContext = componentContext,
            reducer = InitialAuthorizationReducer(),
        )
    }
}