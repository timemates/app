package org.timemates.app.preview.feature.feature.authorization

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import org.timemates.app.authorization.ui.configure_account.ConfigureAccountScreen
import org.timemates.app.authorization.ui.configure_account.mvi.ConfigureAccountScreenComponent.State
import org.timemates.app.preview.feature.mvi.fakeMvi
import org.timemates.app.style.system.theme.AppTheme

@Preview
@Composable
internal fun ConfigureNewAccountScreenPreview() {
    AppTheme {
        ConfigureAccountScreen(
            mvi = fakeMvi(State()),
            navigateToHome = {},
            onBack = {},
        )
    }
}