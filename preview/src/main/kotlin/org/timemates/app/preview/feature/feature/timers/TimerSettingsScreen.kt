package org.timemates.app.preview.feature.feature.timers

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import org.timemates.app.preview.feature.mvi.fakeMvi
import org.timemates.app.style.system.theme.AppTheme
import org.timemates.app.timers.ui.settings.TimerSettingsScreen
import org.timemates.app.timers.ui.settings.mvi.TimerSettingsScreenComponent.State

@Preview
@Composable
internal fun TimerSettingsScreenPreview() {
    AppTheme {
        TimerSettingsScreen(
            mvi = fakeMvi(State()),
            navigateToTimersScreen = {},
        )
    }
}
