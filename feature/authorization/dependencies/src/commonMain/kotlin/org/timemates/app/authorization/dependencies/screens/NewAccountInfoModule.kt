package org.timemates.app.authorization.dependencies.screens

import com.arkivanov.decompose.ComponentContext
import org.koin.core.annotation.Factory
import org.koin.core.annotation.Module
import org.timemates.app.authorization.dependencies.AuthorizationDataModule
import org.timemates.app.authorization.ui.new_account_info.mvi.NewAccountInfoScreenComponent
import org.timemates.sdk.authorization.email.types.value.VerificationHash

@Module(includes = [AuthorizationDataModule::class])
class NewAccountInfoModule {
    @Factory
    fun mviComponent(
        componentContext: ComponentContext,
        verificationHash: VerificationHash,
    ): NewAccountInfoScreenComponent {
        return NewAccountInfoScreenComponent(
            componentContext = componentContext,
            verificationHash = verificationHash,
        )
    }
}
