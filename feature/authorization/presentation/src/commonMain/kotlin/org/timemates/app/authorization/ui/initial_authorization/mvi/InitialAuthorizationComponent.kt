package org.timemates.app.authorization.ui.initial_authorization.mvi

import androidx.compose.runtime.Immutable
import com.arkivanov.decompose.ComponentContext
import kotlinx.serialization.Serializable
import org.timemates.app.authorization.ui.initial_authorization.mvi.InitialAuthorizationComponent.Action
import org.timemates.app.authorization.ui.initial_authorization.mvi.InitialAuthorizationComponent.Intent
import org.timemates.app.authorization.ui.initial_authorization.mvi.InitialAuthorizationComponent.State
import pro.respawn.flowmvi.api.Container
import pro.respawn.flowmvi.api.MVIAction
import pro.respawn.flowmvi.api.MVIIntent
import pro.respawn.flowmvi.api.MVIState
import pro.respawn.flowmvi.api.Store
import pro.respawn.flowmvi.dsl.emit
import pro.respawn.flowmvi.essenty.dsl.retainedStore
import pro.respawn.flowmvi.plugins.reduce

class InitialAuthorizationComponent(
    componentContext: ComponentContext,
) : ComponentContext by componentContext, Container<State, Intent, Action> {
    override val store: Store<State, Intent, Action> = retainedStore(initial = State) {
        reduce { intent: Intent ->
            when (intent) {
                is Intent.OnStartClicked -> {
                    emit(Action.NavigateToStart)
                }
            }
        }
    }

    @Immutable
    @Serializable
    data object State : MVIState

    sealed class Action : MVIAction {
        data object NavigateToStart : Action()
    }

    sealed class Intent : MVIIntent {
        data object OnStartClicked : Intent()
    }
}