package com.timemates.app.preview.feature.feature.authorization

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.timemates.app.preview.feature.statemachine.fakeStateMachine
import io.timemates.app.authorization.ui.initial_authorization.InitialAuthorizationScreen
import io.timemates.app.foundation.mvi.EmptyState
import io.timemates.app.style.system.theme.AppTheme

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