@file:Suppress("USELESS_CAST")

package io.timemates.app.mvi.compose

import androidx.compose.runtime.Composable
import androidx.lifecycle.ViewModelStoreOwner
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import io.timemates.androidx.viewmodel.ViewModel
import io.timemates.app.foundation.mvi.*
import org.koin.androidx.compose.defaultExtras
import org.koin.androidx.viewmodel.resolveViewModel
import org.koin.compose.LocalKoinScope
import org.koin.core.annotation.KoinInternalApi
import org.koin.core.parameter.ParametersDefinition
import org.koin.core.qualifier.Qualifier
import org.koin.core.scope.Scope

/**
 * Creates and returns an instance of the specified state machine using the provided factory.
 *
 * @param TSM The reified type of the state machine.
 * @param TState The type of the state in the state machine.
 * @param TEvent The type of the events in the state machine.
 * @param TEffect The type of the effects in the state machine.
 * @param factory The factory to create the state machine instance.
 * @return The created instance of the state machine.
 */
@Composable
actual inline fun <TState : UiState, TEvent : UiEvent, TEffect : UiEffect> stateMachine(
    noinline parameters: ParametersDefinition?,
): StateMachine<TState, TEvent, TEffect> = koinVM(parameters = parameters)

@OptIn(KoinInternalApi::class)
@PublishedApi
@Composable
internal inline fun <reified T : ViewModel> koinVM(
    qualifier: Qualifier? = null,
    viewModelStoreOwner: ViewModelStoreOwner = checkNotNull(LocalViewModelStoreOwner.current) {
        "No ViewModelStoreOwner was provided via LocalViewModelStoreOwner"
    },
    key: String? = null,
    extras: CreationExtras = defaultExtras(viewModelStoreOwner),
    scope: Scope = LocalKoinScope.current,
    noinline parameters: ParametersDefinition? = null,
): T {
    return resolveViewModel(
        T::class, viewModelStoreOwner.viewModelStore, key, extras, qualifier, scope, parameters
    )
}
