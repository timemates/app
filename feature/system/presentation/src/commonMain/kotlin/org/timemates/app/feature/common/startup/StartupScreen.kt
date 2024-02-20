package org.timemates.app.feature.common.startup

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import io.github.skeptick.libres.compose.painterResource
import org.timemates.app.feature.common.startup.mvi.StartupEffect
import org.timemates.app.feature.common.startup.mvi.StartupEvent
import org.timemates.app.foundation.mvi.EmptyState
import org.timemates.app.foundation.mvi.StateMachine
import org.timemates.app.style.system.Resources
import org.timemates.app.style.system.theme.AppTheme
import kotlinx.coroutines.channels.consumeEach
import kotlinx.coroutines.launch

@Composable
fun StartupScreen(
    stateMachine: StateMachine<EmptyState, StartupEvent, StartupEffect>,
    navigateToAuth: () -> Unit,
    navigateToHome: () -> Unit,
) {
    LaunchedEffect(true) {
        launch { stateMachine.dispatchEvent(StartupEvent.Started) }
        stateMachine.effects.consumeEach { effect ->
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