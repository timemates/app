package org.timemates.app.authorization.ui.new_account_info.mvi

import org.timemates.app.authorization.ui.new_account_info.mvi.NewAccountInfoStateMachine.Effect
import org.timemates.app.authorization.ui.new_account_info.mvi.NewAccountInfoStateMachine.Event
import org.timemates.app.foundation.mvi.EmptyState
import org.timemates.app.foundation.mvi.Reducer
import org.timemates.app.foundation.mvi.ReducerScope
import io.timemates.sdk.authorization.email.types.value.VerificationHash

class NewAccountInfoReducer(private val verificationHash: VerificationHash) : Reducer<EmptyState, Event, Effect> {
    override fun ReducerScope<Effect>.reduce(state: EmptyState, event: Event): EmptyState {
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