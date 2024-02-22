package org.timemates.app.feature.common.startup

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import io.github.skeptick.libres.compose.painterResource
import kotlinx.coroutines.channels.consumeEach
import org.timemates.app.feature.common.startup.mvi.StartupEffect
import org.timemates.app.feature.common.startup.mvi.StartupEvent
import org.timemates.app.feature.common.startup.mvi.StartupScreenMVIComponent.State
import org.timemates.app.foundation.mvi.MVI
import org.timemates.app.style.system.Resources
import org.timemates.app.style.system.theme.AppTheme

@Composable
fun StartupScreen(
    mvi: MVI<State, StartupEvent, StartupEffect>,
    navigateToAuth: () -> Unit,
    navigateToHome: () -> Unit,
) {
    LaunchedEffect(true) {
        mvi.dispatchEvent(StartupEvent.Started)
        mvi.effects.consumeEach { effect ->
            when (effect) {
                StartupEffect.Authorized -> navigateToHome()
                StartupEffect.Unauthorized -> navigateToAuth()
            }
        }
    }

    Box(Modifier.fillMaxSize().background(AppTheme.colors.background), contentAlignment = Alignment.Center) {
        Image(
            modifier = Modifier.size(96.dp),
            painter = Resources.image.app_icon.painterResource(),
            contentDescription = null,
        )
    }
}