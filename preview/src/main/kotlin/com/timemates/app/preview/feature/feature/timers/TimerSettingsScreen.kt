package com.timemates.app.preview.feature.feature.timers

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.timemates.app.preview.feature.statemachine.fakeStateMachine
import io.timemates.app.style.system.theme.AppTheme
import io.timemates.app.timers.ui.settings.TimerSettingsScreen
import io.timemates.app.timers.ui.settings.mvi.TimerSettingsStateMachine.State

@Preview
@Composable
internal fun TimerSettingsScreenPreview() {
    AppTheme {
        TimerSettingsScreen(
            stateMachine = fakeStateMachine(State()),
            saveChanges = {},
            navigateToTimersScreen = {},
        )
    }
}
