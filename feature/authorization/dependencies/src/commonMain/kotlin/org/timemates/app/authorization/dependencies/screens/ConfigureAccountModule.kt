package org.timemates.app.authorization.dependencies.screens

import com.arkivanov.decompose.ComponentContext
import io.timemates.sdk.authorization.email.types.value.VerificationHash
import org.koin.core.annotation.Factory
import org.koin.core.annotation.Module
import org.koin.core.annotation.Singleton
import org.timemates.app.authorization.dependencies.AuthorizationDataModule
import org.timemates.app.authorization.repositories.AuthorizationsRepository
import org.timemates.app.authorization.ui.configure_account.mvi.ConfigureAccountMiddleware
import org.timemates.app.authorization.ui.configure_account.mvi.ConfigureAccountReducer
import org.timemates.app.authorization.ui.configure_account.mvi.ConfigureAccountScreenComponent
import org.timemates.app.authorization.usecases.CreateNewAccountUseCase
import org.timemates.app.authorization.validation.UserDescriptionValidator
import org.timemates.app.authorization.validation.UserNameValidator

@Module(includes = [AuthorizationDataModule::class])
class ConfigureAccountModule {

    @Singleton
    fun configureAccountMiddleware(): ConfigureAccountMiddleware = ConfigureAccountMiddleware()

    @Factory
    fun createNewAccountUseCase(
        authorizationsRepository: AuthorizationsRepository,
    ): CreateNewAccountUseCase = CreateNewAccountUseCase(authorizationsRepository)

    @Singleton
    fun userNameValidator(): UserNameValidator = UserNameValidator()

    @Singleton
    fun userDescriptionValidator(): UserDescriptionValidator = UserDescriptionValidator()

    @Factory
    fun mviComponent(
        componentContext: ComponentContext,
        verificationHash: VerificationHash,
        configureAccountMiddleware: ConfigureAccountMiddleware,
        createNewAccountUseCase: CreateNewAccountUseCase,
        userNameValidator: UserNameValidator,
        userDescriptionValidator: UserDescriptionValidator,
    ): ConfigureAccountScreenComponent {
        return ConfigureAccountScreenComponent(
            componentContext = componentContext,
            reducer = ConfigureAccountReducer(
                verificationHash = verificationHash,
                createNewAccountUseCase = createNewAccountUseCase,
                userNameValidator = userNameValidator,
                userDescriptionValidator = userDescriptionValidator,
            ),
            middleware = configureAccountMiddleware,
        )
    }
}