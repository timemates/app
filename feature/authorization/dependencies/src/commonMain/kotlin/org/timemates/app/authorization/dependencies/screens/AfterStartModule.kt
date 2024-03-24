package org.timemates.app.authorization.dependencies.screens

import com.arkivanov.decompose.ComponentContext
import org.koin.core.annotation.Factory
import org.koin.core.annotation.Module
import org.timemates.app.authorization.dependencies.AuthorizationDataModule
import org.timemates.app.authorization.ui.afterstart.mvi.AfterStartScreenComponent
import org.timemates.sdk.authorization.email.types.value.VerificationHash

@Module(includes = [AuthorizationDataModule::class])
class AfterStartModule {
    @Factory
    fun mviComponent(
        componentContext: ComponentContext,
        verificationHash: VerificationHash,
    ): AfterStartScreenComponent {
        return AfterStartScreenComponent(
            componentContext = componentContext,
            verificationHash = verificationHash,
        )
    }
}