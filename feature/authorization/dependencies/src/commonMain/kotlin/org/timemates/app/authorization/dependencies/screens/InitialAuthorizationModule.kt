package org.timemates.app.authorization.dependencies.screens

import org.timemates.app.authorization.ui.initial_authorization.mvi.InitialAuthorizationReducer
import org.timemates.app.authorization.ui.initial_authorization.mvi.InitialAuthorizationStateMachine
import org.koin.core.annotation.Factory
import org.koin.core.annotation.Module

@Module
class InitialAuthorizationModule {
    @Factory
    fun stateMachine(): InitialAuthorizationStateMachine {
        return InitialAuthorizationStateMachine(
            reducer = InitialAuthorizationReducer()
        )
    }
}