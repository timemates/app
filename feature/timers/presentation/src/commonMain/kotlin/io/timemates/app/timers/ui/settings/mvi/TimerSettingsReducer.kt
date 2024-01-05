package io.timemates.app.timers.ui.settings.mvi

import io.timemates.app.foundation.mvi.Reducer
import io.timemates.app.foundation.mvi.ReducerScope
import io.timemates.app.timers.ui.settings.mvi.TimerSettingsStateMachine.Effect
import io.timemates.app.timers.ui.settings.mvi.TimerSettingsStateMachine.Event
import io.timemates.app.timers.ui.settings.mvi.TimerSettingsStateMachine.State
import io.timemates.app.users.usecases.TimerSettingsUseCase
import io.timemates.app.users.validation.TimerDescriptionValidator
import io.timemates.app.users.validation.TimerNameValidator
import io.timemates.sdk.common.constructor.createOrThrow
import io.timemates.sdk.timers.types.TimerSettings
import io.timemates.sdk.timers.types.value.TimerDescription
import io.timemates.sdk.timers.types.value.TimerId
import io.timemates.sdk.timers.types.value.TimerName
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch


class TimerSettingsReducer(
    private val timerId: TimerId,
    private val timerSettingsUseCase: TimerSettingsUseCase,
    private val timerNameValidator: TimerNameValidator,
    private val timerDescriptionValidator: TimerDescriptionValidator,
) : Reducer<State, Event, Effect> {
    override fun ReducerScope<Effect>.reduce(state: State, event: Event): State {
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

                editTimer(
                    timerId = timerId,
                    newName = name,
                    newDescription = description,
                    settings = TimerSettings.Patch(
                        workTime = state.workTime,
                        restTime = state.restTime,
                        bigRestEnabled = state.bigRestEnabled,
                        bigRestPer = state.bigRestPer,
                        bigRestTime = state.bigRestTime,
                        isEveryoneCanPause = state.isEveryoneCanPause,
                        isConfirmationRequired = state.isConfirmationRequired,
                    ),
                    sendEffect = sendEffect,
                    coroutineScope = machineScope,
                )

                return state.copy(isLoading = true)
            }


            is Event.NameIsChanged ->
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

    private fun editTimer(
        timerId: TimerId,
        newName: TimerName,
        newDescription: TimerDescription,
        settings: TimerSettings.Patch?,
        sendEffect: (Effect) -> Unit,
        coroutineScope: CoroutineScope,
    ) {
        coroutineScope.launch {
            when (val result = timerSettingsUseCase.execute(timerId, newName, newDescription, settings)) {
                is TimerSettingsUseCase.Result.Failure ->
                    sendEffect(Effect.Failure(result.exception))

                is TimerSettingsUseCase.Result.Success ->
                    sendEffect(Effect.Success)
            }
        }
    }
}
