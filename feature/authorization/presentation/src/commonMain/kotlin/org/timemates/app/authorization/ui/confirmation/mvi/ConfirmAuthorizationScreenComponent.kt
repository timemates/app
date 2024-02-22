package org.timemates.app.authorization.ui.confirmation.mvi

import androidx.compose.runtime.Immutable
import com.arkivanov.decompose.ComponentContext
import io.timemates.sdk.authorization.email.types.value.VerificationHash
import io.timemates.sdk.authorization.sessions.types.Authorization
import kotlinx.serialization.Serializable
import org.timemates.app.authorization.ui.confirmation.mvi.ConfirmAuthorizationScreenComponent.Effect
import org.timemates.app.authorization.ui.confirmation.mvi.ConfirmAuthorizationScreenComponent.Event
import org.timemates.app.authorization.ui.confirmation.mvi.ConfirmAuthorizationScreenComponent.State
import org.timemates.app.foundation.mvi.MVIComponent
import org.timemates.app.foundation.mvi.UiEffect
import org.timemates.app.foundation.mvi.UiEvent
import org.timemates.app.foundation.mvi.UiState
import org.timemates.app.foundation.mvi.mviComponent

class ConfirmAuthorizationScreenComponent(
    componentContext: ComponentContext,
    reducer: ConfirmAuthorizationsReducer,
    middleware: ConfirmAuthorizationMiddleware,
) : MVIComponent<State, Event, Effect> by mviComponent(
    componentContext = componentContext,
    componentName = "ConfirmAuthorizationComponent",
    initState = State(),
    reducer = reducer,
    middlewares = listOf(middleware),
) {
    @Serializable
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