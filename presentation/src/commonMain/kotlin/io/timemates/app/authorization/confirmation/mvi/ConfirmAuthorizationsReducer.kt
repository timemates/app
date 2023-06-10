package io.timemates.app.authorization.confirmation.mvi

import io.timemates.app.authorization.confirmation.mvi.ConfirmAuthorizationStateMachine.Effect
import io.timemates.app.authorization.confirmation.mvi.ConfirmAuthorizationStateMachine.Event
import io.timemates.app.authorization.confirmation.mvi.ConfirmAuthorizationStateMachine.State
import io.timemates.app.core.repositories.AuthorizationRepository
import io.timemates.common.mvi.Reducer
import io.timemates.sdk.authorization.email.types.value.VerificationHash
import io.timemates.sdk.authorization.sessions.types.value.ConfirmationCode
import io.timemates.sdk.common.constructor.createOrThrow
import io.timemates.sdk.common.exceptions.TooManyRequestsException
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class ConfirmAuthorizationsReducer(
    private val verificationHash: VerificationHash,
    private val authorizationRepository: AuthorizationRepository,
    private val coroutineScope: CoroutineScope,
) : Reducer<State, Event, Effect> {
    override fun reduce(state: State, event: Event, sendEffect: (Effect) -> Unit): State {
        return when (event) {
            Event.OnConfirmClicked -> {
                if (state.code.length != 6)
                    state.copy(isCodeSizeInvalid = true)
                else {
                    confirm(state.code, sendEffect)
                    state.copy(isLoading = true)
                }
            }

            is Event.CodeChange -> state.copy(code = event.code)
        }
    }

    private fun confirm(code: String, sendEffect: (Effect) -> Unit) {
        coroutineScope.launch {
            authorizationRepository.confirm(verificationHash, ConfirmationCode.createOrThrow(code))
                .onSuccess {
                    if (it.isNewAccount)
                        sendEffect(Effect.NavigateToCreateAccount(verificationHash))
                    else sendEffect(Effect.NavigateToHome(it.authorization!!))
                }
                .onFailure {
                    sendEffect(
                        when (it) {
                            is TooManyRequestsException -> Effect.TooManyAttempts
                            else -> Effect.Failure(it)
                        }
                    )
                }
        }
    }
}