package org.timemates.app.preview.feature.feature.authorization

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import org.timemates.app.authorization.ui.initial_authorization.InitialAuthorizationScreen
import org.timemates.app.authorization.ui.initial_authorization.mvi.InitialAuthorizationScreenComponent.State
import org.timemates.app.preview.feature.mvi.fakeMvi
import org.timemates.app.style.system.theme.AppTheme

@Preview
@Composable
fun InitialScreenPreview() {
    AppTheme {
        InitialAuthorizationScreen(
            mvi = fakeMvi(State),
            navigateToStartAuthorization = {},
        )
    }
}