package io.timemates.app.authorization.start.mvi

import io.timemates.app.authorization.start.mvi.StartAuthorizationStateMachine.Effect
import io.timemates.app.authorization.start.mvi.StartAuthorizationStateMachine.Event
import io.timemates.app.authorization.start.mvi.StartAuthorizationStateMachine.State
import io.timemates.app.core.usecases.authorization.AuthorizeByEmailUseCase
import io.timemates.app.core.validation.EmailAddressValidator
import io.timemates.common.mvi.Reducer
import io.timemates.sdk.common.constructor.createOrThrow
import io.timemates.sdk.common.exceptions.TooManyRequestsException
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
                    state.copy(isLoading = true)
                }
            }
        }
    }

    private fun authorizeWithEmail(
        email: String,
        sendEffect: (Effect) -> Unit
    ) {
        coroutineScope.launch {
            authorizeByEmail.execute(EmailAddress.createOrThrow(email))
                .onSuccess {
                    sendEffect(Effect.NavigateToConfirmation(it))
                }
                .onFailure {
                    when (it) {
                        is TooManyRequestsException -> sendEffect(Effect.TooManyAttempts)
                        else -> sendEffect(Effect.Failure(it))
                    }
                }
        }
    }
}