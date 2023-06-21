package io.timemates.app.authorization.ui.start.mvi

import io.timemates.app.authorization.ui.start.mvi.StartAuthorizationStateMachine.Effect
import io.timemates.app.authorization.ui.start.mvi.StartAuthorizationStateMachine.Event
import io.timemates.app.authorization.ui.start.mvi.StartAuthorizationStateMachine.State
import io.timemates.app.authorization.usecases.AuthorizeByEmailUseCase
import io.timemates.app.authorization.validation.EmailAddressValidator
import io.timemates.app.foundation.mvi.Reducer
import io.timemates.sdk.common.constructor.createOrThrow
import io.timemates.sdk.users.profile.types.value.EmailAddress
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class StartAuthorizationReducer(
    private val validateEmail: EmailAddressValidator,
    private val authorizeByEmail: AuthorizeByEmailUseCase,
    private val coroutineScope: CoroutineScope,
) : Reducer<State, Event, Effect> {
    override fun reduce(state: State, event: Event, sendEffect: (Effect) -> Unit): State {
        return when (event) {
            is Event.EmailChange -> state.copy(email = event.email)
            Event.OnStartClick -> when (validateEmail.validate(state.email)) {
                EmailAddressValidator.Result.PatternDoesNotMatch ->
                    state.copy(isEmailInvalid = true)

                EmailAddressValidator.Result.SizeViolation ->
                    state.copy(isEmailLengthSizeInvalid = true)

                EmailAddressValidator.Result.Success -> {
                    authorizeWithEmail(state.email, sendEffect)
                    state.copy(isLoading = true, isEmailInvalid = false)
                }
            }
        }
    }

    private fun authorizeWithEmail(
        email: String,
        sendEffect: (Effect) -> Unit
    ) {
        coroutineScope.launch {
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