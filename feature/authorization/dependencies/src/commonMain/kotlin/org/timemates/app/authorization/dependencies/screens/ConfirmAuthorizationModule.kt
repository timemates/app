package org.timemates.app.authorization.dependencies.screens

import com.arkivanov.decompose.ComponentContext
import org.koin.core.annotation.Factory
import org.koin.core.annotation.Module
import org.timemates.app.authorization.dependencies.AuthorizationDataModule
import org.timemates.app.authorization.repositories.AuthorizationsRepository
import org.timemates.app.authorization.ui.confirmation.mvi.ConfirmAuthorizationScreenComponent
import org.timemates.app.authorization.usecases.ConfirmEmailAuthorizationUseCase
import org.timemates.sdk.authorization.email.types.value.VerificationHash

@Module(includes = [AuthorizationDataModule::class])
class ConfirmAuthorizationModule {

    @Factory
    fun confirmAuthorizationUseCase(
        authorizationsRepository: AuthorizationsRepository,
    ): ConfirmEmailAuthorizationUseCase = ConfirmEmailAuthorizationUseCase(authorizationsRepository)

    @Factory
    fun mviComponent(
        componentContext: ComponentContext,
        verificationHash: VerificationHash,
        confirmEmailAuthorizationUseCase: ConfirmEmailAuthorizationUseCase,
    ): ConfirmAuthorizationScreenComponent {
        return ConfirmAuthorizationScreenComponent(
            componentContext = componentContext,
            verificationHash = verificationHash,
            confirmEmailAuthorizationUseCase = confirmEmailAuthorizationUseCase,
        )
    }
}
