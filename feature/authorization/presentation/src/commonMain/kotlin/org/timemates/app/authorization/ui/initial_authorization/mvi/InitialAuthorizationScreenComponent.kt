package org.timemates.app.authorization.ui.initial_authorization.mvi

import androidx.compose.runtime.Immutable
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
import pro.respawn.flowmvi.api.MVIAction
import pro.respawn.flowmvi.api.MVIIntent
import pro.respawn.flowmvi.api.MVIState

class InitialAuthorizationScreenComponent(
    componentContext: ComponentContext,
    reducer: InitialAuthorizationReducer,
) : MVIComponent<State, Event, Effect> by mviComponent(
    componentContext = componentContext,
    componentName = COMPONENT_NAME,
    reducer = reducer,
    initState = State,
) {
    companion object {
        const val COMPONENT_NAME = "InitialAuthorizationScreen"
    }

    @Immutable
    @Serializable
    data object State : UiState, MVIState {
        private fun readResolve(): Any = State
    }

    sealed class Effect : UiEffect, MVIAction {
        data object NavigateToStart : Effect()
    }

    sealed class Event : UiEvent, MVIIntent {
        data object OnStartClicked : Event()
    }
}
