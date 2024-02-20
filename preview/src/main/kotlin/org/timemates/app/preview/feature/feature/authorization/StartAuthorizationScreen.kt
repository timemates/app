package org.timemates.app.preview.feature.feature.authorization

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import org.timemates.app.authorization.ui.start.StartAuthorizationScreen
import org.timemates.app.authorization.ui.start.mvi.StartAuthorizationStateMachine.State
import org.timemates.app.style.system.theme.AppTheme
import org.timemates.app.preview.feature.statemachine.fakeStateMachine

@Preview
@Composable
internal fun StartAuthorizationScreenPreview() {
    AppTheme {
        StartAuthorizationScreen(stateMachine = fakeStateMachine(State()), onNavigateToConfirmation = {})
    }
}