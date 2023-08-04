package io.timemates.app.authorization.ui.start.mvi

import androidx.compose.runtime.Immutable
import io.timemates.app.authorization.ui.start.mvi.StartAuthorizationStateMachine.Effect
import io.timemates.app.authorization.ui.start.mvi.StartAuthorizationStateMachine.Event
import io.timemates.app.authorization.ui.start.mvi.StartAuthorizationStateMachine.State
import io.timemates.app.foundation.mvi.StateMachine
import io.timemates.app.foundation.mvi.UiEffect
import io.timemates.app.foundation.mvi.UiEvent
import io.timemates.app.foundation.mvi.UiState
import io.timemates.sdk.authorization.email.types.value.VerificationHash

class StartAuthorizationStateMachine(
    reducer: StartAuthorizationReducer,
    middleware: StartAuthorizationMiddleware,
) : StateMachine<State, Event, Effect>(reducer, middlewares = listOf(middleware)) {

    override fun initDefaultState(): State = State()

    @Immutable
    data class State(
        val email: String = "",
        val isEmailInvalid: Boolean = false,
        val isEmailLengthSizeInvalid: Boolean = false,
        val isLoading: Boolean = false,
    ) : UiState

    sealed class Event : UiEvent {
        data class EmailChange(val email: String) : Event()

        object OnStartClick : Event()
    }

    sealed class Effect : UiEffect {
        object TooManyAttempts : Effect()

        data class Failure(val throwable: Throwable) : Effect()

        data class NavigateToConfirmation(
            val verificationHash: VerificationHash,
        ) : Effect()
    }
}