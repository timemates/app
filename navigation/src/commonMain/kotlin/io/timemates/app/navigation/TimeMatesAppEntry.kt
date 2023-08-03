package io.timemates.app.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import com.arkivanov.decompose.extensions.compose.jetbrains.stack.animation.fade
import com.arkivanov.decompose.extensions.compose.jetbrains.stack.animation.plus
import com.arkivanov.decompose.extensions.compose.jetbrains.stack.animation.scale
import com.arkivanov.decompose.extensions.compose.jetbrains.stack.animation.stackAnimation
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.pop
import com.arkivanov.decompose.router.stack.popTo
import com.arkivanov.decompose.router.stack.push
import com.arkivanov.decompose.router.stack.replaceAll
import io.timemates.app.authorization.ui.afterstart.AfterStartScreen
import io.timemates.app.authorization.ui.configure_account.ConfigureAccountScreen
import io.timemates.app.authorization.ui.confirmation.ConfirmAuthorizationScreen
import io.timemates.app.authorization.ui.new_account_info.NewAccountInfoScreen
import io.timemates.app.authorization.ui.start.StartAuthorizationScreen
import io.timemates.app.mvi.compose.stateMachine
import io.timemates.app.style.system.theme.AppTheme
import io.timemates.app.timers.ui.create_timer.CreateTimerScreen
import io.timemates.sdk.authorization.email.types.value.VerificationHash
import io.timemates.sdk.common.constructor.createOrThrow
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.channels.consumeEach
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.receiveAsFlow
import org.koin.core.parameter.parametersOf

@Composable
fun TimeMatesAppEntry(
    navigation: StackNavigation<Screen> = remember { StackNavigation() },
    initialScreen: Screen = Screen.CreateTimer,
    isDarkTheme: Boolean = false,
    navigateToAuthorization: ReceiveChannel<Unit>,
) = AppTheme(isDarkTheme) {

    LaunchedEffect(Unit) {
        navigateToAuthorization.consumeEach {
            navigation.push(Screen.CreateTimer)
        }
    }

    ChildStack(
        source = navigation,
        initialStack = { listOf(initialScreen) },
        animation = stackAnimation(fade() + scale()),
    ) { screen ->
        when (screen) {
            is Screen.ConfirmAuthorization -> ConfirmAuthorizationScreen(
                stateMachine = stateMachine {
                    parametersOf(VerificationHash.createOrThrow(screen.verificationHash))
                },
                onBack = { navigation.pop() },
                navigateToConfiguring = {
                    navigation.replaceAll(Screen.StartAuthorization, Screen.NewAccountInfo(it))
                },
                navigateToHome = {
                    // TODO when home is ready
                },
            )

            Screen.StartAuthorization -> StartAuthorizationScreen(
                stateMachine = stateMachine(),
                onNavigateToConfirmation = {
                    navigation.push(Screen.AfterStart(it.string))
                },
            )

            is Screen.AfterStart -> AfterStartScreen(
                stateMachine = stateMachine {
                    parametersOf(VerificationHash.createOrThrow(screen.verificationHash))
                },
                navigateToConfirmation = {
                    navigation.push(Screen.ConfirmAuthorization(screen.verificationHash))
                },
                navigateToStart = {
                    navigation.pop()
                },
            )

            is Screen.NewAccountInfo -> NewAccountInfoScreen(
                stateMachine = stateMachine {
                    parametersOf(VerificationHash.createOrThrow(screen.verificationHash))
                },
                navigateToConfigure = {
                    navigation.push(Screen.NewAccount(screen.verificationHash))
                },
                navigateToStart = {
                    navigation.popTo(0)
                }
            )

            is Screen.NewAccount -> ConfigureAccountScreen(
                stateMachine = stateMachine {
                    parametersOf(VerificationHash.createOrThrow(screen.verificationHash))
                },
                onBack = {
                    navigation.popTo(0)
                },
                navigateToHome = {
                    // TODO navigation to home when home is ready
                }
            )

            is Screen.CreateTimer -> CreateTimerScreen()
        }
    }
}