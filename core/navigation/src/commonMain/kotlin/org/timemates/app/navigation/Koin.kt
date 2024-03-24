@file:Suppress("USELESS_CAST")

package org.timemates.app.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import org.koin.compose.LocalKoinScope
import org.koin.core.parameter.ParametersDefinition

/**
 * Creates and returns an instance of the specified state machine using the provided factory.
 *
 * @param TSM The reified type of the state machine.
 * @return The created instance of the state machine.
 */
@Composable
inline fun <reified TSM> koinMviComponent(
    noinline parameters: ParametersDefinition? = null,
): TSM {
    val koin = LocalKoinScope.current.getKoin()

    return remember {
        koin.get(qualifier = null, parameters)
    }
}
