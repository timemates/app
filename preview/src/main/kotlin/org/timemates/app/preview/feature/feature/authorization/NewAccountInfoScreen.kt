package org.timemates.app.preview.feature.feature.authorization

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import org.timemates.app.preview.feature.statemachine.fakeStateMachine
import org.timemates.app.authorization.ui.new_account_info.NewAccountInfoScreen
import org.timemates.app.foundation.mvi.EmptyState
import org.timemates.app.style.system.theme.AppTheme

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