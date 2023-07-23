package io.timemates.app.authorization.ui.confirmation.mvi

import androidx.compose.runtime.Immutable
import io.timemates.app.authorization.ui.confirmation.mvi.ConfirmAuthorizationStateMachine.Effect
import io.timemates.app.authorization.ui.confirmation.mvi.ConfirmAuthorizationStateMachine.Event
import io.timemates.app.authorization.ui.confirmation.mvi.ConfirmAuthorizationStateMachine.State
import io.timemates.app.foundation.mvi.AbstractStateMachine
import io.timemates.app.foundation.mvi.UiEffect
import io.timemates.app.foundation.mvi.UiEvent
import io.timemates.app.foundation.mvi.UiState
import io.timemates.sdk.authorization.email.types.value.VerificationHash
import io.timemates.sdk.authorization.sessions.types.Authorization

class ConfirmAuthorizationStateMachine(
    reducer: ConfirmAuthorizationsReducer,
    middleware: ConfirmAuthorizationMiddleware,
) : AbstractStateMachine<State, Event, Effect>(
    reducer = reducer,
    middlewares = listOf(middleware),
) {

    override fun initDefaultState(): State {
        return State()
    }

    @Immutable
    data class State(
        val code: String = "",
        val isCodeInvalid: Boolean = false,
        val isCodeSizeInvalid: Boolean = false,
        val isLoading: Boolean = false,
    ) : UiState

    sealed class Event : UiEvent {
        data class CodeChange(val code: String) : Event()

        object OnConfirmClicked : Event()
    }

    sealed class Effect : UiEffect {
        object TooManyAttempts : Effect()

        data class Failure(val throwable: Throwable) : Effect()

        data class NavigateToCreateAccount(
            val verificationHash: VerificationHash,
        ) : Effect()

        data class NavigateToHome(
            val authorization: Authorization
        ) : Effect()
    }
}