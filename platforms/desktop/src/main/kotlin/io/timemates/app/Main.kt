package io.timemates.app

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Timer
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.toAwtImage
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowPosition
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import app.cash.sqldelight.db.SqlDriver
import com.arkivanov.decompose.DefaultComponentContext
import com.arkivanov.decompose.ExperimentalDecomposeApi
import com.arkivanov.decompose.extensions.compose.jetbrains.lifecycle.LifecycleController
import com.arkivanov.essenty.lifecycle.LifecycleRegistry
import io.timemates.app.authorization.dependencies.AuthorizationDataModule
import io.timemates.app.authorization.dependencies.screens.ConfirmAuthorizationModule
import io.timemates.app.authorization.dependencies.screens.StartAuthorizationModule
import io.timemates.app.navigation.LocalComponentContext
import io.timemates.app.navigation.TimeMatesAppEntry
import io.timemates.app.tray.TimeMatesTray
import io.timemates.data.database.TimeMatesAuthorizations
import org.koin.core.context.startKoin
import org.koin.dsl.module
import org.koin.ksp.generated.module

@OptIn(ExperimentalDecomposeApi::class)
fun main() {
    val koin = startKoin {
        module {
            single<SqlDriver> {
                JdbcSqliteDriver(JdbcSqliteDriver.IN_MEMORY)
            }
        }
        modules(
            AuthorizationDataModule().module,
            ConfirmAuthorizationModule().module,
            StartAuthorizationModule().module,
        )
    }
    val lifecycle = LifecycleRegistry()
    val rootComponentContext = DefaultComponentContext(lifecycle = lifecycle)

    application {
        val (isAppVisible, setIsAppVisible) = remember { mutableStateOf(false) }
        val windowState = rememberWindowState(
            size = DpSize(350.dp, 650.dp),
        )
        val trayIcon = rememberVectorPainter(Icons.Outlined.Timer)
            .toAwtImage(LocalDensity.current, LayoutDirection.Ltr)

        remember {
            var initial = isAppVisible

            TimeMatesTray.initialize(
                trayIcon,
                onClick = { x, y ->
                    windowState.position = WindowPosition(x.dp, y.dp)
                    initial = !initial
                    setIsAppVisible(!initial)
                }
            )
        }

        CompositionLocalProvider(LocalComponentContext provides rootComponentContext) {
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

                Box(modifier = Modifier.fillMaxSize().clip(MaterialTheme.shapes.small)) {
                    TimeMatesAppEntry(isDarkTheme = false)
                }
            }
        }

    }
}