package org.timemates.app.feature.common.startup.mvi

import com.arkivanov.decompose.ComponentContext
import org.timemates.app.feature.common.MVI
import org.timemates.app.feature.common.startup.mvi.StartupScreenMVIComponent.Action
import org.timemates.app.feature.common.startup.mvi.StartupScreenMVIComponent.Intent
import org.timemates.app.feature.common.startup.mvi.StartupScreenMVIComponent.State
import org.timemates.app.feature.splash.repositories.AuthRepository
import pro.respawn.flowmvi.api.Container
import pro.respawn.flowmvi.api.MVIAction
import pro.respawn.flowmvi.api.MVIIntent
import pro.respawn.flowmvi.api.MVIState
import pro.respawn.flowmvi.api.Store
import pro.respawn.flowmvi.essenty.dsl.retainedStore
import pro.respawn.flowmvi.plugins.init

/**
 * The global app state machine. Responsible for checking whether user is authorized
 * and for a new updates (TODO).
 */
class StartupScreenMVIComponent(
    componentContext: ComponentContext,
    authRepository: AuthRepository,
) : ComponentContext by componentContext, MVI<State, Intent, Action> {

    override val store: Store<State, Intent, Action> = retainedStore(initial = State) {
        init {
            if (authRepository.isAuthorized())
                action(Action.GoToMainScreen)
            else action(Action.GoToAuthorization)
        }
    }

    data object State : MVIState

    sealed class Intent : MVIIntent

    sealed class Action : MVIAction {
        /**
         * Indicates that user is not authorized and should be moved to the
         * authorization screen.
         */
        data object GoToAuthorization : Action()

        /**
         * Indicates that user is authorized and should be moved to the
         * home screen.
         */
        data object GoToMainScreen : Action()
    }
}

