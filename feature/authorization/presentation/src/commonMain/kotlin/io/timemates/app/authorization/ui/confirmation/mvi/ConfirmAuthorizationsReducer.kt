package io.timemates.app.authorization.ui.confirmation.mvi

import io.timemates.app.authorization.repositories.AuthorizationsRepository
import io.timemates.app.authorization.ui.confirmation.mvi.ConfirmAuthorizationStateMachine.Effect
import io.timemates.app.authorization.ui.confirmation.mvi.ConfirmAuthorizationStateMachine.Event
import io.timemates.app.authorization.ui.confirmation.mvi.ConfirmAuthorizationStateMachine.State
import io.timemates.app.authorization.validation.ConfirmationCodeValidator
import io.timemates.app.foundation.mvi.Reducer
import io.timemates.sdk.authorization.email.types.value.VerificationHash
import io.timemates.sdk.authorization.sessions.types.value.ConfirmationCode
import io.timemates.sdk.common.constructor.createOrThrow
import io.timemates.sdk.common.exceptions.TooManyRequestsException
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class ConfirmAuthorizationsReducer(
    private val verificationHash: VerificationHash,
    private val authorizationsRepository: AuthorizationsRepository,
    private val confirmationCodeValidator: ConfirmationCodeValidator,
    private val coroutineScope: CoroutineScope,
) : Reducer<State, Event, Effect> {
    override fun reduce(
        state: State,
        event: Event,
        sendEffect: (Effect) -> Unit
    ): State {
        return when (event) {
            Event.OnConfirmClicked -> {
                when (confirmationCodeValidator.validate(state.code)) {
                    ConfirmationCodeValidator.Result.SizeIsInvalid -> state.copy(
                        isCodeSizeInvalid = true,
                        isCodeInvalid = false,
                    )

                    ConfirmationCodeValidator.Result.PatternFailure -> state.copy(
                        isCodeSizeInvalid = false,
                        isCodeInvalid = true,
                    )

                    ConfirmationCodeValidator.Result.Success -> {
                        confirm(state.code, sendEffect)
                        state.copy(isLoading = true)
                    }
                }
            }

            is Event.CodeChange -> state.copy(code = event.code)
            Event.OnConfirmClicked -> state
        }
    }

    private fun confirm(
        code: String,
        sendEffect: (Effect) -> Unit,
    ) {
        coroutineScope.launch {
            authorizationsRepository.confirm(verificationHash, ConfirmationCode.createOrThrow(code))
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