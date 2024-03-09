package org.timemates.app.authorization.ui.initial_authorization.mvi

import com.arkivanov.decompose.ComponentContext
import org.timemates.app.authorization.ui.initial_authorization.mvi.InitialAuthorizationScreenComponent.*
import pro.respawn.flowmvi.api.Container
import pro.respawn.flowmvi.api.Store
import pro.respawn.flowmvi.dsl.emit
import pro.respawn.flowmvi.essenty.dsl.retainedStore
import pro.respawn.flowmvi.plugins.reduce

class InitialAuthorizationComponent(
    componentContext: ComponentContext,
) : ComponentContext by componentContext, Container<State, Event, Effect> {
    override val store: Store<State, Event, Effect> = retainedStore(initial = State) {
        coroutineContext
        reduce { event: Event ->
            when (event) {
                is Event.OnStartClicked -> {
                    emit(Effect.NavigateToStart)
                }
            }
        }
    }
}