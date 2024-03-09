package org.timemates.app.authorization.ui.confirmation.mvi

import org.timemates.sdk.authorization.email.types.value.VerificationHash
import org.timemates.sdk.authorization.sessions.types.value.ConfirmationCode
import org.timemates.sdk.common.constructor.createOrThrow
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import org.timemates.app.authorization.ui.confirmation.mvi.ConfirmAuthorizationScreenComponent.Effect
import org.timemates.app.authorization.ui.confirmation.mvi.ConfirmAuthorizationScreenComponent.Event
import org.timemates.app.authorization.ui.confirmation.mvi.ConfirmAuthorizationScreenComponent.State
import org.timemates.app.authorization.usecases.ConfirmEmailAuthorizationUseCase
import org.timemates.app.authorization.validation.ConfirmationCodeValidator
import org.timemates.app.foundation.mvi.Reducer
import org.timemates.app.foundation.mvi.ReducerScope

class ConfirmAuthorizationsReducer(
    private val verificationHash: VerificationHash,
    private val confirmEmailAuthorizationUseCase: ConfirmEmailAuthorizationUseCase,
    private val confirmationCodeValidator: ConfirmationCodeValidator,
) : Reducer<State, Event, Effect> {
    override fun ReducerScope<Effect>.reduce(state: State, event: Event): State {
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
                        confirm(machineScope, ConfirmationCode.createOrThrow(state.code), sendEffect)
                        state.copy(isLoading = true)
                    }
                }
            }

            is Event.CodeChange -> state.copy(code = event.code)
            Event.OnConfirmClicked -> state
        }
    }

    private fun confirm(
        scope: CoroutineScope,
        code: ConfirmationCode,
        sendEffect: (Effect) -> Unit,
    ) {
        scope.launch {
            when (val result = confirmEmailAuthorizationUseCase.execute(verificationHash, code)) {
                ConfirmEmailAuthorizationUseCase.Result.AttemptsExceeded ->
                    sendEffect(Effect.TooManyAttempts)

                is ConfirmEmailAuthorizationUseCase.Result.Failure ->
                    sendEffect(Effect.Failure(result.exception))

                ConfirmEmailAuthorizationUseCase.Result.InvalidCode ->
                    sendEffect(Effect.AttemptIsFailed)

                is ConfirmEmailAuthorizationUseCase.Result.Success -> {
                    if (result.isNewAccount) {
                        sendEffect(Effect.NavigateToCreateAccount(verificationHash))
                    } else {
                        sendEffect(Effect.NavigateToHome(result.authorization!!))
                    }
                }
            }
        }
    }
}