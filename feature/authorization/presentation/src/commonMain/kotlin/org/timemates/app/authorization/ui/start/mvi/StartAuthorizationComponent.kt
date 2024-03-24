package org.timemates.app.authorization.ui.start.mvi

import androidx.compose.runtime.Immutable
import com.arkivanov.decompose.ComponentContext
import kotlinx.coroutines.launch
import org.timemates.app.authorization.ui.start.mvi.StartAuthorizationComponent.Action
import org.timemates.app.authorization.ui.start.mvi.StartAuthorizationComponent.Intent
import org.timemates.app.authorization.ui.start.mvi.StartAuthorizationComponent.State
import org.timemates.app.authorization.usecases.AuthorizeByEmailUseCase
import org.timemates.app.feature.common.Input
import org.timemates.app.feature.common.MVI
import org.timemates.app.feature.common.input
import org.timemates.app.feature.common.isValid
import org.timemates.sdk.authorization.email.types.value.VerificationHash
import org.timemates.sdk.common.constructor.createOrThrow
import org.timemates.sdk.users.profile.types.value.EmailAddress
import pro.respawn.flowmvi.api.Container
import pro.respawn.flowmvi.api.MVIAction
import pro.respawn.flowmvi.api.MVIIntent
import pro.respawn.flowmvi.api.MVIState
import pro.respawn.flowmvi.api.PipelineContext
import pro.respawn.flowmvi.api.Store
import pro.respawn.flowmvi.dsl.emit
import pro.respawn.flowmvi.essenty.dsl.retainedStore
import pro.respawn.flowmvi.plugins.recover
import pro.respawn.flowmvi.plugins.reduce

class StartAuthorizationComponent(
    componentContext: ComponentContext,
    private val authorizeByEmail: AuthorizeByEmailUseCase,
) : ComponentContext by componentContext, MVI<State, Intent, Action> {
    override val store: Store<State, Intent, Action> = retainedStore(initial = State()) {
        recover { exception ->
            emit(Action.Failure(exception))
            null
        }

        reduce { intent ->
            updateState {
                when (intent) {
                    is Intent.EmailChange -> copy(
                        email = input(intent.email),
                    )

                    Intent.OnStartClick -> copy(email = email.validated(EmailAddress.factory)).run {
                        when {
                            email.isValid() -> {
                                authWithEmailAsync(email.value)
                                copy(isLoading = true)
                            }

                            else -> this
                        }
                    }
                }
            }
        }
    }

    private fun PipelineContext<State, Intent, Action>.authWithEmailAsync(email: String) {
        launch {
            when (val result = authorizeByEmail.execute(EmailAddress.factory.createOrThrow(email))) {
                is AuthorizeByEmailUseCase.Result.Success -> {
                    emit(Action.NavigateToConfirmation(result.verificationHash))
                    updateState { copy(isLoading = false) }
                }

                AuthorizeByEmailUseCase.Result.TooManyRequests -> {
                    emit(Action.TooManyAttempts)
                    updateState { copy(isLoading = false) }
                }

                is AuthorizeByEmailUseCase.Result.Failure -> {
                    emit(Action.Failure(result.throwable))
                    updateState { copy(isLoading = false) }
                }
            }
        }
    }

    @Immutable
    data class State(
        val email: Input<String> = input(""),
        val isLoading: Boolean = false,
    ) : MVIState

    sealed class Intent : MVIIntent {
        data class EmailChange(val email: String) : Intent()

        data object OnStartClick : Intent()
    }

    sealed class Action : MVIAction {
        data object TooManyAttempts : Action()

        data class Failure(val throwable: Throwable) : Action()

        data class NavigateToConfirmation(
            val verificationHash: VerificationHash,
        ) : Action()
    }
}