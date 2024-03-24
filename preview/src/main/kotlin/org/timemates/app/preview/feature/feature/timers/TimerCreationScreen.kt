package org.timemates.app.preview.feature.feature.timers

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import org.timemates.app.preview.feature.mvi.fakeMvi
import org.timemates.app.style.system.theme.AppTheme
import org.timemates.app.timers.ui.timer_creation.TimerCreationScreen
import org.timemates.app.timers.ui.timer_creation.mvi.TimerCreationScreenComponent

@Preview
@Composable
internal fun TimerCreationScreenPreview() {
    AppTheme {
        TimerCreationScreen(
            mvi = fakeMvi(TimerCreationScreenComponent.State()),
            navigateToTimersScreen = {},
        )
    }
}