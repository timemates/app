package io.timemates.app.timers.ui.timers_list.mvi

import io.timemates.app.foundation.mvi.Middleware
import io.timemates.app.foundation.mvi.StateStore
import io.timemates.app.timers.ui.timers_list.mvi.TimersListStateMachine.State
import io.timemates.app.timers.ui.timers_list.mvi.TimersListStateMachine.Effect

class TimersListMiddleware : Middleware<State, Effect> {
    override fun onEffect(effect: Effect, store: StateStore<State>): State {
        return when(effect) {
            is Effect.Failure -> {
                store.state.value.copy(isLoading = false, isError = true)
            }

            is Effect.NoMoreTimers -> {
                store.state.value.copy(hasMoreItems = false, isLoading = false)
            }

            is Effect.LoadTimers -> {
                store.state.value.copy(timersList = store.state.value.timersList + effect.timersList, isLoading = false)
            }
        }
    }
}