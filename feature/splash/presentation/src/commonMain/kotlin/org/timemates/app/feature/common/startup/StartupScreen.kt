package org.timemates.app.feature.common.startup

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import io.github.skeptick.libres.compose.painterResource
import org.timemates.app.feature.common.startup.mvi.StartupScreenMVIComponent
import org.timemates.app.style.system.Resources
import org.timemates.app.style.system.theme.AppTheme
import pro.respawn.flowmvi.essenty.compose.subscribe

@Composable
fun StartupScreen(
    mvi: StartupScreenMVIComponent,
    navigateToAuth: () -> Unit,
    navigateToHome: () -> Unit,
) {
    @Suppress("UNUSED_VARIABLE")
    val state = mvi.subscribe { action ->
        when (action) {
            StartupScreenMVIComponent.Action.GoToAuthorization -> navigateToAuth()
            StartupScreenMVIComponent.Action.GoToMainScreen -> navigateToHome()
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