package io.timemates.app.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import com.arkivanov.decompose.extensions.compose.jetbrains.stack.animation.fade
import com.arkivanov.decompose.extensions.compose.jetbrains.stack.animation.plus
import com.arkivanov.decompose.extensions.compose.jetbrains.stack.animation.scale
import com.arkivanov.decompose.extensions.compose.jetbrains.stack.animation.stackAnimation
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.push
import io.timemates.app.authorization.ui.confirmation.ConfirmAuthorizationScreen
import io.timemates.app.authorization.ui.start.StartAuthorizationScreen
import io.timemates.app.mvi.compose.stateMachine
import io.timemates.app.style.system.theme.AppTheme
import io.timemates.sdk.authorization.email.types.value.VerificationHash
import io.timemates.sdk.common.constructor.createOrThrow
import org.koin.core.parameter.parametersOf

@Composable
fun TimeMatesAppEntry(
    initialScreen: Screen = Screen.StartAuthorization,
    isDarkTheme: Boolean = false
) = AppTheme(isDarkTheme) {
    val navigation = remember { StackNavigation<Screen>() }

    ChildStack(
        source = navigation,
        initialStack = { listOf(initialScreen) },
        animation = stackAnimation(fade() + scale()),
    ) { screen ->
        when (screen) {
            is Screen.ConfirmAuthorization -> ConfirmAuthorizationScreen(
                stateMachine = stateMachine {
                    parametersOf(VerificationHash.createOrThrow(screen.verificationHash))
                }
            )

            Screen.StartAuthorization -> StartAuthorizationScreen(
                stateMachine = stateMachine(),
                onNavigateToConfirmation = {
                    navigation.push(Screen.ConfirmAuthorization(it.string))
                }
            )
        }
    }
}