package org.timemates.app.feature.common.startup.mvi

import com.arkivanov.decompose.ComponentContext
import kotlinx.coroutines.launch
import kotlinx.serialization.Serializable
import org.timemates.app.feature.common.startup.mvi.StartupScreenMVIComponent.State
import org.timemates.app.feature.system.repositories.AuthRepository
import org.timemates.app.foundation.mvi.MVIComponent
import org.timemates.app.foundation.mvi.ReducerScope
import org.timemates.app.foundation.mvi.UiEffect
import org.timemates.app.foundation.mvi.UiEvent
import org.timemates.app.foundation.mvi.UiState
import org.timemates.app.foundation.mvi.mviComponent
import org.timemates.app.foundation.mvi.Reducer as MviReducer

/**
 * The global app state machine. Responsible for checking whether user is authorized
 * and for a new updates (TODO).
 */
class StartupScreenMVIComponent(
    componentContext: ComponentContext,
    authRepository: AuthRepository,
) : MVIComponent<State, StartupEvent, StartupEffect> by mviComponent(
    componentContext = componentContext,
    componentName = "StartupScreen",
    initState = State,
    reducer = Reducer(authRepository),
) {
    @Serializable
    data object State : UiState {
        private fun readResolve(): Any = State
    }

    class Reducer(
        private val authRepository: AuthRepository,
    ) : MviReducer<State, StartupEvent, StartupEffect> {
        override fun ReducerScope<StartupEffect>.reduce(
            state: State,
            event: StartupEvent,
        ): State {
            return when (event) {
                StartupEvent.Started -> {
                    machineScope.launch {
                        if (authRepository.isAuthorized())
                            sendEffect(StartupEffect.Authorized)
                        else sendEffect(StartupEffect.Unauthorized)
                    }

                    state
                }
            }
        }
    }
}

sealed class StartupEvent : UiEvent {
    /**
     * Indicates that app is started.
     */
    data object Started : StartupEvent()
}

sealed class StartupEffect : UiEffect {
    /**
     * Indicates that user is not authorized and should be moved to the
     * authorization screen.
     */
    data object Unauthorized : StartupEffect()

    /**
     * Indicates that user is authorized and should be moved to the
     * home screen.
     */
    data object Authorized : StartupEffect()
}