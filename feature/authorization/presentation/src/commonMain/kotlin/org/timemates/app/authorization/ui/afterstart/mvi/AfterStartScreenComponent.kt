package org.timemates.app.authorization.ui.afterstart.mvi

import androidx.compose.runtime.Immutable
import com.arkivanov.decompose.ComponentContext
import org.timemates.app.authorization.ui.afterstart.mvi.AfterStartScreenComponent.Action
import org.timemates.app.authorization.ui.afterstart.mvi.AfterStartScreenComponent.Intent
import org.timemates.app.authorization.ui.afterstart.mvi.AfterStartScreenComponent.State
import org.timemates.sdk.authorization.email.types.value.VerificationHash
import pro.respawn.flowmvi.api.Container
import pro.respawn.flowmvi.api.MVIAction
import pro.respawn.flowmvi.api.MVIIntent
import pro.respawn.flowmvi.api.MVIState
import pro.respawn.flowmvi.api.Store
import pro.respawn.flowmvi.essenty.dsl.retainedStore
import pro.respawn.flowmvi.plugins.reduce

class AfterStartScreenComponent(
    componentContext: ComponentContext,
    private val verificationHash: VerificationHash,
) : ComponentContext by componentContext, Container<State, Intent, Action> {

    override val store: Store<State, Intent, Action> = retainedStore(initial = State) {
        reduce { intent ->
            when (intent) {
                Intent.NextClicked -> action(Action.NavigateToConfirmation(verificationHash))
                Intent.OnChangeEmailClicked -> action(Action.OnChangeEmailClicked)
            }
        }
    }

    @Immutable
    data object State : MVIState

    sealed class Action : MVIAction {
        data class NavigateToConfirmation(val verificationHash: VerificationHash) : Action()

        data object OnChangeEmailClicked : Action()
    }

    sealed class Intent : MVIIntent {
        data object NextClicked : Intent()

        data object OnChangeEmailClicked : Intent()
    }
}