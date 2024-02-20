package org.timemates.app.authorization.ui.afterstart.mvi

import org.timemates.app.authorization.ui.afterstart.mvi.AfterStartStateMachine.Effect
import org.timemates.app.authorization.ui.afterstart.mvi.AfterStartStateMachine.Event
import org.timemates.app.foundation.mvi.EmptyState
import org.timemates.app.foundation.mvi.Reducer
import org.timemates.app.foundation.mvi.ReducerScope
import io.timemates.sdk.authorization.email.types.value.VerificationHash

class AfterStartReducer(private val verificationHash: VerificationHash) : Reducer<EmptyState, Event, Effect> {
    override fun ReducerScope<Effect>.reduce(state: EmptyState, event: Event): EmptyState {
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