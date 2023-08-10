package io.timemates.app.authorization.ui.initial_authorization.mvi

import io.timemates.app.authorization.ui.initial_authorization.mvi.InitialAuthorizationStateMachine.Effect
import io.timemates.app.authorization.ui.initial_authorization.mvi.InitialAuthorizationStateMachine.Event
import io.timemates.app.foundation.mvi.EmptyState
import io.timemates.app.foundation.mvi.Reducer

class InitialAuthorizationReducer : Reducer<EmptyState, Event, Effect> {
    override fun reduce(
        state: EmptyState,
        event: Event,
        sendEffect: (Effect) -> Unit,
    ): EmptyState {
        return when (event) {
            is Event.OnStartClicked -> {
                sendEffect(Effect.NavigateToStart)
                state
            }
        }
    }
}