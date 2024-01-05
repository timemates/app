package io.timemates.app

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Timer
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowPosition
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import com.arkivanov.decompose.DefaultComponentContext
import com.arkivanov.decompose.ExperimentalDecomposeApi
import com.arkivanov.decompose.extensions.compose.jetbrains.lifecycle.LifecycleController
import com.arkivanov.essenty.lifecycle.LifecycleRegistry
import io.timemates.app.common.App
import io.timemates.app.style.system.theme.AppTheme
import io.timemates.sdk.common.exceptions.UnauthorizedException
import kotlinx.coroutines.channels.Channel

@OptIn(ExperimentalDecomposeApi::class)
fun startUi(authorizationFailedChannel: Channel<UnauthorizedException>) {
    val lifecycle = LifecycleRegistry()
    val rootComponentContext = DefaultComponentContext(lifecycle = lifecycle)

    application {
        val (isAppVisible, setIsAppVisible) = remember { mutableStateOf(false) }

        val windowState = rememberWindowState(
            isMinimized = true,
            size = DpSize(AppConstants.APP_WIDTH.dp, AppConstants.APP_HEIGHT.dp),
        )
        val trayIcon = rememberVectorPainter(Icons.Outlined.Timer)

        AppTray(
            trayIcon,
            onClick = { x, y ->
                windowState.position = WindowPosition(x.dp, y.dp)
                windowState.isMinimized = false
                setIsAppVisible(!isAppVisible)
            }
        )
        LifecycleController(lifecycle, windowState)

        Window(
            onCloseRequest = ::exitApplication,
            state = windowState,
            title = "TimeMates",
            resizable = false,
            undecorated = true,
            transparent = true,
            visible = isAppVisible,
        ) {
            window.isAlwaysOnTop = true
            Box(modifier = Modifier.fillMaxSize().clip(MaterialTheme.shapes.small)) {
                AppTheme(useDarkTheme = false) {
                    App(
                        componentContext = rootComponentContext,
                        authorizationFailedChannel,
                    )
                }
            }
        }
    }
}