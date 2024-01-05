package io.timemates.app.feature.common.startup.mvi

import io.timemates.app.feature.system.repositories.AuthRepository
import io.timemates.app.foundation.mvi.EmptyState
import io.timemates.app.foundation.mvi.ReducerScope
import io.timemates.app.foundation.mvi.StateMachine
import io.timemates.app.foundation.mvi.UiEffect
import io.timemates.app.foundation.mvi.UiEvent
import kotlinx.coroutines.launch
import io.timemates.app.foundation.mvi.Reducer as MviReducer

/**
 * The global app state machine. Responsible for checking whether user is authorized
 * and for a new updates (TODO).
 */
class StartupStateMachine(
    authRepository: AuthRepository,
) : StateMachine<EmptyState, StartupEvent, StartupEffect>(
    initState = EmptyState,
    reducer = Reducer(authRepository),
) {
    class Reducer(
        private val authRepository: AuthRepository,
    ) : MviReducer<EmptyState, StartupEvent, StartupEffect> {
        override fun ReducerScope<StartupEffect>.reduce(
            state: EmptyState,
            event: StartupEvent,
        ): EmptyState {
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