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
import io.timemates.app.authorization.ui.afterstart.mvi.AfterStartStateMachine
import io.timemates.app.authorization.ui.configure_account.ConfigureAccountScreen
import io.timemates.app.authorization.ui.configure_account.mvi.ConfigureAccountStateMachine
import io.timemates.app.authorization.ui.confirmation.ConfirmAuthorizationScreen
import io.timemates.app.authorization.ui.confirmation.mvi.ConfirmAuthorizationStateMachine
import io.timemates.app.authorization.ui.initial_authorization.InitialAuthorizationScreen
import io.timemates.app.authorization.ui.initial_authorization.mvi.InitialAuthorizationStateMachine
import io.timemates.app.authorization.ui.new_account_info.NewAccountInfoScreen
import io.timemates.app.authorization.ui.new_account_info.mvi.NewAccountInfoStateMachine
import io.timemates.app.authorization.ui.start.StartAuthorizationScreen
import io.timemates.app.authorization.ui.start.mvi.StartAuthorizationStateMachine
import io.timemates.app.feature.common.startup.StartupScreen
import io.timemates.app.feature.common.startup.mvi.StartupStateMachine
import io.timemates.app.mvi.compose.stateMachine
import io.timemates.app.timers.ui.settings.TimerSettingsScreen
import io.timemates.app.timers.ui.settings.mvi.TimerSettingsStateMachine
import io.timemates.app.timers.ui.timer_creation.TimerCreationScreen
import io.timemates.app.timers.ui.timer_creation.mvi.TimerCreationStateMachine
import io.timemates.app.timers.ui.timers_list.TimersListScreen
import io.timemates.app.timers.ui.timers_list.mvi.TimersListStateMachine
import io.timemates.sdk.authorization.email.types.value.VerificationHash
import io.timemates.sdk.common.constructor.createOrThrow
import io.timemates.sdk.common.exceptions.UnauthorizedException
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.channels.consumeEach
import org.koin.core.parameter.parametersOf

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

    ChildStack(
        source = navigation,
        initialStack = { listOf(initialScreen) },
        animation = stackAnimation(fade() + scale()),
    ) { screen ->
        println(screen)
        when (screen) {
            is Screen.Startup -> StartupScreen(
                stateMachine = stateMachine<StartupStateMachine>(),
                navigateToAuth = { navigation.push(Screen.InitialAuthorizationScreen) },
                navigateToHome = { navigation.replaceAll(Screen.TimersList) },
            )

            is Screen.ConfirmAuthorization -> ConfirmAuthorizationScreen(
                stateMachine = stateMachine<ConfirmAuthorizationStateMachine> {
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
                stateMachine = stateMachine<InitialAuthorizationStateMachine>(),
                navigateToStartAuthorization = {
                    navigation.push(Screen.StartAuthorization)
                },
            )

            Screen.StartAuthorization -> StartAuthorizationScreen(
                stateMachine = stateMachine<StartAuthorizationStateMachine>(),
                onNavigateToConfirmation = {
                    navigation.push(Screen.AfterStart(it.string))
                },
            )

            is Screen.AfterStart -> AfterStartScreen(
                stateMachine = stateMachine<AfterStartStateMachine> {
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
                stateMachine = stateMachine<NewAccountInfoStateMachine> {
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
                stateMachine = stateMachine<ConfigureAccountStateMachine> {
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
                stateMachine = stateMachine<TimersListStateMachine>(),
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
                stateMachine = stateMachine<TimerCreationStateMachine>(),
                navigateToTimersScreen = {
                    navigation.pop()
                },
            )

            is Screen.TimerSettings -> TimerSettingsScreen(
                stateMachine = stateMachine<TimerSettingsStateMachine>(),
                navigateToTimersScreen = {
                    navigation.pop()
                },
            )
        }
    }
}