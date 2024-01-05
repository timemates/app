package io.timemates.app.authorization.ui.initial_authorization.mvi

import io.timemates.app.authorization.ui.initial_authorization.mvi.InitialAuthorizationStateMachine.Effect
import io.timemates.app.authorization.ui.initial_authorization.mvi.InitialAuthorizationStateMachine.Event
import io.timemates.app.foundation.mvi.EmptyState
import io.timemates.app.foundation.mvi.StateMachine
import io.timemates.app.foundation.mvi.UiEffect
import io.timemates.app.foundation.mvi.UiEvent

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
