package org.timemates.app.authorization.ui.new_account_info.mvi

import com.arkivanov.decompose.ComponentContext
import io.timemates.sdk.authorization.email.types.value.VerificationHash
import kotlinx.serialization.Serializable
import org.timemates.app.authorization.ui.new_account_info.mvi.NewAccountInfoScreenComponent.Effect
import org.timemates.app.authorization.ui.new_account_info.mvi.NewAccountInfoScreenComponent.Event
import org.timemates.app.authorization.ui.new_account_info.mvi.NewAccountInfoScreenComponent.State
import org.timemates.app.foundation.mvi.MVIComponent
import org.timemates.app.foundation.mvi.UiEffect
import org.timemates.app.foundation.mvi.UiEvent
import org.timemates.app.foundation.mvi.UiState
import org.timemates.app.foundation.mvi.mviComponent

class NewAccountInfoScreenComponent(
    componentContext: ComponentContext,
    reducer: NewAccountInfoReducer,
) : MVIComponent<State, Event, Effect> by mviComponent(
    componentContext = componentContext,
    componentName = "NewAccountInfoScreen",
    reducer = reducer,
    initState = State,
) {
    @Serializable
    data object State : UiState {
        private fun readResolve(): Any = State
    }

    sealed class Effect : UiEffect {
        data class NavigateToAccountConfiguring(val verificationHash: VerificationHash) : Effect()

        data object NavigateToStart : Effect()
    }

    sealed class Event : UiEvent {
        data object NextClicked : Event()

        data object OnBackClicked : Event()
    }
}