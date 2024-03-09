@file:Suppress("USELESS_CAST")

package org.timemates.app.mvi.compose

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.StableMarker
import androidx.compose.runtime.remember
import org.koin.compose.LocalKoinApplication
import org.koin.compose.LocalKoinScope
import org.koin.compose.rememberKoinInject
import org.koin.core.parameter.ParametersDefinition
import org.koin.core.parameter.ParametersHolder
import org.koin.core.qualifier.Qualifier
import org.koin.core.scope.Scope
import org.timemates.app.foundation.mvi.*

/**
 * Creates and returns an instance of the specified state machine using the provided factory.
 *
 * @param TSM The reified type of the state machine.
 * @return The created instance of the state machine.
 */
@Composable
inline fun <reified TSM : MVIComponent<*, *, *>> koinMviComponent(
    noinline parameters: ParametersDefinition? = null,
): TSM {
    val koin = LocalKoinScope.current.getKoin()

    return remember {
        koin.get(qualifier = null, parameters)
    }
}
