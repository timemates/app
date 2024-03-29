package org.timemates.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.addCallback
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.defaultComponentContext
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.pop
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import org.timemates.app.feature.common.providable.LocalTimeProvider
import org.timemates.app.foundation.time.SystemUTCTimeProvider
import org.timemates.app.navigation.LocalComponentContext
import org.timemates.app.navigation.Screen
import org.timemates.app.navigation.TimeMatesAppEntry
import org.timemates.app.style.system.theme.AppTheme

class AppActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val componentContent = defaultComponentContext()

        setContent {
            val systemUiController = rememberSystemUiController()
            val navigation: StackNavigation<Screen> = remember { StackNavigation() }

            LaunchedEffect(Unit) {
                onBackPressedDispatcher.addCallback(this@AppActivity) {
                    navigation.pop { isSuccess ->
                        if (!isSuccess)
                            finish()
                    }
                }
            }

            CompositionLocalProvider(
                LocalComponentContext provides componentContent,
                LocalTimeProvider provides SystemUTCTimeProvider(),
            ) {
                AppTheme {
                    systemUiController.setSystemBarsColor(AppTheme.colors.background)

                    Box(modifier = Modifier.fillMaxSize()) {
                        TimeMatesAppEntry(
                            navigation = navigation,
                            navigateToAuthorization = TimeMatesApplication.onAuthFailedChannel,
                        )
                    }
                }
            }
        }
    }
}
