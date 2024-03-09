package org.timemates.app.authorization.dependencies.screens

import com.arkivanov.decompose.ComponentContext
import org.koin.core.annotation.Factory
import org.koin.core.annotation.Module
import org.koin.core.annotation.Singleton
import org.timemates.app.authorization.dependencies.AuthorizationDataModule
import org.timemates.app.authorization.repositories.AuthorizationsRepository
import org.timemates.app.authorization.ui.start.mvi.StartAuthorizationScreenComponent
import org.timemates.app.authorization.ui.start.mvi.StartAuthorizationMiddleware
import org.timemates.app.authorization.ui.start.mvi.StartAuthorizationReducer
import org.timemates.app.authorization.usecases.AuthorizeByEmailUseCase
import org.timemates.app.authorization.validation.EmailAddressValidator

@Module(includes = [AuthorizationDataModule::class])
class StartAuthorizationModule {
    @Singleton
    fun emailValidator(): EmailAddressValidator = EmailAddressValidator()

    @Factory
    fun authByEmailUseCase(authorizationsRepository: AuthorizationsRepository): AuthorizeByEmailUseCase =
        AuthorizeByEmailUseCase(authorizationsRepository)

    @Factory
    fun reducer(
        emailAddressValidator: EmailAddressValidator,
        authorizeByEmailUseCase: AuthorizeByEmailUseCase,
    ): StartAuthorizationReducer {
        return StartAuthorizationReducer(
            validateEmail = emailAddressValidator,
            authorizeByEmail = authorizeByEmailUseCase,
        )
    }

    @Singleton
    fun middleware(): StartAuthorizationMiddleware = StartAuthorizationMiddleware()

    @Factory
    fun mviComponent(
        componentContext: ComponentContext,
        reducer: StartAuthorizationReducer,
        middleware: StartAuthorizationMiddleware,
    ): StartAuthorizationScreenComponent {
        return StartAuthorizationScreenComponent(
            componentContext = componentContext,
            reducer = reducer,
            middleware = middleware,
        )
    }
}