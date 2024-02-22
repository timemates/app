@file:Suppress("USELESS_CAST")

package org.timemates.app.mvi.compose

import androidx.compose.runtime.Composable
import org.koin.compose.LocalKoinScope
import org.koin.core.parameter.ParametersDefinition
import org.timemates.app.foundation.mvi.*

/**
 * Creates and returns an instance of the specified state machine using the provided factory.
 *
 * @param TSM The reified type of the state machine.
 * @param factory The factory to create the state machine instance.
 * @return The created instance of the state machine.
 */
@Composable
inline fun <reified TSM : MVIComponent<*, *, *>> koinMviComponent(
    noinline parameters: ParametersDefinition? = null,
): TSM {
    return LocalKoinScope.current.getKoin().get<TSM>(parameters = parameters)
}
