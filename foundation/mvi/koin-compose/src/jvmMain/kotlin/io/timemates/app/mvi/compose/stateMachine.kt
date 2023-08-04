package io.timemates.app.mvi.compose

import androidx.compose.runtime.Composable
import io.timemates.app.foundation.mvi.AbstractStateMachine
import io.timemates.app.foundation.mvi.StateMachine
import io.timemates.app.foundation.mvi.UiEffect
import io.timemates.app.foundation.mvi.UiEvent
import io.timemates.app.foundation.mvi.UiState
import org.koin.compose.koinInject
import org.koin.core.parameter.ParametersDefinition

@Composable
actual inline fun <TState : UiState, TEvent : UiEvent, TEffect : UiEffect> stateMachine(
    noinline parameters: ParametersDefinition?
) = koinInject<StateMachine<TState, TEvent, TEffect>>(
    parameters = parameters,
)