package io.timemates.app.mvi.compose

import androidx.compose.runtime.Composable
import io.timemates.app.foundation.mvi.StateMachine
import org.koin.compose.koinInject
import org.koin.core.parameter.ParametersDefinition

@Composable
actual inline fun <reified TSM : StateMachine<*, *, *>> stateMachine(
    noinline parameters: ParametersDefinition?,
): TSM = koinInject<TSM>(
    parameters = parameters,
)