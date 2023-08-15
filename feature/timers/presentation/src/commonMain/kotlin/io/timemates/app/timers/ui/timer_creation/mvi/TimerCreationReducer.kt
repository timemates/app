package io.timemates.app.timers.ui.timer_creation.mvi

import io.timemates.app.foundation.mvi.Reducer
import io.timemates.app.timers.ui.timer_creation.mvi.TimerCreationStateMachine.Effect
import io.timemates.app.timers.ui.timer_creation.mvi.TimerCreationStateMachine.Event
import io.timemates.app.timers.ui.timer_creation.mvi.TimerCreationStateMachine.State
import io.timemates.app.users.usecases.TimerCreationUseCase
import io.timemates.app.users.validation.TimerDescriptionValidator
import io.timemates.app.users.validation.TimerNameValidator
import io.timemates.sdk.common.constructor.createOrThrow
import io.timemates.sdk.timers.types.TimerSettings
import io.timemates.sdk.timers.types.value.TimerDescription
import io.timemates.sdk.timers.types.value.TimerName
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class TimerCreationReducer(
    private val timerCreationUseCase: TimerCreationUseCase,
    private val timerNameValidator: TimerNameValidator,
    private val timerDescriptionValidator: TimerDescriptionValidator,
    private val coroutineScope: CoroutineScope,
) : Reducer<State, Event, Effect> {
    override fun reduce(
        state: State,
        event: Event,
        sendEffect: (Effect) -> Unit,
    ): State {
        return when (event) {
            Event.OnDoneClicked -> {
                val name = when (timerNameValidator.validate(state.name)) {
                    is TimerNameValidator.Result.SizeViolation ->
                        return state.copy(isNameSizeInvalid = true)

                    else -> TimerName.createOrThrow(state.name)
                }

                val description = when (timerDescriptionValidator.validate(state.description)) {
                    is TimerDescriptionValidator.Result.SizeViolation ->
                        return state.copy(isDescriptionSizeInvalid = true)

                    else -> TimerDescription.createOrThrow(state.description)
                }

                createTimer(
                    name = name,
                    description = description,
                    settings = TimerSettings(
                        state.workTime,
                        state.restTime,
                        state.bigRestTime,
                        state.bigRestEnabled,
                        state.bigRestPer,
                        state.isEveryoneCanPause,
                        state.isConfirmationRequired,
                    ),
                    sendEffect = sendEffect,
                )

                return state.copy(isLoading = true)
            }

            is Event.NameIsChanged  ->
                state.copy(name = event.name, isNameSizeInvalid = false)

            is Event.DescriptionIsChanged ->
                state.copy(description = event.description, isDescriptionSizeInvalid = false)

            is Event.WorkTimeIsChanged ->
                state.copy(workTime = event.workTime)

            is Event.RestTimeIsChanged ->
                state.copy(workTime = event.restTime)

            is Event.BigRestModeIsChanged ->
                state.copy(bigRestEnabled = event.bigRestEnabled)

            is Event.BigRestPerIsChanged ->
                state.copy(bigRestPer = event.bigRestPer)

            is Event.BigRestTimeIsChanged ->
                state.copy(bigRestTime = event.bigRestTime)

            is Event.TimerPauseControlAccessIsChanged ->
                state.copy(isEveryoneCanPause = event.isEveryoneCanPause)

            is Event.ConfirmationRequirementChanged ->
                state.copy(isConfirmationRequired = event.isConfirmationRequired)
        }
    }

    private fun createTimer(
        name: TimerName,
        description: TimerDescription,
        settings: TimerSettings,
        sendEffect: (Effect) -> Unit,
    ) {
        coroutineScope.launch {
            when (val result = timerCreationUseCase.execute(name, description, settings)) {
                is TimerCreationUseCase.Result.Failure ->
                    sendEffect(Effect.Failure(result.exception))

                is TimerCreationUseCase.Result.Success -> {
                    sendEffect(Effect.NavigateToTimersScreen)
                }
            }
        }
    }
}
