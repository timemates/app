package io.timemates.app.timers.ui.timers_list.mvi

import io.timemates.app.foundation.mvi.Reducer
import io.timemates.app.timers.ui.timers_list.mvi.TimersListStateMachine.Effect
import io.timemates.app.timers.ui.timers_list.mvi.TimersListStateMachine.Event
import io.timemates.app.timers.ui.timers_list.mvi.TimersListStateMachine.State
import io.timemates.app.users.usecases.GetUserTimersUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class TimersListReducer (
    private val getUserTimersUseCase: GetUserTimersUseCase,
    private val coroutineScope: CoroutineScope,
) : Reducer<State, Event, Effect> {

    private val iteratorResult = coroutineScope.async(start = CoroutineStart.LAZY) { getUserTimersUseCase.execute() }

    override fun reduce(
        state: State,
        event: Event,
        sendEffect: (Effect) -> Unit,
    ): State {
        when(event) {
            is Event.Load -> {
                getUserTimers(sendEffect)
                return state.copy(isLoading = true)
            }
        }
    }

    private fun getUserTimers(
        sendEffect: (Effect) -> Unit,
    ) {
        coroutineScope.launch {
            when (val result = iteratorResult.await()) {
                is GetUserTimersUseCase.Result.Success -> {
                    if (result.list.hasNext()) {
                        val currentResult = result.list.next()
                        currentResult
                            .onSuccess {
                                sendEffect(Effect.LoadTimers(currentResult.getOrThrow()))
                            }.onFailure {
                                sendEffect(Effect.Failure(currentResult.exceptionOrNull()!!))
                            }
                    }
                    if (!result.list.hasNext()) {
                        sendEffect(Effect.NoMoreTimers)
                    }
                }
            }
        }
    }
}
