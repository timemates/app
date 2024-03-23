package org.timemates.app

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Timer
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowPosition
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import com.arkivanov.decompose.DefaultComponentContext
import com.arkivanov.decompose.ExperimentalDecomposeApi
import com.arkivanov.decompose.extensions.compose.lifecycle.LifecycleController
import com.arkivanov.essenty.lifecycle.LifecycleRegistry
import com.arkivanov.essenty.statekeeper.StateKeeperDispatcher
import kotlinx.coroutines.channels.Channel
import org.timemates.app.common.App
import org.timemates.app.foundation.time.SystemUTCTimeProvider
import org.timemates.app.style.system.theme.AppTheme
import org.timemates.sdk.common.exceptions.UnauthorizedException
import java.awt.event.WindowEvent
import java.awt.event.WindowListener
import javax.swing.SwingUtilities

@OptIn(ExperimentalDecomposeApi::class)
fun startUi(authorizationFailedChannel: Channel<UnauthorizedException>) {
    val lifecycle = LifecycleRegistry()
    val stateKeeper = StateKeeperDispatcher()
    val rootComponentContext = DefaultComponentContext(
        lifecycle = lifecycle,
        stateKeeper = stateKeeper,
    )

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
            window.title = "TimeMates"

            Box(
                modifier = Modifier.fillMaxSize()
                    .padding(16.dp)
            ) {
                Card(
                    modifier = Modifier.fillMaxSize(),
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                ) {
                    AppTheme(useDarkTheme = false) {
                        App(
                            componentContext = rootComponentContext,
                            onAuthFailed = authorizationFailedChannel,
                            timeProvider = SystemUTCTimeProvider(),
                        )
                    }
                }
            }

            LaunchedEffect(true) {
                SwingUtilities.invokeLater {
                    window.addWindowListener(object : WindowListener {
                        override fun windowOpened(e: WindowEvent?) {
                            window.isAlwaysOnTop = true
                            window.toFront()
                            window.requestFocus()
                            window.isAlwaysOnTop = false
                        }

                        override fun windowClosing(e: WindowEvent?) {}

                        override fun windowClosed(e: WindowEvent?) {}

                        override fun windowIconified(e: WindowEvent?) {}

                        override fun windowDeiconified(e: WindowEvent?) {}

                        override fun windowActivated(e: WindowEvent?) {
                            window.isAlwaysOnTop = true
                            window.toFront()
                            window.requestFocus()
                        }

                        override fun windowDeactivated(e: WindowEvent?) {
                            setIsAppVisible(false)
                        }
                    })
                }
            }
        }
    }
}