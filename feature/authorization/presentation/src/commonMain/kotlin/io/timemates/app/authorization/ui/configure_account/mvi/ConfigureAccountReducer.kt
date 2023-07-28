package io.timemates.app.authorization.ui.configure_account.mvi

import io.timemates.app.authorization.ui.configure_account.mvi.ConfigureAccountStateMachine.Effect
import io.timemates.app.authorization.ui.configure_account.mvi.ConfigureAccountStateMachine.Event
import io.timemates.app.authorization.ui.configure_account.mvi.ConfigureAccountStateMachine.State
import io.timemates.app.authorization.usecases.CreateNewAccountUseCase
import io.timemates.app.authorization.validation.UserDescriptionValidator
import io.timemates.app.authorization.validation.UserNameValidator
import io.timemates.app.foundation.mvi.Reducer
import io.timemates.sdk.authorization.email.types.value.VerificationHash
import io.timemates.sdk.common.constructor.createOrThrow
import io.timemates.sdk.users.profile.types.value.UserDescription
import io.timemates.sdk.users.profile.types.value.UserName
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class ConfigureAccountReducer(
    private val verificationHash: VerificationHash,
    private val createNewAccountUseCase: CreateNewAccountUseCase,
    private val userNameValidator: UserNameValidator,
    private val userDescriptionValidator: UserDescriptionValidator,
    private val coroutineScope: CoroutineScope,
) : Reducer<State, Event, Effect> {
    override fun reduce(
        state: State,
        event: Event,
        sendEffect: (Effect) -> Unit,
    ): State {
        return when (event) {
            Event.OnDoneClicked -> {
                val name = when (userNameValidator.validate(state.name)) {
                    is UserNameValidator.Result.SizeViolation ->
                        return state.copy(isNameSizeInvalid = true)

                    else -> UserName.createOrThrow(state.name)
                }
                val description = when (userDescriptionValidator.validate(state.aboutYou)) {
                    is UserDescriptionValidator.Result.SizeViolation ->
                        return state.copy(isAboutYouSizeInvalid = true)

                    else -> UserDescription.createOrThrow(state.aboutYou)
                }

                completeRegistration(name, description, sendEffect)
                return state.copy(isLoading = true)
            }

            is Event.NameIsChanged ->
                state.copy(name = event.name, isNameSizeInvalid = false)

            is Event.DescriptionIsChanged ->
                state.copy(aboutYou = event.description, isAboutYouSizeInvalid = false)
        }
    }

    private fun completeRegistration(
        name: UserName,
        description: UserDescription,
        sendEffect: (Effect) -> Unit,
    ) {
        coroutineScope.launch {
            when (val result = createNewAccountUseCase.execute(verificationHash, name, description)) {
                is CreateNewAccountUseCase.Result.Failure ->
                    sendEffect(Effect.Failure(result.exception))

                is CreateNewAccountUseCase.Result.Success ->
                    sendEffect(Effect.NavigateToHomePage(result.authorization))
            }
        }
    }
}