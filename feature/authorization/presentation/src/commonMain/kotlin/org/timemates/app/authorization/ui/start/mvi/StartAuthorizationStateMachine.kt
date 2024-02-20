package org.timemates.app.authorization.ui.start.mvi

import androidx.compose.runtime.Immutable
import org.timemates.app.authorization.ui.start.mvi.StartAuthorizationStateMachine.Effect
import org.timemates.app.authorization.ui.start.mvi.StartAuthorizationStateMachine.Event
import org.timemates.app.authorization.ui.start.mvi.StartAuthorizationStateMachine.State
import org.timemates.app.foundation.mvi.StateMachine
import org.timemates.app.foundation.mvi.UiEffect
import org.timemates.app.foundation.mvi.UiEvent
import org.timemates.app.foundation.mvi.UiState
import io.timemates.sdk.authorization.email.types.value.VerificationHash

class StartAuthorizationStateMachine(
    reducer: StartAuthorizationReducer,
    middleware: StartAuthorizationMiddleware,
) : StateMachine<State, Event, Effect>(
    initState = State(),
    reducer = reducer,
    middlewares = listOf(middleware)
) {
    @Immutable
    data class State(
        val email: String = "",
        val isEmailInvalid: Boolean = false,
        val isEmailLengthSizeInvalid: Boolean = false,
        val isLoading: Boolean = false,
    ) : UiState

    sealed class Event : UiEvent {
        data class EmailChange(val email: String) : Event()

        data object OnStartClick : Event()
    }

    sealed class Effect : UiEffect {
        data object TooManyAttempts : Effect()

        data class Failure(val throwable: Throwable) : Effect()

        data class NavigateToConfirmation(
            val verificationHash: VerificationHash,
        ) : Effect()
    }
}