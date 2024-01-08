package io.timemates.app.timers.ui.timers_list.mvi

import io.timemates.app.foundation.mvi.Middleware
import io.timemates.app.foundation.mvi.StateStore
import io.timemates.app.timers.ui.timers_list.mvi.TimersListStateMachine.State
import io.timemates.app.timers.ui.timers_list.mvi.TimersListStateMachine.Effect

class TimersListMiddleware : Middleware<State, Effect> {
    override fun onEffect(effect: Effect, state: State): State {
        return when(effect) {
            is Effect.Failure -> {
                state.copy(isLoading = false, isError = true)
            }

            is Effect.NoMoreTimers -> {
                state.copy(hasMoreItems = false, isLoading = false)
            }

            is Effect.LoadTimers -> {
                state.copy(timersList = (state.timersList + effect.timersList).distinct())
            }
        }
    }
}