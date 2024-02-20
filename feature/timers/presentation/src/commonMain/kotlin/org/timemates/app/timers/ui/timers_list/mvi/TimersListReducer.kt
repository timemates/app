package org.timemates.app.timers.ui.timers_list.mvi

import org.timemates.app.foundation.mvi.Reducer
import org.timemates.app.foundation.mvi.ReducerScope
import org.timemates.app.timers.ui.timers_list.mvi.TimersListStateMachine.Effect
import org.timemates.app.timers.ui.timers_list.mvi.TimersListStateMachine.Event
import org.timemates.app.timers.ui.timers_list.mvi.TimersListStateMachine.State
import org.timemates.app.users.usecases.GetUserTimersUseCase
import io.timemates.sdk.common.pagination.PagesIterator
import io.timemates.sdk.timers.types.Timer
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class TimersListReducer (
    private val getUserTimersUseCase: GetUserTimersUseCase,
) : Reducer<State, Event, Effect> {
    private var pagesIterator: PagesIterator<Timer>? = null

    override fun ReducerScope<Effect>.reduce(state: State, event: Event): State {
        when(event) {
            is Event.Load -> {
                getUserTimers(sendEffect, machineScope)
                return state.copy(isLoading = pagesIterator == null || pagesIterator?.hasNext() == true)
            }
        }
    }

    private fun getUserTimers(
        sendEffect: (Effect) -> Unit,
        scope: CoroutineScope,
    ) {
        scope.launch {
            if(pagesIterator == null) {
                when (val result = getUserTimersUseCase.execute()) {
                    is GetUserTimersUseCase.Result.Success -> {
                        this@TimersListReducer.pagesIterator = result.list
                    }
                }
            }

            val pagesIterator = pagesIterator!!

            if (pagesIterator.hasNext()) {
                val currentResult = pagesIterator.next()
                currentResult
                    .onSuccess {
                        sendEffect(Effect.LoadTimers(currentResult.getOrThrow()))
                    }.onFailure {
                        sendEffect(Effect.Failure(currentResult.exceptionOrNull()!!))
                    }
            } else {
                sendEffect(Effect.NoMoreTimers)
            }
        }
    }
}
