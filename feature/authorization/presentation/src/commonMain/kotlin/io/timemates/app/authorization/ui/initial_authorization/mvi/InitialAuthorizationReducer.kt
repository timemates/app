package io.timemates.app.authorization.ui.initial_authorization.mvi

import io.timemates.app.authorization.ui.initial_authorization.mvi.InitialAuthorizationStateMachine.Effect
import io.timemates.app.authorization.ui.initial_authorization.mvi.InitialAuthorizationStateMachine.Event
import io.timemates.app.foundation.mvi.EmptyState
import io.timemates.app.foundation.mvi.Reducer
import io.timemates.app.foundation.mvi.ReducerScope

class InitialAuthorizationReducer : Reducer<EmptyState, Event, Effect> {
    override fun ReducerScope<Effect>.reduce(state: EmptyState, event: Event): EmptyState {
        return when (event) {
            is Event.OnStartClicked -> {
                sendEffect(Effect.NavigateToStart)
                state
            }
        }
    }
}