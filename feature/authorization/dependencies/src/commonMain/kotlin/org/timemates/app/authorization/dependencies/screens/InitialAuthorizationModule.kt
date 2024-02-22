package org.timemates.app.authorization.dependencies.screens

import org.koin.core.annotation.Factory
import org.koin.core.annotation.Module
import org.timemates.app.authorization.ui.initial_authorization.mvi.InitialAuthorizationReducer
import org.timemates.app.authorization.ui.initial_authorization.mvi.InitialAuthorizationScreenComponent

@Module
class InitialAuthorizationModule {
    @Factory
    fun mviComponent(componentComponent: InitialAuthorizationScreenComponent): InitialAuthorizationScreenComponent {
        return InitialAuthorizationScreenComponent(
            componentContext = componentComponent,
            reducer = InitialAuthorizationReducer(),
        )
    }
}