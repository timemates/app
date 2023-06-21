package io.timemates.app.authorization.dependencies.screens

import io.timemates.app.authorization.dependencies.AuthorizationDataModule
import io.timemates.app.authorization.repositories.AuthorizationRepository
import io.timemates.app.authorization.ui.start.mvi.StartAuthorizationMiddleware
import io.timemates.app.authorization.ui.start.mvi.StartAuthorizationReducer
import io.timemates.app.authorization.ui.start.mvi.StartAuthorizationStateMachine
import io.timemates.app.authorization.usecases.AuthorizeByEmailUseCase
import io.timemates.app.authorization.validation.EmailAddressValidator
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import org.koin.core.annotation.Module
import org.koin.core.annotation.Singleton

@Module(includes = [AuthorizationDataModule::class])
class StartAuthorizationModule {
    @Singleton
    fun emailValidator(): EmailAddressValidator = EmailAddressValidator()

    @Singleton
    fun authByEmailUseCase(authorizationRepository: AuthorizationRepository): AuthorizeByEmailUseCase =
        AuthorizeByEmailUseCase(authorizationRepository)

    @Singleton
    fun reducer(
        emailAddressValidator: EmailAddressValidator,
        authorizeByEmailUseCase: AuthorizeByEmailUseCase,
    ): StartAuthorizationReducer {
        return StartAuthorizationReducer(
            validateEmail = emailAddressValidator,
            authorizeByEmail = authorizeByEmailUseCase,
            coroutineScope = CoroutineScope(Dispatchers.Default),
        )
    }

    @Singleton
    fun middleware(): StartAuthorizationMiddleware = StartAuthorizationMiddleware()

    @Singleton
    fun stateMachine(
        reducer: StartAuthorizationReducer,
        middleware: StartAuthorizationMiddleware,
    ): StartAuthorizationStateMachine {
        return StartAuthorizationStateMachine(
            reducer = reducer,
            middleware = middleware,
        )
    }
}