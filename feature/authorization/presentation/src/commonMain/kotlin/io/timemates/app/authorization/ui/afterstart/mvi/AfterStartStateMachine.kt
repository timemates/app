package io.timemates.app.authorization.ui.afterstart.mvi

import io.timemates.app.authorization.ui.afterstart.mvi.AfterStartStateMachine.Effect
import io.timemates.app.authorization.ui.afterstart.mvi.AfterStartStateMachine.Event
import io.timemates.app.foundation.mvi.AbstractStateMachine
import io.timemates.app.foundation.mvi.EmptyState
import io.timemates.app.foundation.mvi.UiEffect
import io.timemates.app.foundation.mvi.UiEvent
import io.timemates.sdk.authorization.email.types.value.VerificationHash

class AfterStartStateMachine(
    reducer: AfterStartReducer,
) : AbstractStateMachine<EmptyState, Event, Effect>(
    reducer = reducer,
    middlewares = emptyList(),
) {
    sealed class Effect : UiEffect {
        data class NavigateToConfirmation(val verificationHash: VerificationHash) : Effect()

        object OnChangeEmailClicked : Effect()
    }

    sealed class Event : UiEvent {
        object NextClicked : Event()

        object OnChangeEmailClicked : Event()
    }

    override fun initDefaultState(): EmptyState = EmptyState
}