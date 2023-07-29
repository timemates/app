package io.timemates.app.authorization.ui.afterstart

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import io.timemates.app.authorization.ui.afterstart.mvi.AfterStartStateMachine
import io.timemates.app.authorization.ui.afterstart.mvi.AfterStartStateMachine.Event
import io.timemates.app.localization.compose.LocalStrings
import io.timemates.app.style.system.appbar.AppBar
import io.timemates.app.style.system.button.Button
import io.timemates.app.style.system.images.CircleIcon
import kotlinx.coroutines.channels.consumeEach

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AfterStartScreen(
    stateMachine: AfterStartStateMachine,
    navigateToConfirmation: (String) -> Unit,
    navigateToStart: () -> Unit,
) {
    LaunchedEffect(Unit) {
        stateMachine.effects.consumeEach { effect ->
            when (effect) {
                is AfterStartStateMachine.Effect.NavigateToConfirmation ->
                    navigateToConfirmation(effect.verificationHash.string)

                AfterStartStateMachine.Effect.OnChangeEmailClicked ->
                    navigateToStart()
            }
        }
    }

    Scaffold(
        topBar = {
            AppBar(
                navigationIcon = {
                    IconButton(
                        onClick = { stateMachine.dispatchEvent(Event.OnChangeEmailClicked) },
                    ) {
                        Icon(Icons.Rounded.ArrowBack, contentDescription = null)
                    }
                },
                title = LocalStrings.current.appName,
            )
        }
    ) { rootPaddings ->
        Column(
            modifier = Modifier.fillMaxSize()
                .padding(rootPaddings)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            Column(
                modifier = Modifier.fillMaxWidth().weight(1f),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                CircleIcon(
                    painter = Icons.Outlined.Email,
                )

                Spacer(Modifier.height(8.dp))

                Text(
                    text = LocalStrings.current.confirmation,
                    style = MaterialTheme.typography.displaySmall,
                )

                Text(
                    modifier = Modifier.padding(horizontal = 8.dp),
                    text = LocalStrings.current.confirmationDescription,
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.bodySmall,
                )
            }

            Button(
                modifier = Modifier.fillMaxWidth(),
                primary = false,
                onClick = { stateMachine.dispatchEvent(Event.OnChangeEmailClicked) },
            ) {
                Text(LocalStrings.current.changeEmail)
            }

            Button(
                modifier = Modifier.fillMaxWidth(),
                primary = true,
                onClick = { stateMachine.dispatchEvent(Event.NextClicked) },
            ) {
                Text(LocalStrings.current.nextStep)
            }
        }
    }
}