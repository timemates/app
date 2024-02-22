package org.timemates.app.authorization.ui.configure_account.mvi

import androidx.compose.runtime.Immutable
import com.arkivanov.decompose.ComponentContext
import io.timemates.sdk.authorization.sessions.types.Authorization
import kotlinx.serialization.Serializable
import org.timemates.app.authorization.ui.configure_account.mvi.ConfigureAccountScreenComponent.Effect
import org.timemates.app.authorization.ui.configure_account.mvi.ConfigureAccountScreenComponent.Event
import org.timemates.app.authorization.ui.configure_account.mvi.ConfigureAccountScreenComponent.State
import org.timemates.app.foundation.mvi.MVIComponent
import org.timemates.app.foundation.mvi.UiEffect
import org.timemates.app.foundation.mvi.UiEvent
import org.timemates.app.foundation.mvi.UiState
import org.timemates.app.foundation.mvi.mviComponent

class ConfigureAccountScreenComponent(
    componentContext: ComponentContext,
    reducer: ConfigureAccountReducer,
    middleware: ConfigureAccountMiddleware,
) : MVIComponent<State, Event, Effect> by mviComponent(
    componentContext = componentContext,
    componentName = "ConfigureAccountComponent",
    initState = State(),
    reducer = reducer,
    middlewares = listOf(middleware),
) {
    @Serializable
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