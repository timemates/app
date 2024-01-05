package io.timemates.app.authorization.ui.start.mvi

import io.timemates.app.authorization.ui.start.mvi.StartAuthorizationStateMachine.Effect
import io.timemates.app.authorization.ui.start.mvi.StartAuthorizationStateMachine.Event
import io.timemates.app.authorization.ui.start.mvi.StartAuthorizationStateMachine.State
import io.timemates.app.authorization.usecases.AuthorizeByEmailUseCase
import io.timemates.app.authorization.validation.EmailAddressValidator
import io.timemates.app.foundation.mvi.Reducer
import io.timemates.app.foundation.mvi.ReducerScope
import io.timemates.sdk.common.constructor.createOrThrow
import io.timemates.sdk.users.profile.types.value.EmailAddress
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class StartAuthorizationReducer(
    private val validateEmail: EmailAddressValidator,
    private val authorizeByEmail: AuthorizeByEmailUseCase,
) : Reducer<State, Event, Effect> {
    override fun ReducerScope<Effect>.reduce(state: State, event: Event): State {
        return when (event) {
            is Event.EmailChange -> state.copy(
                email = event.email,
                isEmailInvalid = false,
                isEmailLengthSizeInvalid = false
            )

            Event.OnStartClick -> when (validateEmail.validate(state.email)) {
                EmailAddressValidator.Result.PatternDoesNotMatch ->
                    state.copy(isEmailInvalid = true)

                EmailAddressValidator.Result.SizeViolation ->
                    state.copy(isEmailLengthSizeInvalid = true)

                EmailAddressValidator.Result.Success -> {
                    authorizeWithEmail(state.email, sendEffect, machineScope)
                    state.copy(isLoading = true, isEmailInvalid = false)
                }
            }
        }
    }

    private fun authorizeWithEmail(
        email: String,
        sendEffect: (Effect) -> Unit,
        scope: CoroutineScope,
    ) {
        scope.launch {
            when (val result = authorizeByEmail.execute(EmailAddress.createOrThrow(email))) {
                is AuthorizeByEmailUseCase.Result.Success ->
                    sendEffect(Effect.NavigateToConfirmation(result.verificationHash))

                AuthorizeByEmailUseCase.Result.TooManyRequests ->
                    sendEffect(Effect.TooManyAttempts)

                is AuthorizeByEmailUseCase.Result.Failure ->
                    sendEffect(Effect.Failure(result.throwable))
            }
        }
    }
}