package org.timemates.app.authorization.ui.initial_authorization.mvi

import com.arkivanov.decompose.ComponentContext
import kotlinx.serialization.Serializable
import org.timemates.app.authorization.ui.initial_authorization.mvi.InitialAuthorizationScreenComponent.Effect
import org.timemates.app.authorization.ui.initial_authorization.mvi.InitialAuthorizationScreenComponent.Event
import org.timemates.app.authorization.ui.initial_authorization.mvi.InitialAuthorizationScreenComponent.State
import org.timemates.app.foundation.mvi.MVIComponent
import org.timemates.app.foundation.mvi.UiEffect
import org.timemates.app.foundation.mvi.UiEvent
import org.timemates.app.foundation.mvi.UiState
import org.timemates.app.foundation.mvi.mviComponent

class InitialAuthorizationScreenComponent(
    componentContext: ComponentContext,
    reducer: InitialAuthorizationReducer,
) : MVIComponent<State, Event, Effect> by mviComponent(
    componentContext = componentContext,
    componentName = "InitialAuthorizationScreen",
    reducer = reducer,
    initState = State,
) {
    @Serializable
    data object State : UiState {
        private fun readResolve(): Any = State
    }

    sealed class Effect : UiEffect {
        data object NavigateToStart : Effect()
    }

    sealed class Event : UiEvent {
        data object OnStartClicked : Event()
    }
}
