package io.timemates.app.common

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import com.arkivanov.decompose.ComponentContext
import io.timemates.app.navigation.LocalComponentContext
import io.timemates.app.navigation.TimeMatesAppEntry
import io.timemates.sdk.common.exceptions.UnauthorizedException
import kotlinx.coroutines.channels.ReceiveChannel

@Composable
fun App(
    componentContext: ComponentContext,
    onAuthFailed: ReceiveChannel<UnauthorizedException>,
) {
    CompositionLocalProvider(LocalComponentContext provides componentContext) {
        TimeMatesAppEntry(
            navigateToAuthorization = onAuthFailed,
        )
    }
}