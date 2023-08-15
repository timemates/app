package com.timemates.app.preview.feature.feature.timers

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.timemates.app.preview.feature.statemachine.fakeStateMachine
import io.timemates.app.style.system.theme.AppTheme
import io.timemates.app.timers.ui.timer_creation.TimerCreationScreen
import io.timemates.app.timers.ui.timer_creation.mvi.TimerCreationStateMachine

@Preview
@Composable
internal fun TimerCreationScreenPreview() {
    AppTheme {
        TimerCreationScreen(
            stateMachine = fakeStateMachine(TimerCreationStateMachine.State()),
            navigateToTimersScreen = {},
        )
    }
}