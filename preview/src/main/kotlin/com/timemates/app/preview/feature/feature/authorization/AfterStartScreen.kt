package com.timemates.app.preview.feature.feature.authorization

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.timemates.app.preview.feature.statemachine.fakeStateMachine
import io.timemates.app.authorization.ui.afterstart.AfterStartScreen
import io.timemates.app.foundation.mvi.EmptyState
import io.timemates.app.style.system.theme.AppTheme

@Preview
@Composable
internal fun AfterStartScreenPreview() {
    AppTheme {
        AfterStartScreen(
            stateMachine = fakeStateMachine(EmptyState),
            navigateToConfirmation = {},
            navigateToStart = {}
        )
    }
}