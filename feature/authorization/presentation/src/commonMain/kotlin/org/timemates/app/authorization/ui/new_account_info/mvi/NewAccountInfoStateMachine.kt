package org.timemates.app.authorization.ui.new_account_info.mvi

import org.timemates.app.authorization.ui.new_account_info.mvi.NewAccountInfoStateMachine.Effect
import org.timemates.app.authorization.ui.new_account_info.mvi.NewAccountInfoStateMachine.Event
import org.timemates.app.foundation.mvi.EmptyState
import org.timemates.app.foundation.mvi.StateMachine
import org.timemates.app.foundation.mvi.UiEffect
import org.timemates.app.foundation.mvi.UiEvent
import io.timemates.sdk.authorization.email.types.value.VerificationHash

class NewAccountInfoStateMachine(
    reducer: NewAccountInfoReducer,
) : StateMachine<EmptyState, Event, Effect>(
    initState = EmptyState,
    reducer = reducer,
    middlewares = emptyList(),
) {
    sealed class Effect : UiEffect {
        data class NavigateToAccountConfiguring(val verificationHash: VerificationHash) : Effect()

        data object NavigateToStart : Effect()
    }

    sealed class Event : UiEvent {
        data object NextClicked : Event()

        data object OnBackClicked : Event()
    }
}