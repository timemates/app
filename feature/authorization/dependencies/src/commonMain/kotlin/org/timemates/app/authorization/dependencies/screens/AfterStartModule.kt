package org.timemates.app.authorization.dependencies.screens

import com.arkivanov.decompose.ComponentContext
import io.timemates.sdk.authorization.email.types.value.VerificationHash
import org.koin.core.annotation.Factory
import org.koin.core.annotation.Module
import org.timemates.app.authorization.dependencies.AuthorizationDataModule
import org.timemates.app.authorization.ui.afterstart.mvi.AfterStartReducer
import org.timemates.app.authorization.ui.afterstart.mvi.AfterStartScreenComponent

@Module(includes = [AuthorizationDataModule::class])
class AfterStartModule {
    @Factory
    fun mviComponent(
        componentContext: ComponentContext,
        verificationHash: VerificationHash,
    ): AfterStartScreenComponent {
        return AfterStartScreenComponent(
            componentContext,
            reducer = AfterStartReducer(
                verificationHash = verificationHash
            ),
        )
    }
}