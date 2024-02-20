package org.timemates.app.preview.feature.feature.timers

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import org.timemates.app.preview.feature.statemachine.fakeStateMachine
import org.timemates.app.style.system.theme.AppTheme
import org.timemates.app.timers.ui.settings.TimerSettingsScreen
import org.timemates.app.timers.ui.settings.mvi.TimerSettingsStateMachine.State

@Preview
@Composable
internal fun TimerSettingsScreenPreview() {
    AppTheme {
        TimerSettingsScreen(
            stateMachine = fakeStateMachine(State()),
            navigateToTimersScreen = {},
        )
    }
}
