package org.timemates.app.authorization.dependencies.screens

import com.arkivanov.decompose.ComponentContext
import org.koin.core.annotation.Factory
import org.koin.core.annotation.Module
import org.timemates.app.authorization.dependencies.AuthorizationDataModule
import org.timemates.app.authorization.repositories.AuthorizationsRepository
import org.timemates.app.authorization.ui.configure_account.mvi.ConfigureAccountScreenComponent
import org.timemates.app.authorization.usecases.CreateNewAccountUseCase
import org.timemates.sdk.authorization.email.types.value.VerificationHash

@Module(includes = [AuthorizationDataModule::class])
class ConfigureAccountModule {

    @Factory
    fun createNewAccountUseCase(
        authorizationsRepository: AuthorizationsRepository,
    ): CreateNewAccountUseCase = CreateNewAccountUseCase(authorizationsRepository)

    @Factory
    fun mviComponent(
        componentContext: ComponentContext,
        verificationHash: VerificationHash,
        createNewAccountUseCase: CreateNewAccountUseCase,
    ): ConfigureAccountScreenComponent {
        return ConfigureAccountScreenComponent(
            componentContext = componentContext,
            verificationHash = verificationHash,
            createNewAccountUseCase = createNewAccountUseCase,
        )
    }
}