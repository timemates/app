package org.timemates.app.authorization.ui.configure_account.mvi

import androidx.compose.runtime.Immutable
import com.arkivanov.decompose.ComponentContext
import kotlinx.coroutines.launch
import org.timemates.app.authorization.ui.configure_account.mvi.ConfigureAccountScreenComponent.Action
import org.timemates.app.authorization.ui.configure_account.mvi.ConfigureAccountScreenComponent.Intent
import org.timemates.app.authorization.ui.configure_account.mvi.ConfigureAccountScreenComponent.State
import org.timemates.app.authorization.usecases.CreateNewAccountUseCase
import org.timemates.app.feature.common.Input
import org.timemates.app.feature.common.input
import org.timemates.app.feature.common.isValid
import org.timemates.sdk.authorization.email.types.value.VerificationHash
import org.timemates.sdk.authorization.sessions.types.Authorization
import org.timemates.sdk.common.constructor.createOrThrow
import org.timemates.sdk.users.profile.types.value.UserDescription
import org.timemates.sdk.users.profile.types.value.UserName
import pro.respawn.flowmvi.api.Container
import pro.respawn.flowmvi.api.MVIAction
import pro.respawn.flowmvi.api.MVIIntent
import pro.respawn.flowmvi.api.MVIState
import pro.respawn.flowmvi.api.PipelineContext
import pro.respawn.flowmvi.api.Store
import pro.respawn.flowmvi.essenty.dsl.retainedStore
import pro.respawn.flowmvi.plugins.recover
import pro.respawn.flowmvi.plugins.reduce

class ConfigureAccountScreenComponent(
    componentContext: ComponentContext,
    private val verificationHash: VerificationHash,
    private val createNewAccountUseCase: CreateNewAccountUseCase,
) : ComponentContext by componentContext, Container<State, Intent, Action> {

    override val store: Store<State, Intent, Action> = retainedStore(initial = State()) {
        recover { exception ->
            action(Action.ShowFailure(exception))
            null
        }

        reduce { intent ->
            updateState {
                when (intent) {
                    is Intent.DescriptionIsChanged -> copy(aboutYou = input(intent.description))
                    is Intent.NameIsChanged -> copy(name = input(intent.name))
                    Intent.OnDoneClicked -> copy(
                        name = name.validated(UserName.factory),
                        aboutYou = aboutYou.validated(UserDescription.factory),
                    ).run {
                        if (name.isValid() && aboutYou.isValid()) {
                            registerUserAsync(
                                name = UserName.factory.createOrThrow(name.value),
                                description = UserDescription.factory.createOrThrow(aboutYou.value),
                            )
                            copy(isLoading = true)
                        } else this
                    }
                }
            }
        }
    }

    private fun PipelineContext<State, Intent, Action>.registerUserAsync(
        name: UserName,
        description: UserDescription,
    ) {
        launch {
            when (val result = createNewAccountUseCase.execute(verificationHash, name, description)) {
                is CreateNewAccountUseCase.Result.Failure -> {
                    action(Action.ShowFailure(result.exception))
                }

                is CreateNewAccountUseCase.Result.Success ->
                    action(Action.NavigateToHomePage(result.authorization))
            }

            updateState { copy(isLoading = false) }
        }
    }

    @Immutable
    data class State(
        val name: Input<String> = input(""),
        val aboutYou: Input<String> = input(""),
        val isLoading: Boolean = false,
    ) : MVIState

    sealed class Intent : MVIIntent {
        data class NameIsChanged(val name: String) : Intent()

        data class DescriptionIsChanged(val description: String) : Intent()

        data object OnDoneClicked : Intent()
    }

    sealed class Action : MVIAction {
        data class ShowFailure(val throwable: Throwable) : Action()

        data object NavigateToStart : Action()

        data class NavigateToHomePage(
            val authorization: Authorization,
        ) : Action()
    }
}