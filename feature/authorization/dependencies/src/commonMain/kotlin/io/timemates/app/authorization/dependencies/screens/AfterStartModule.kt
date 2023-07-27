package io.timemates.app.authorization.dependencies.screens

import io.timemates.app.authorization.dependencies.AuthorizationDataModule
import io.timemates.app.authorization.ui.afterstart.mvi.AfterStartReducer
import io.timemates.app.authorization.ui.afterstart.mvi.AfterStartStateMachine
import io.timemates.sdk.authorization.email.types.value.VerificationHash
import org.koin.core.annotation.Factory
import org.koin.core.annotation.Module

@Module(includes = [AuthorizationDataModule::class])
class AfterStartModule {
    @Factory
    fun stateMachine(
        verificationHash: VerificationHash,
    ): AfterStartStateMachine {
        return AfterStartStateMachine(
            reducer = AfterStartReducer(
                verificationHash = verificationHash
            ),
        )
    }
}