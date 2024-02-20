package org.timemates.app.authorization.ui.afterstart.mvi

import org.timemates.app.authorization.ui.afterstart.mvi.AfterStartStateMachine.Effect
import org.timemates.app.authorization.ui.afterstart.mvi.AfterStartStateMachine.Event
import org.timemates.app.foundation.mvi.EmptyState
import org.timemates.app.foundation.mvi.StateMachine
import org.timemates.app.foundation.mvi.UiEffect
import org.timemates.app.foundation.mvi.UiEvent
import io.timemates.sdk.authorization.email.types.value.VerificationHash

class AfterStartStateMachine(
    reducer: AfterStartReducer,
) : StateMachine<EmptyState, Event, Effect>(
    initState = EmptyState,
    reducer = reducer,
    middlewares = emptyList(),
) {
    sealed class Effect : UiEffect {
        data class NavigateToConfirmation(val verificationHash: VerificationHash) : Effect()

        object OnChangeEmailClicked : Effect()
    }

    sealed class Event : UiEvent {
        object NextClicked : Event()

        object OnChangeEmailClicked : Event()
    }
}