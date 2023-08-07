package io.timemates.app.authorization.ui.new_account_info.mvi

import io.timemates.app.authorization.ui.new_account_info.mvi.NewAccountInfoStateMachine.Effect
import io.timemates.app.authorization.ui.new_account_info.mvi.NewAccountInfoStateMachine.Event
import io.timemates.app.foundation.mvi.StateMachine
import io.timemates.app.foundation.mvi.EmptyState
import io.timemates.app.foundation.mvi.UiEffect
import io.timemates.app.foundation.mvi.UiEvent
import io.timemates.sdk.authorization.email.types.value.VerificationHash

class NewAccountInfoStateMachine(
    reducer: NewAccountInfoReducer,
) : StateMachine<EmptyState, Event, Effect>(
    reducer = reducer,
    middlewares = emptyList(),
) {
    sealed class Effect : UiEffect {
        data class NavigateToAccountConfiguring(val verificationHash: VerificationHash) : Effect()

        object NavigateToStart : Effect()
    }

    sealed class Event : UiEvent {
        object NextClicked : Event()

        object OnBackClicked : Event()
    }

    override fun initDefaultState(): EmptyState = EmptyState
}