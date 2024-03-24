package org.timemates.app.authorization.dependencies

import org.koin.core.annotation.Module
import org.timemates.app.authorization.dependencies.screens.AfterStartModule
import org.timemates.app.authorization.dependencies.screens.ConfigureAccountModule
import org.timemates.app.authorization.dependencies.screens.ConfirmAuthorizationModule
import org.timemates.app.authorization.dependencies.screens.InitialAuthorizationModule
import org.timemates.app.authorization.dependencies.screens.NewAccountInfoModule
import org.timemates.app.authorization.dependencies.screens.StartAuthorizationModule

@Module(
    includes = [
        AuthorizationDataModule::class,
        // Screen-related
        AfterStartModule::class,
        ConfigureAccountModule::class,
        ConfirmAuthorizationModule::class,
        InitialAuthorizationModule::class,
        NewAccountInfoModule::class,
        StartAuthorizationModule::class,
    ],
)
class AuthorizationModule