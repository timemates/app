package org.timemates.app.preview.feature.feature.authorization

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import org.timemates.app.preview.feature.statemachine.fakeStateMachine
import org.timemates.app.authorization.ui.confirmation.ConfirmAuthorizationScreen
import org.timemates.app.authorization.ui.confirmation.mvi.ConfirmAuthorizationStateMachine.State
import org.timemates.app.style.system.theme.AppTheme

@Preview
@Composable
internal fun ConfirmationScreenPreview() {
    AppTheme {
        ConfirmAuthorizationScreen(
            stateMachine = fakeStateMachine(State()),
            onBack = {},
            navigateToConfiguring = {},
            navigateToHome = {},
        )
    }
}