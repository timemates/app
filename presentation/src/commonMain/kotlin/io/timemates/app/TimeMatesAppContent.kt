package io.timemates.app

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import com.arkivanov.decompose.extensions.compose.jetbrains.stack.animation.fade
import com.arkivanov.decompose.extensions.compose.jetbrains.stack.animation.plus
import com.arkivanov.decompose.extensions.compose.jetbrains.stack.animation.scale
import com.arkivanov.decompose.extensions.compose.jetbrains.stack.animation.stackAnimation
import com.arkivanov.decompose.router.stack.StackNavigation
import io.timemates.app.navigation.ChildStack
import io.timemates.app.navigation.Screen
import io.timemates.app.style.system.AppTheme

@Composable
fun TimeMatesAppContent(
    initialScreen: Screen = Screen.StartAuthorization
) = AppTheme {
    val navigation = remember { StackNavigation<Screen>() }

    ChildStack(
        source = navigation,
        initialStack = { listOf(Screen.StartAuthorization) },
        animation = stackAnimation(fade() + scale()),
    ) { screen ->
        when (screen) {
            is Screen.ConfirmAuthorization -> TODO()
            Screen.StartAuthorization -> TODO()
        }
    }
}