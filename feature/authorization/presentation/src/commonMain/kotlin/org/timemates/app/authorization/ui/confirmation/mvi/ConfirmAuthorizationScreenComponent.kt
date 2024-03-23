package org.timemates.app.authorization.ui.confirmation.mvi

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable
import com.arkivanov.decompose.ComponentContext
import kotlinx.coroutines.launch
import org.timemates.app.authorization.ui.confirmation.mvi.ConfirmAuthorizationScreenComponent.Action
import org.timemates.app.authorization.ui.confirmation.mvi.ConfirmAuthorizationScreenComponent.Intent
import org.timemates.app.authorization.ui.confirmation.mvi.ConfirmAuthorizationScreenComponent.State
import org.timemates.app.authorization.usecases.ConfirmEmailAuthorizationUseCase
import org.timemates.app.feature.common.Input
import org.timemates.app.feature.common.input
import org.timemates.app.feature.common.isValid
import org.timemates.sdk.authorization.email.types.value.VerificationHash
import org.timemates.sdk.authorization.sessions.types.Authorization
import org.timemates.sdk.authorization.sessions.types.value.ConfirmationCode
import org.timemates.sdk.common.constructor.createOrThrow
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

class ConfirmAuthorizationScreenComponent(
    componentContext: ComponentContext,
    private val verificationHash: VerificationHash,
    private val confirmEmailAuthorizationUseCase: ConfirmEmailAuthorizationUseCase,
) : ComponentContext by componentContext, Container<State, Intent, Action> {

    override val store: Store<State, Intent, Action> = retainedStore(initial = State()) {
        recover { exception ->
            emit(Action.Failure(exception))
            null
        }

        reduce { intent ->
            when (intent) {
                is Intent.CodeChange -> updateState { copy(code = input(intent.code)) }
                Intent.OnConfirmClicked -> {
                    updateState {
                        copy(code = code.validated(ConfirmationCode.factory)).run {
                            when {
                                code.isValid() -> {
                                    sendCodeAsync(ConfirmationCode.factory.createOrThrow(code.value))
                                    copy(isLoading = true)
                                }

                                else -> this
                            }
                        }
                    }
                }
            }
        }
    }

    private fun PipelineContext<State, Intent, Action>.sendCodeAsync(code: ConfirmationCode) {
        launch {
            when (val result = confirmEmailAuthorizationUseCase.execute(verificationHash, code)) {
                ConfirmEmailAuthorizationUseCase.Result.AttemptsExceeded -> {
                    action(Action.TooManyAttempts)
                    updateState { copy(canSendRequest = false) }
                }

                is ConfirmEmailAuthorizationUseCase.Result.Failure ->
                    emit(Action.Failure(result.exception))

                ConfirmEmailAuthorizationUseCase.Result.InvalidCode ->
                    emit(Action.AttemptIsFailed)

                is ConfirmEmailAuthorizationUseCase.Result.Success -> {
                    action(
                        if (result.isNewAccount)
                            Action.NavigateToCreateAccount(verificationHash)
                        else Action.NavigateToHome(result.authorization!!)
                    )
                }
            }
        }
    }

    @Immutable
    data class State(
        val code: Input<String> = input(""),
        val isLoading: Boolean = false,
        val canSendRequest: Boolean = true,
    ) : MVIState

    sealed class Intent : MVIIntent {
        data class CodeChange(val code: String) : Intent()

        data object OnConfirmClicked : Intent()
    }

    sealed class Action : MVIAction {
        data object TooManyAttempts : Action()

        data object AttemptIsFailed : Action()

        data class Failure(val throwable: Throwable) : Action()

        data class NavigateToCreateAccount(
            val verificationHash: VerificationHash,
        ) : Action()

        data class NavigateToHome(
            val authorization: Authorization,
        ) : Action()
    }
}