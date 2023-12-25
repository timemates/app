package com.timemates.app.preview.feature.feature.timers

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.timemates.app.preview.feature.statemachine.fakeStateMachine
import io.timemates.app.style.system.theme.AppTheme
import io.timemates.app.timers.ui.timers_list.TimersListScreen
import io.timemates.app.timers.ui.timers_list.mvi.TimersListStateMachine

@Preview
@Composable
internal fun TimerListScreenPreview() {
    AppTheme {
        TimersListScreen(
            stateMachine = fakeStateMachine(TimersListStateMachine.State()),
            navigateToSetting = {},
            navigateToTimerCreationScreen = {},
            navigateToTimer = {},
        )
    }
}
