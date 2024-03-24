package org.timemates.app.common

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import com.arkivanov.decompose.ComponentContext
import org.timemates.app.navigation.LocalComponentContext
import org.timemates.app.navigation.TimeMatesAppEntry
import org.timemates.sdk.common.exceptions.UnauthorizedException
import kotlinx.coroutines.channels.ReceiveChannel
import org.timemates.app.feature.common.providable.LocalTimeProvider
import org.timemates.app.foundation.time.TimeProvider

@Composable
fun App(
    timeProvider: TimeProvider,
    componentContext: ComponentContext,
    onAuthFailed: ReceiveChannel<UnauthorizedException>,
) {
    CompositionLocalProvider(
        LocalComponentContext provides componentContext,
        LocalTimeProvider provides timeProvider,
    ) {
        TimeMatesAppEntry(
            navigateToAuthorization = onAuthFailed,
        )
    }
}