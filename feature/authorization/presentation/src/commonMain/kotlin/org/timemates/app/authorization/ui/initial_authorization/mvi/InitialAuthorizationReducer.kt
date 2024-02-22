package org.timemates.app.authorization.ui.initial_authorization.mvi

import org.timemates.app.authorization.ui.initial_authorization.mvi.InitialAuthorizationScreenComponent.Effect
import org.timemates.app.authorization.ui.initial_authorization.mvi.InitialAuthorizationScreenComponent.Event
import org.timemates.app.authorization.ui.initial_authorization.mvi.InitialAuthorizationScreenComponent.State
import org.timemates.app.foundation.mvi.Reducer
import org.timemates.app.foundation.mvi.ReducerScope

class InitialAuthorizationReducer : Reducer<State, Event, Effect> {
    override fun ReducerScope<Effect>.reduce(state: State, event: Event): State {
        return when (event) {
            is Event.OnStartClicked -> {
                sendEffect(Effect.NavigateToStart)
                state
            }
        }
    }
}