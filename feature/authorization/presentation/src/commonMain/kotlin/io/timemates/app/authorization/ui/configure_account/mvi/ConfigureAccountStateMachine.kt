package io.timemates.app.authorization.ui.configure_account.mvi

import androidx.compose.runtime.Immutable
import io.timemates.app.authorization.ui.configure_account.mvi.ConfigureAccountStateMachine.Effect
import io.timemates.app.authorization.ui.configure_account.mvi.ConfigureAccountStateMachine.Event
import io.timemates.app.authorization.ui.configure_account.mvi.ConfigureAccountStateMachine.State
import io.timemates.app.foundation.mvi.StateMachine
import io.timemates.app.foundation.mvi.UiEffect
import io.timemates.app.foundation.mvi.UiEvent
import io.timemates.app.foundation.mvi.UiState
import io.timemates.sdk.authorization.sessions.types.Authorization

class ConfigureAccountStateMachine(
    reducer: ConfigureAccountReducer,
    middleware: ConfigureAccountMiddleware,
) : StateMachine<State, Event, Effect>(
    initState = State(),
    reducer = reducer,
    middlewares = listOf(middleware),
) {
    @Immutable
    data class State(
        val name: String = "",
        val aboutYou: String = "",
        val isNameSizeInvalid: Boolean = false,
        val isAboutYouSizeInvalid: Boolean = false,
        val isLoading: Boolean = false,
    ) : UiState

    sealed class Event : UiEvent {
        data class NameIsChanged(val name: String) : Event()

        data class DescriptionIsChanged(val description: String) : Event()

        object OnDoneClicked : Event()
    }

    sealed class Effect : UiEffect {
        data class Failure(val throwable: Throwable) : Effect()

        object NavigateToStart : Effect()

        data class NavigateToHomePage(
            val authorization: Authorization,
        ) : Effect()
    }
}