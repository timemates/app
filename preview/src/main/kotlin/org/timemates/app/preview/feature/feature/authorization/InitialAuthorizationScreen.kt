package org.timemates.app.preview.feature.feature.authorization

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import org.timemates.app.preview.feature.statemachine.fakeStateMachine
import org.timemates.app.authorization.ui.initial_authorization.InitialAuthorizationScreen
import org.timemates.app.foundation.mvi.EmptyState
import org.timemates.app.style.system.theme.AppTheme

@Preview
@Composable
fun InitialScreenPreview() {
    AppTheme {
        InitialAuthorizationScreen(
            stateMachine = fakeStateMachine(EmptyState),
            navigateToStartAuthorization = {},
        )
    }
}