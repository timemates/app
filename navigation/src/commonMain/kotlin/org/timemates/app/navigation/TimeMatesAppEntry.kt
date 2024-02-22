package org.timemates.app.navigation

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
import io.timemates.sdk.authorization.email.types.value.VerificationHash
import io.timemates.sdk.common.constructor.createOrThrow
import io.timemates.sdk.common.exceptions.UnauthorizedException
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.channels.consumeEach
import org.koin.core.parameter.parametersOf
import org.timemates.app.authorization.ui.afterstart.AfterStartScreen
import org.timemates.app.authorization.ui.afterstart.mvi.AfterStartScreenComponent
import org.timemates.app.authorization.ui.configure_account.ConfigureAccountScreen
import org.timemates.app.authorization.ui.configure_account.mvi.ConfigureAccountScreenComponent
import org.timemates.app.authorization.ui.confirmation.ConfirmAuthorizationScreen
import org.timemates.app.authorization.ui.confirmation.mvi.ConfirmAuthorizationScreenComponent
import org.timemates.app.authorization.ui.initial_authorization.InitialAuthorizationScreen
import org.timemates.app.authorization.ui.initial_authorization.mvi.InitialAuthorizationScreenComponent
import org.timemates.app.authorization.ui.new_account_info.NewAccountInfoScreen
import org.timemates.app.authorization.ui.new_account_info.mvi.NewAccountInfoScreenComponent
import org.timemates.app.authorization.ui.start.StartAuthorizationScreen
import org.timemates.app.authorization.ui.start.mvi.StartAuthorizationComponent
import org.timemates.app.feature.common.startup.StartupScreen
import org.timemates.app.feature.common.startup.mvi.StartupScreenMVIComponent
import org.timemates.app.mvi.compose.koinMviComponent
import org.timemates.app.timers.ui.settings.TimerSettingsScreen
import org.timemates.app.timers.ui.settings.mvi.TimerSettingsScreenComponent
import org.timemates.app.timers.ui.timer_creation.TimerCreationScreen
import org.timemates.app.timers.ui.timer_creation.mvi.TimerCreationScreenComponent
import org.timemates.app.timers.ui.timers_list.TimersListScreen
import org.timemates.app.timers.ui.timers_list.mvi.TimersListScreenComponent

@Composable
fun TimeMatesAppEntry(
    navigation: StackNavigation<Screen> = remember { StackNavigation() },
    initialScreen: Screen = Screen.Startup,
    navigateToAuthorization: ReceiveChannel<UnauthorizedException>,
) {
    LaunchedEffect(Unit) {
        navigateToAuthorization.consumeEach {
            navigation.push(Screen.InitialAuthorizationScreen)
        }
    }

    val componentContext = LocalComponentContext.current

    ChildStack(
        source = navigation,
        initialStack = { listOf(initialScreen) },
        animation = stackAnimation(fade() + scale()),
    ) { screen ->
        when (screen) {
            is Screen.Startup -> StartupScreen(
                mvi = koinMviComponent<StartupScreenMVIComponent> {
                    parametersOf(componentContext)
                },
                navigateToAuth = { navigation.push(Screen.InitialAuthorizationScreen) },
                navigateToHome = { navigation.replaceAll(Screen.TimersList) },
            )

            is Screen.ConfirmAuthorization -> ConfirmAuthorizationScreen(
                mvi = koinMviComponent<ConfirmAuthorizationScreenComponent> {
                    parametersOf(VerificationHash.createOrThrow(screen.verificationHash))
                },
                onBack = { navigation.pop() },
                navigateToConfiguring = {
                    navigation.replaceAll(Screen.StartAuthorization, Screen.NewAccountInfo(it))
                },
                navigateToHome = {
                    navigation.replaceAll(Screen.TimersList)
                },
            )

            Screen.InitialAuthorizationScreen -> InitialAuthorizationScreen(
                mvi = koinMviComponent<InitialAuthorizationScreenComponent>(),
                navigateToStartAuthorization = {
                    navigation.push(Screen.StartAuthorization)
                },
            )

            Screen.StartAuthorization -> StartAuthorizationScreen(
                mvi = koinMviComponent<StartAuthorizationComponent>(),
                onNavigateToConfirmation = {
                    navigation.push(Screen.AfterStart(it.string))
                },
            )

            is Screen.AfterStart -> AfterStartScreen(
                mvi = koinMviComponent<AfterStartScreenComponent> {
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
                mvi = koinMviComponent<NewAccountInfoScreenComponent> {
                    parametersOf(VerificationHash.createOrThrow(screen.verificationHash))
                },
                navigateToConfigure = {
                    navigation.push(Screen.NewAccount(screen.verificationHash))
                },
                navigateToStart = {
                    navigation.popTo(0)
                },
            )

            is Screen.NewAccount -> ConfigureAccountScreen(
                mvi = koinMviComponent<ConfigureAccountScreenComponent> {
                    parametersOf(VerificationHash.createOrThrow(screen.verificationHash))
                },
                onBack = {
                    navigation.popTo(0)
                },
                navigateToHome = {
                    navigation.replaceAll(Screen.TimersList)
                },
            )

            is Screen.TimersList -> TimersListScreen(
                mvi = koinMviComponent<TimersListScreenComponent>(),
                navigateToSetting = {
                    // TODO when settings page is ready
                },
                navigateToTimerCreationScreen = {
                    navigation.push(Screen.TimerCreation)
                },
                navigateToTimer = {
                    //TODO when timer screen will be ready
                },
            )

            is Screen.TimerCreation -> TimerCreationScreen(
                mvi = koinMviComponent<TimerCreationScreenComponent>(),
                navigateToTimersScreen = {
                    navigation.pop()
                },
            )

            is Screen.TimerSettings -> TimerSettingsScreen(
                mvi = koinMviComponent<TimerSettingsScreenComponent>(),
                navigateToTimersScreen = {
                    navigation.pop()
                },
            )
        }
    }
}