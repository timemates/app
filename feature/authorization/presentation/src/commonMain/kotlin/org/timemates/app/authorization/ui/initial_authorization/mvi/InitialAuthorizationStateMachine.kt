package org.timemates.app.authorization.ui.initial_authorization.mvi

import org.timemates.app.authorization.ui.initial_authorization.mvi.InitialAuthorizationStateMachine.Effect
import org.timemates.app.authorization.ui.initial_authorization.mvi.InitialAuthorizationStateMachine.Event
import org.timemates.app.foundation.mvi.EmptyState
import org.timemates.app.foundation.mvi.StateMachine
import org.timemates.app.foundation.mvi.UiEffect
import org.timemates.app.foundation.mvi.UiEvent

class InitialAuthorizationStateMachine(
    reducer: InitialAuthorizationReducer,
) : StateMachine<EmptyState, Event, Effect>(
    initState = EmptyState,
    reducer = reducer,
    middlewares = emptyList(),
) {
   sealed class Effect : UiEffect {
        object NavigateToStart : Effect()
    }

    sealed class Event : UiEvent {
        object OnStartClicked : Event()
    }
}
