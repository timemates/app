package org.timemates.app.authorization.ui.confirmation.mvi

import androidx.compose.runtime.Immutable
import org.timemates.app.authorization.ui.confirmation.mvi.ConfirmAuthorizationStateMachine.Effect
import org.timemates.app.authorization.ui.confirmation.mvi.ConfirmAuthorizationStateMachine.Event
import org.timemates.app.authorization.ui.confirmation.mvi.ConfirmAuthorizationStateMachine.State
import org.timemates.app.foundation.mvi.StateMachine
import org.timemates.app.foundation.mvi.UiEffect
import org.timemates.app.foundation.mvi.UiEvent
import org.timemates.app.foundation.mvi.UiState
import io.timemates.sdk.authorization.email.types.value.VerificationHash
import io.timemates.sdk.authorization.sessions.types.Authorization

class ConfirmAuthorizationStateMachine(
    reducer: ConfirmAuthorizationsReducer,
    middleware: ConfirmAuthorizationMiddleware,
) : StateMachine<State, Event, Effect>(
    initState = State(),
    reducer = reducer,
    middlewares = listOf(middleware),
) {
    @Immutable
    data class State(
        val code: String = "",
        val isCodeInvalid: Boolean = false,
        val isCodeSizeInvalid: Boolean = false,
        val isLoading: Boolean = false,
    ) : UiState

    sealed class Event : UiEvent {
        data class CodeChange(val code: String) : Event()

        data object OnConfirmClicked : Event()
    }

    sealed class Effect : UiEffect {
        data object TooManyAttempts : Effect()

        data object AttemptIsFailed : Effect()

        data class Failure(val throwable: Throwable) : Effect()

        data class NavigateToCreateAccount(
            val verificationHash: VerificationHash,
        ) : Effect()

        data class NavigateToHome(
            val authorization: Authorization,
        ) : Effect()
    }
}