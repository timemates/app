package io.timemates.app.authorization.dependencies.screens

import io.timemates.app.authorization.dependencies.AuthorizationDataModule
import io.timemates.app.authorization.ui.new_account_info.mvi.NewAccountInfoReducer
import io.timemates.app.authorization.ui.new_account_info.mvi.NewAccountInfoStateMachine
import io.timemates.sdk.authorization.email.types.value.VerificationHash
import org.koin.core.annotation.Factory
import org.koin.core.annotation.Module

@Module(includes = [AuthorizationDataModule::class])
class NewAccountInfoModule {
    @Factory
    fun stateMachine(
        verificationHash: VerificationHash,
    ): NewAccountInfoStateMachine {
        return NewAccountInfoStateMachine(
            reducer = NewAccountInfoReducer(
                verificationHash = verificationHash
            ),
        )
    }
}
