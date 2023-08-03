package io.timemates.app.timers.ui.create_timer.mvi

import io.timemates.app.foundation.mvi.Middleware
import io.timemates.app.foundation.mvi.StateStore
import io.timemates.app.timers.ui.create_timer.mvi.CreateTimerStateMachine.State
import io.timemates.app.timers.ui.create_timer.mvi.CreateTimerStateMachine.Effect

class CreateTimerMiddleware : Middleware<State, Effect> {
    override fun onEffect(effect: Effect, store: StateStore<State>): State {
        return when (effect) {
            is Effect.Failure, is Effect.NavigateToTimers -> store.state.value.copy(isLoading = false)

            else -> store.state.value
        }
    }
}