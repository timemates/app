package io.timemates.app.authorization.ui.new_account_info.mvi

import io.timemates.app.authorization.ui.new_account_info.mvi.NewAccountInfoStateMachine.Effect
import io.timemates.app.authorization.ui.new_account_info.mvi.NewAccountInfoStateMachine.Event
import io.timemates.app.foundation.mvi.EmptyState
import io.timemates.app.foundation.mvi.Reducer
import io.timemates.sdk.authorization.email.types.value.VerificationHash

class NewAccountInfoReducer(private val verificationHash: VerificationHash) : Reducer<EmptyState, Event, Effect> {
    override fun reduce(
        state: EmptyState,
        event: Event,
        sendEffect: (Effect) -> Unit,
    ): EmptyState {
        return when (event) {
            is Event.NextClicked -> {
                sendEffect(Effect.NavigateToAccountConfiguring(verificationHash))
                state
            }

            is Event.OnBackClicked -> {
                sendEffect(Effect.NavigateToStart)
                state
            }
        }
    }
}