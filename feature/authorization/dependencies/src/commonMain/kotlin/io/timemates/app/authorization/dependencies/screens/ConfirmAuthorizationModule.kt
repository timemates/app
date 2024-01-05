package io.timemates.app.authorization.dependencies.screens

import io.timemates.app.authorization.dependencies.AuthorizationDataModule
import io.timemates.app.authorization.repositories.AuthorizationsRepository
import io.timemates.app.authorization.ui.confirmation.mvi.ConfirmAuthorizationMiddleware
import io.timemates.app.authorization.ui.confirmation.mvi.ConfirmAuthorizationStateMachine
import io.timemates.app.authorization.ui.confirmation.mvi.ConfirmAuthorizationsReducer
import io.timemates.app.authorization.usecases.ConfirmEmailAuthorizationUseCase
import io.timemates.app.authorization.validation.ConfirmationCodeValidator
import io.timemates.sdk.authorization.email.types.value.VerificationHash
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import org.koin.core.annotation.Factory
import org.koin.core.annotation.Module
import org.koin.core.annotation.Singleton

@Module(includes = [AuthorizationDataModule::class])
class ConfirmAuthorizationModule {

    @Singleton
    fun confirmationCodeValidator(): ConfirmationCodeValidator = ConfirmationCodeValidator()

    @Singleton
    fun middleware(): ConfirmAuthorizationMiddleware = ConfirmAuthorizationMiddleware()

    @Factory
    fun confirmAuthorizationUseCase(
        authorizationsRepository: AuthorizationsRepository,
    ): ConfirmEmailAuthorizationUseCase = ConfirmEmailAuthorizationUseCase(authorizationsRepository)

    @Factory
    fun stateMachine(
        verificationHash: VerificationHash,
        middleware: ConfirmAuthorizationMiddleware,
        confirmEmailAuthorizationUseCase: ConfirmEmailAuthorizationUseCase,
        confirmationCodeValidator: ConfirmationCodeValidator,
    ): ConfirmAuthorizationStateMachine {
        return ConfirmAuthorizationStateMachine(
            reducer = ConfirmAuthorizationsReducer(
                verificationHash,
                confirmEmailAuthorizationUseCase,
                confirmationCodeValidator,
            ),
            middleware = middleware,
        )
    }
}
