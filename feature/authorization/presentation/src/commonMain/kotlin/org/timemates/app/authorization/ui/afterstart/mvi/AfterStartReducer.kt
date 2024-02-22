package org.timemates.app.authorization.ui.afterstart.mvi

import io.timemates.sdk.authorization.email.types.value.VerificationHash
import org.timemates.app.authorization.ui.afterstart.mvi.AfterStartScreenComponent.Effect
import org.timemates.app.authorization.ui.afterstart.mvi.AfterStartScreenComponent.Event
import org.timemates.app.authorization.ui.afterstart.mvi.AfterStartScreenComponent.State
import org.timemates.app.foundation.mvi.Reducer
import org.timemates.app.foundation.mvi.ReducerScope

class AfterStartReducer(private val verificationHash: VerificationHash) : Reducer<State, Event, Effect> {
    override fun ReducerScope<Effect>.reduce(state: State, event: Event): State {
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