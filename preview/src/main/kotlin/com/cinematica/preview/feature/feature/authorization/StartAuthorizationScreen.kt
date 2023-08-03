package com.cinematica.preview.feature.feature.authorization

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import io.timemates.app.authorization.ui.start.StartAuthorizationScreen
import io.timemates.app.authorization.ui.start.mvi.StartAuthorizationStateMachine.State
import io.timemates.app.style.system.theme.AppTheme
import com.cinematica.preview.feature.statemachine.fakeStateMachine

@Preview
@Composable
internal fun StartAuthorizationScreenPreview() {
    AppTheme {
        StartAuthorizationScreen(stateMachine = fakeStateMachine(State()), onNavigateToConfirmation = {})
    }
}