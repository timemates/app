package io.timemates.app.authorization.dependencies.screens

import io.timemates.app.authorization.dependencies.AuthorizationDataModule
import io.timemates.app.authorization.repositories.AuthorizationsRepository
import io.timemates.app.authorization.ui.configure_account.mvi.ConfigureAccountMiddleware
import io.timemates.app.authorization.ui.configure_account.mvi.ConfigureAccountReducer
import io.timemates.app.authorization.ui.configure_account.mvi.ConfigureAccountStateMachine
import io.timemates.app.authorization.usecases.CreateNewAccountUseCase
import io.timemates.app.authorization.validation.UserDescriptionValidator
import io.timemates.app.authorization.validation.UserNameValidator
import io.timemates.sdk.authorization.email.types.value.VerificationHash
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import org.koin.core.annotation.Factory
import org.koin.core.annotation.Module
import org.koin.core.annotation.Singleton

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
    fun stateMachine(
        verificationHash: VerificationHash,
        configureAccountMiddleware: ConfigureAccountMiddleware,
        createNewAccountUseCase: CreateNewAccountUseCase,
        userNameValidator: UserNameValidator,
        userDescriptionValidator: UserDescriptionValidator,
    ): ConfigureAccountStateMachine {
        return ConfigureAccountStateMachine(
            reducer = ConfigureAccountReducer(
                verificationHash = verificationHash,
                createNewAccountUseCase = createNewAccountUseCase,
                userNameValidator = userNameValidator,
                userDescriptionValidator = userDescriptionValidator,
                CoroutineScope(Dispatchers.IO),
            ),
            configureAccountMiddleware,
        )
    }
}