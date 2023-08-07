package com.timemates.app.preview.feature.feature.authorization

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.timemates.app.preview.feature.statemachine.fakeStateMachine
import io.timemates.app.authorization.ui.configure_account.ConfigureAccountScreen
import io.timemates.app.authorization.ui.configure_account.mvi.ConfigureAccountStateMachine.State
import io.timemates.app.style.system.theme.AppTheme

@Preview
@Composable
internal fun ConfigureNewAccountScreenPreview() {
    AppTheme {
        ConfigureAccountScreen(
            stateMachine = fakeStateMachine(State()),
            navigateToHome = {},
            onBack = {},
        )
    }
}