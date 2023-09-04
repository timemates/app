package io.timemates.app.authorization.ui.initial_authorization

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import dev.icerock.moko.resources.compose.fontFamilyResource
import dev.icerock.moko.resources.compose.painterResource
import io.timemates.app.authorization.ui.initial_authorization.mvi.InitialAuthorizationStateMachine.Effect
import io.timemates.app.authorization.ui.initial_authorization.mvi.InitialAuthorizationStateMachine.Event
import io.timemates.app.foundation.mvi.EmptyState
import io.timemates.app.foundation.mvi.StateMachine
import io.timemates.app.localization.compose.LocalStrings
import io.timemates.app.style.system.Resources
import io.timemates.app.style.system.button.Button
import io.timemates.app.style.system.theme.AppTheme
import kotlinx.coroutines.channels.consumeEach

@Composable
fun InitialAuthorizationScreen(
    stateMachine: StateMachine<EmptyState, Event, Effect>,
    navigateToStartAuthorization: () -> Unit,
) {
    val painter: Painter = painterResource(Resources.images.initial_screen_image)

    LaunchedEffect(Unit) {
        stateMachine.effects.consumeEach {  effect ->
            when (effect) {
                is Effect.NavigateToStart ->
                    navigateToStartAuthorization()
            }
        }
    }

    Scaffold { rootPaddings ->
        Column(
            modifier = Modifier.fillMaxSize()
                .padding(rootPaddings)
                .padding(16.dp),
        ) {
            Column(
                modifier = Modifier.fillMaxWidth().weight(1f),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Icon(
                    modifier = Modifier,
                    painter = painter,
                    contentDescription = null,
                )

                Spacer(Modifier.height(8.dp))

                Text(
                    text = LocalStrings.current.welcome,
                    modifier = Modifier,
                    fontFamily = fontFamilyResource(Resources.fonts.Inter.black),
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.titleLarge,
                )

                Spacer(Modifier.height(16.dp))

                Text(
                    text = LocalStrings.current.welcomeDescription,
                    modifier = Modifier.padding(horizontal = 8.dp),
                    color = AppTheme.colors.secondaryVariant,
                    style = MaterialTheme.typography.bodyMedium,
                    textAlign = TextAlign.Center,
                )
            }

            Button(
                modifier = Modifier.fillMaxWidth(),
                primary = true,
                onClick = { stateMachine.dispatchEvent(Event.OnStartClicked) },
            ) {
                Text(LocalStrings.current.letsStart)
            }
        }
    }
}
