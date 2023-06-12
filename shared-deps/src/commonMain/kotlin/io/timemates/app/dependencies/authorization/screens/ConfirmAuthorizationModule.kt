package io.timemates.app.dependencies.authorization.screens

import io.timemates.app.authorization.confirmation.mvi.ConfirmAuthorizationMiddleware
import io.timemates.app.authorization.confirmation.mvi.ConfirmAuthorizationStateMachine
import io.timemates.app.authorization.confirmation.mvi.ConfirmAuthorizationsReducer
import io.timemates.app.common.mvi.middleware.AuthorizationFailureMiddleware
import io.timemates.app.core.handler.OnAuthorizationFailedHandler
import io.timemates.app.core.repositories.AuthorizationRepository
import io.timemates.app.core.validation.ConfirmationCodeValidator
import io.timemates.app.dependencies.authorization.AuthorizationDataModule
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
    fun stateMachine(
        verificationHash: VerificationHash,
        middleware: ConfirmAuthorizationMiddleware,
        authorizationsRepository: AuthorizationRepository,
        confirmationCodeValidator: ConfirmationCodeValidator,
    ): ConfirmAuthorizationStateMachine {
        return ConfirmAuthorizationStateMachine(
            reducer = ConfirmAuthorizationsReducer(
                verificationHash,
                authorizationsRepository,
                confirmationCodeValidator,
                CoroutineScope(Dispatchers.IO),
            ),
            middleware = middleware,
        )
    }
}