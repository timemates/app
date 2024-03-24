package org.timemates.app.users.dependencies

import org.koin.core.annotation.Module

@Module(
    includes = [
        UsersDataModule::class,
    ]
)
class UsersModule