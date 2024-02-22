package org.timemates.app.preview.feature.feature.timers

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import org.timemates.app.preview.feature.mvi.fakeMvi
import org.timemates.app.style.system.theme.AppTheme
import org.timemates.app.timers.ui.timers_list.TimersListScreen
import org.timemates.app.timers.ui.timers_list.mvi.TimersListScreenComponent

@Preview
@Composable
internal fun TimerListScreenPreview() {
    AppTheme {
        TimersListScreen(
            mvi = fakeMvi(TimersListScreenComponent.State()),
            navigateToSetting = {},
            navigateToTimerCreationScreen = {},
            navigateToTimer = {},
        )
    }
}
