package org.timemates.app.timers.ui.settings.mvi

import androidx.compose.runtime.Immutable
import com.arkivanov.decompose.ComponentContext
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import org.timemates.app.feature.common.Input
import org.timemates.app.feature.common.input
import org.timemates.app.feature.common.isValid
import org.timemates.app.timers.ui.settings.mvi.TimerSettingsScreenComponent.Action
import org.timemates.app.timers.ui.settings.mvi.TimerSettingsScreenComponent.Intent
import org.timemates.app.timers.ui.settings.mvi.TimerSettingsScreenComponent.State
import org.timemates.app.users.usecases.TimerSettingsUseCase
import org.timemates.sdk.common.constructor.createOrThrow
import org.timemates.sdk.common.types.value.Count
import org.timemates.sdk.timers.types.TimerSettings
import org.timemates.sdk.timers.types.value.TimerDescription
import org.timemates.sdk.timers.types.value.TimerId
import org.timemates.sdk.timers.types.value.TimerName
import pro.respawn.flowmvi.api.Container
import pro.respawn.flowmvi.api.MVIAction
import pro.respawn.flowmvi.api.MVIIntent
import pro.respawn.flowmvi.api.MVIState
import pro.respawn.flowmvi.api.PipelineContext
import pro.respawn.flowmvi.api.Store
import pro.respawn.flowmvi.essenty.dsl.retainedStore
import pro.respawn.flowmvi.plugins.recover
import pro.respawn.flowmvi.plugins.reduce
import kotlin.time.Duration
import kotlin.time.Duration.Companion.minutes

class TimerSettingsScreenComponent(
    componentContext: ComponentContext,
    private val timerId: TimerId,
    private val timerSettingsUseCase: TimerSettingsUseCase,
) : ComponentContext by componentContext, Container<State, Intent, Action> {
    override val store: Store<State, Intent, Action> = retainedStore(initial = State()) {
        recover { exception ->
            action(Action.ShowFailure(exception))
            null
        }

        reduce { intent ->
            updateState {
                when (intent) {
                    is Intent.NameIsChanged -> copy(name = input(intent.name))
                    is Intent.DescriptionIsChanged ->
                        copy(description = input(intent.description))

                    is Intent.BigRestModeIsChanged ->
                        copy(bigRestEnabled = intent.bigRestEnabled)

                    is Intent.BigRestPerIsChanged ->
                        copy(bigRestPer = bigRestPer)

                    is Intent.BigRestTimeIsChanged ->
                        copy(bigRestTime = intent.bigRestTime)


                    is Intent.RestTimeIsChanged -> copy(restTime = intent.restTime)
                    is Intent.WorkTimeIsChanged -> copy(workTime = intent.workTime)

                    is Intent.TimerPauseControlAccessIsChanged ->
                        copy(isEveryoneCanPause = intent.isEveryoneCanPause)

                    is Intent.ConfirmationRequirementChanged ->
                        copy(isConfirmationRequired = intent.isConfirmationRequired)

                    Intent.OnDoneClicked -> copy(
                        name = name.validated(TimerName.factory),
                        description = description.validated(TimerDescription.factory),
                    ).run {
                        if (name.isValid() && description.isValid() && bigRestPer.isValid()) {
                            saveChangesAsync(
                                name = TimerName.factory.createOrThrow(name.value),
                                description = TimerDescription.factory.createOrThrow(description.value),
                                settings = TimerSettings.Patch(
                                    workTime = workTime,
                                    restTime = restTime,
                                    bigRestTime = bigRestTime,
                                    bigRestEnabled = bigRestEnabled,
                                    bigRestPer = Count.factory.createOrThrow(bigRestPer.value),
                                    isEveryoneCanPause = isEveryoneCanPause,
                                    isConfirmationRequired = isConfirmationRequired,
                                ),
                            )
                            copy(isLoading = true)
                        } else this
                    }
                }
            }
        }
    }

    private suspend fun PipelineContext<State, Intent, Action>.saveChangesAsync(
        name: TimerName,
        description: TimerDescription,
        settings: TimerSettings.Patch,
    ): Unit = coroutineScope {
        launch {
            when (val result = timerSettingsUseCase.execute(timerId, name, description, settings)) {
                is TimerSettingsUseCase.Result.Failure ->
                    action(Action.ShowFailure(result.exception))

                TimerSettingsUseCase.Result.Success -> action(Action.NavigateToTimersScreen)
            }

            updateState { copy(isLoading = false) }
        }
    }


    @Immutable
    data class State(
        val name: Input<String> = input(""),
        val description: Input<String> = input(""),
        val workTime: Duration = 25.minutes,
        val restTime: Duration = 5.minutes,
        val bigRestEnabled: Boolean = true,
        val bigRestPer: Input<Int> = input(4),
        val bigRestTime: Duration = 10.minutes,
        val isEveryoneCanPause: Boolean = false,
        val isConfirmationRequired: Boolean = true,
        val isLoading: Boolean = false,
    ) : MVIState

    sealed class Intent : MVIIntent {
        data class NameIsChanged(val name: String) : Intent()

        data class DescriptionIsChanged(val description: String) : Intent()

        data class WorkTimeIsChanged(val workTime: Duration) : Intent()

        data class RestTimeIsChanged(val restTime: Duration) : Intent()

        data class BigRestModeIsChanged(val bigRestEnabled: Boolean) : Intent()

        data class BigRestPerIsChanged(val bigRestPer: Count) : Intent()

        data class BigRestTimeIsChanged(val bigRestTime: Duration) : Intent()

        data class TimerPauseControlAccessIsChanged(val isEveryoneCanPause: Boolean) : Intent()

        data class ConfirmationRequirementChanged(val isConfirmationRequired: Boolean) : Intent()

        data object OnDoneClicked : Intent()
    }

    sealed class Action : MVIAction {
        data class ShowFailure(val throwable: Throwable) : Action()

        data object NavigateToTimersScreen : Action()
    }
}
