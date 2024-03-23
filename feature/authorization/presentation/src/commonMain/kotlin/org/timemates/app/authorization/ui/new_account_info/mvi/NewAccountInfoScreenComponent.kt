package org.timemates.app.authorization.ui.new_account_info.mvi

import com.arkivanov.decompose.ComponentContext
import org.timemates.app.authorization.ui.new_account_info.mvi.NewAccountInfoScreenComponent.Action
import org.timemates.app.authorization.ui.new_account_info.mvi.NewAccountInfoScreenComponent.Intent
import org.timemates.app.authorization.ui.new_account_info.mvi.NewAccountInfoScreenComponent.State
import org.timemates.sdk.authorization.email.types.value.VerificationHash
import pro.respawn.flowmvi.api.Container
import pro.respawn.flowmvi.api.MVIAction
import pro.respawn.flowmvi.api.MVIIntent
import pro.respawn.flowmvi.api.MVIState
import pro.respawn.flowmvi.essenty.dsl.retainedStore
import pro.respawn.flowmvi.plugins.reduce

class NewAccountInfoScreenComponent(
    componentContext: ComponentContext,
    private val verificationHash: VerificationHash,
) : ComponentContext by componentContext, Container<State, Intent, Action> {
    override val store = retainedStore(initial = State) {
        reduce { intent ->
            when (intent) {
                Intent.NextClicked -> action(Action.NavigateToAccountConfiguring(verificationHash))
                Intent.OnBackClicked -> action(Action.NavigateToStart)
            }
        }
    }

    data object State : MVIState

    sealed class Action : MVIAction {
        data class NavigateToAccountConfiguring(val verificationHash: VerificationHash) : Action()

        data object NavigateToStart : Action()
    }

    sealed class Intent : MVIIntent {
        data object NextClicked : Intent()

        data object OnBackClicked : Intent()
    }
}