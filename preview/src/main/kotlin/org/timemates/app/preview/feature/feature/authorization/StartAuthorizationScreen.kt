package org.timemates.app.preview.feature.feature.authorization

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import org.timemates.app.authorization.ui.start.StartAuthorizationScreen
import org.timemates.app.preview.feature.mvi.fakeMvi
import org.timemates.app.style.system.theme.AppTheme

@Preview
@Composable
internal fun StartAuthorizationScreenPreview() {
    AppTheme {
        StartAuthorizationScreen(mvi = fakeMvi(State()), onNavigateToConfirmation = {})
    }
}