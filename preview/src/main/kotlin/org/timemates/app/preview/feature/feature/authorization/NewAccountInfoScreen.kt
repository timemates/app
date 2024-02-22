package org.timemates.app.preview.feature.feature.authorization

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import org.timemates.app.authorization.ui.new_account_info.NewAccountInfoScreen
import org.timemates.app.authorization.ui.new_account_info.mvi.NewAccountInfoScreenComponent.State
import org.timemates.app.preview.feature.mvi.fakeMvi
import org.timemates.app.style.system.theme.AppTheme

@Preview
@Composable
internal fun NewAccountInfoScreenPreview() {
    AppTheme {
        NewAccountInfoScreen(
            mvi = fakeMvi(State),
            navigateToConfigure = {},
            navigateToStart = {}
        )
    }
}