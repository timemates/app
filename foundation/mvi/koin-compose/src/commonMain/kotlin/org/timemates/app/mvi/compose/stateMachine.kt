package org.timemates.app.mvi.compose

import androidx.compose.runtime.Composable
import org.timemates.app.foundation.mvi.StateMachine
import org.koin.core.parameter.ParametersDefinition


/**
 * Creates and returns an instance of the specified state machine using the provided factory.
 *
 * @param TSM The reified type of the state machine.
 * @param TState The type of the state in the state machine.
 * @param TEvent The type of the events in the state machine.
 * @param TEffect The type of the effects in the state machine.
 * @return The created instance of the state machine.
 */
@Composable
expect inline fun <reified TSM : StateMachine<*, *, *>> stateMachine(
    noinline parameters: ParametersDefinition? = null,
): TSM