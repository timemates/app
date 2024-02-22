package org.timemates.app.timers.ui.timers_list.mvi

import org.timemates.app.foundation.mvi.Middleware
import org.timemates.app.timers.ui.timers_list.mvi.TimersListScreenComponent.Effect
import org.timemates.app.timers.ui.timers_list.mvi.TimersListScreenComponent.State

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