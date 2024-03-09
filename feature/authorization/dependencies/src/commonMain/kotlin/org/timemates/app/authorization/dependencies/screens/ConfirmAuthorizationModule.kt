package org.timemates.app.authorization.dependencies.screens

import com.arkivanov.decompose.ComponentContext
import org.timemates.sdk.authorization.email.types.value.VerificationHash
import org.koin.core.annotation.Factory
import org.koin.core.annotation.Module
import org.koin.core.annotation.Singleton
import org.timemates.app.authorization.dependencies.AuthorizationDataModule
import org.timemates.app.authorization.repositories.AuthorizationsRepository
import org.timemates.app.authorization.ui.confirmation.mvi.ConfirmAuthorizationMiddleware
import org.timemates.app.authorization.ui.confirmation.mvi.ConfirmAuthorizationScreenComponent
import org.timemates.app.authorization.ui.confirmation.mvi.ConfirmAuthorizationsReducer
import org.timemates.app.authorization.usecases.ConfirmEmailAuthorizationUseCase
import org.timemates.app.authorization.validation.ConfirmationCodeValidator

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
    fun mviComponent(
        componentContext: ComponentContext,
        verificationHash: VerificationHash,
        middleware: ConfirmAuthorizationMiddleware,
        confirmEmailAuthorizationUseCase: ConfirmEmailAuthorizationUseCase,
        confirmationCodeValidator: ConfirmationCodeValidator,
    ): ConfirmAuthorizationScreenComponent {
        return ConfirmAuthorizationScreenComponent(
            componentContext = componentContext,
            reducer = ConfirmAuthorizationsReducer(
                verificationHash,
                confirmEmailAuthorizationUseCase,
                confirmationCodeValidator,
            ),
            middleware = middleware,
        )
    }
}
