package io.timemates.app.timers.ui.create_timer.mvi

import io.timemates.app.foundation.mvi.Reducer
import io.timemates.app.timers.ui.create_timer.mvi.CreateTimerStateMachine.State
import io.timemates.app.timers.ui.create_timer.mvi.CreateTimerStateMachine.Event
import io.timemates.app.timers.ui.create_timer.mvi.CreateTimerStateMachine.Effect
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class CreateTimerReducer(
    private val coroutineScope: CoroutineScope,
): Reducer<State, Event, Effect> {
    override fun reduce(state: State, event: Event, sendEffect: (Effect) -> Unit): State {
        return when (event) {
            is Event.NameChange -> state.copy(
                name = event.name,
            )

            is Event.DescriptionChange -> state.copy(
                description = event.description
            )

            is Event.WorkTimeChange -> state.copy(
                workTime = event.workTime
            )

            is Event.RestTimeChange -> state.copy(
                restTime = event.restTime
            )
            // todo change logic
            Event.OnCreateClick -> state.copy()
        }
    }

    private fun createTimer() {
        coroutineScope.launch {

        }
    }
}