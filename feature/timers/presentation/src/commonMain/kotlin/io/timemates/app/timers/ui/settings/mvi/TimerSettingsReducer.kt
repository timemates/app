package io.timemates.app.timers.ui.settings.mvi

import io.timemates.app.foundation.mvi.Reducer
import io.timemates.app.timers.ui.settings.mvi.TimerSettingsStateMachine.Effect
import io.timemates.app.timers.ui.settings.mvi.TimerSettingsStateMachine.Event
import io.timemates.app.timers.ui.settings.mvi.TimerSettingsStateMachine.State
import io.timemates.app.users.usecases.EditTimerUseCase
import io.timemates.app.users.validation.TimerDescriptionValidator
import io.timemates.app.users.validation.TimerNameValidator
import io.timemates.sdk.common.constructor.createOrThrow
import io.timemates.sdk.timers.types.value.TimerName
import io.timemates.sdk.users.profile.types.value.UserDescription
import kotlinx.coroutines.CoroutineScope


class TimerSettingsReducer(
    private val editTimerUseCase: EditTimerUseCase,
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

                    else -> UserDescription.createOrThrow(state.description)
                }
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

            is Event.PeriodIsChanged ->
                state.copy(period = event.period, isPeriodSizeInvalid = false)

            is Event.PauseTimeIsChanged ->
                state.copy(pauseTime = event.pauseTime, isPauseTimeSizeInvalid = false)
        }
    }
}