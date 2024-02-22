package org.timemates.app.authorization.ui.afterstart.mvi

import com.arkivanov.decompose.ComponentContext
import io.timemates.sdk.authorization.email.types.value.VerificationHash
import kotlinx.serialization.Serializable
import org.timemates.app.authorization.ui.afterstart.mvi.AfterStartScreenComponent.Effect
import org.timemates.app.authorization.ui.afterstart.mvi.AfterStartScreenComponent.Event
import org.timemates.app.authorization.ui.afterstart.mvi.AfterStartScreenComponent.State
import org.timemates.app.foundation.mvi.MVIComponent
import org.timemates.app.foundation.mvi.UiEffect
import org.timemates.app.foundation.mvi.UiEvent
import org.timemates.app.foundation.mvi.UiState
import org.timemates.app.foundation.mvi.mviComponent

class AfterStartScreenComponent(
    componentContext: ComponentContext,
    reducer: AfterStartReducer,
) : MVIComponent<State, Event, Effect> by mviComponent(
    componentName = "AfterStartComponent",
    componentContext = componentContext,
    initState = State,
    reducer = reducer,
    middlewares = emptyList(),
) {
    @Serializable
    data object State : UiState {
        private fun readResolve(): Any = State

    }

    sealed class Effect : UiEffect {
        data class NavigateToConfirmation(val verificationHash: VerificationHash) : Effect()

        data object OnChangeEmailClicked : Effect()
    }

    sealed class Event : UiEvent {
        data object NextClicked : Event()

        data object OnChangeEmailClicked : Event()
    }
}