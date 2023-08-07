package com.timemates.app.preview.feature.feature.authorization

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.timemates.app.preview.feature.statemachine.fakeStateMachine
import io.timemates.app.authorization.ui.new_account_info.NewAccountInfoScreen
import io.timemates.app.foundation.mvi.EmptyState
import io.timemates.app.style.system.theme.AppTheme

@Preview
@Composable
internal fun NewAccountInfoScreenPreview() {
    AppTheme {
        NewAccountInfoScreen(
            stateMachine = fakeStateMachine(EmptyState),
            navigateToConfigure = {},
            navigateToStart = {}
        )
    }
}