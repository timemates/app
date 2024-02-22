package org.timemates.app.authorization.ui.new_account_info.mvi

import io.timemates.sdk.authorization.email.types.value.VerificationHash
import org.timemates.app.authorization.ui.new_account_info.mvi.NewAccountInfoScreenComponent.Effect
import org.timemates.app.authorization.ui.new_account_info.mvi.NewAccountInfoScreenComponent.Event
import org.timemates.app.authorization.ui.new_account_info.mvi.NewAccountInfoScreenComponent.State
import org.timemates.app.foundation.mvi.Reducer
import org.timemates.app.foundation.mvi.ReducerScope

class NewAccountInfoReducer(private val verificationHash: VerificationHash) : Reducer<State, Event, Effect> {
    override fun ReducerScope<Effect>.reduce(state: State, event: Event): State {
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