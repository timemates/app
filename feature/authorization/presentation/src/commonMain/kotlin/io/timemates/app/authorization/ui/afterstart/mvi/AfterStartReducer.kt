package io.timemates.app.authorization.ui.afterstart.mvi

import io.timemates.app.authorization.ui.afterstart.mvi.AfterStartStateMachine.Effect
import io.timemates.app.authorization.ui.afterstart.mvi.AfterStartStateMachine.Event
import io.timemates.app.foundation.mvi.EmptyState
import io.timemates.app.foundation.mvi.Reducer
import io.timemates.sdk.authorization.email.types.value.VerificationHash

class AfterStartReducer(private val verificationHash: VerificationHash) : Reducer<EmptyState, Event, Effect> {
    override fun reduce(
        state: EmptyState,
        event: Event,
        sendEffect: (Effect) -> Unit,
    ): EmptyState {
        return when (event) {
            is Event.NextClicked -> {
                sendEffect(Effect.NavigateToConfirmation(verificationHash))
                state
            }

            is Event.OnChangeEmailClicked -> {
                sendEffect(Effect.OnChangeEmailClicked)
                state
            }
        }
    }
}