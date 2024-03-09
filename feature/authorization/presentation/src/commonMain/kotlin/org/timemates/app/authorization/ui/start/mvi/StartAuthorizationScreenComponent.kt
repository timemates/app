package org.timemates.app.authorization.ui.start.mvi

import androidx.compose.runtime.Immutable
import com.arkivanov.decompose.ComponentContext
import org.timemates.sdk.authorization.email.types.value.VerificationHash
import kotlinx.serialization.Serializable
import org.timemates.app.authorization.ui.start.mvi.StartAuthorizationScreenComponent.Effect
import org.timemates.app.authorization.ui.start.mvi.StartAuthorizationScreenComponent.Event
import org.timemates.app.authorization.ui.start.mvi.StartAuthorizationScreenComponent.State
import org.timemates.app.foundation.mvi.MVIComponent
import org.timemates.app.foundation.mvi.UiEffect
import org.timemates.app.foundation.mvi.UiEvent
import org.timemates.app.foundation.mvi.UiState
import org.timemates.app.foundation.mvi.mviComponent

class StartAuthorizationScreenComponent(
    componentContext: ComponentContext,
    reducer: StartAuthorizationReducer,
    middleware: StartAuthorizationMiddleware,
) : MVIComponent<State, Event, Effect> by mviComponent(
    componentContext = componentContext,
    componentName = "StartAuthorizationComponent",
    initState = State(),
    reducer = reducer,
    middlewares = listOf(middleware),
) {
    @Serializable
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