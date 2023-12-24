package io.timemates.app.timers.ui.timer_creation.mvi

import io.timemates.app.foundation.mvi.Middleware
import io.timemates.app.foundation.mvi.StateStore
import io.timemates.app.timers.ui.timer_creation.mvi.TimerCreationStateMachine.Effect
import io.timemates.app.timers.ui.timer_creation.mvi.TimerCreationStateMachine.State

class TimerCreationMiddleware : Middleware<State, Effect> {
    override fun onEffect(effect: Effect, store: StateStore<State>): State {
        return when (effect) {
            is Effect.Failure ->
                store.state.value.copy(isLoading = false)

            else -> store.state.value
        }
    }
}