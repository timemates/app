package org.timemates.app.authorization.ui.initial_authorization.mvi

import org.timemates.app.authorization.ui.initial_authorization.mvi.InitialAuthorizationStateMachine.Effect
import org.timemates.app.authorization.ui.initial_authorization.mvi.InitialAuthorizationStateMachine.Event
import org.timemates.app.foundation.mvi.EmptyState
import org.timemates.app.foundation.mvi.Reducer
import org.timemates.app.foundation.mvi.ReducerScope

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