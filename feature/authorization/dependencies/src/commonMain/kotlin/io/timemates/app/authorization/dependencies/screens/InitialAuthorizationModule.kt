package io.timemates.app.authorization.dependencies.screens

import io.timemates.app.authorization.ui.initial_authorization.mvi.InitialAuthorizationReducer
import io.timemates.app.authorization.ui.initial_authorization.mvi.InitialAuthorizationStateMachine
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