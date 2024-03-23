package org.timemates.app.authorization.dependencies.screens

import com.arkivanov.decompose.ComponentContext
import org.koin.core.annotation.Factory
import org.koin.core.annotation.Module
import org.timemates.app.authorization.dependencies.AuthorizationDataModule
import org.timemates.app.authorization.repositories.AuthorizationsRepository
import org.timemates.app.authorization.ui.start.mvi.StartAuthorizationComponent
import org.timemates.app.authorization.usecases.AuthorizeByEmailUseCase

@Module(includes = [AuthorizationDataModule::class])
class StartAuthorizationModule {

    @Factory
    fun authByEmailUseCase(authorizationsRepository: AuthorizationsRepository): AuthorizeByEmailUseCase =
        AuthorizeByEmailUseCase(authorizationsRepository)

    @Factory
    fun mviComponent(
        componentContext: ComponentContext,
        authorizeByEmailUseCase: AuthorizeByEmailUseCase,
    ): StartAuthorizationComponent {
        return StartAuthorizationComponent(
            componentContext = componentContext,
            authorizeByEmail = authorizeByEmailUseCase,
        )
    }
}