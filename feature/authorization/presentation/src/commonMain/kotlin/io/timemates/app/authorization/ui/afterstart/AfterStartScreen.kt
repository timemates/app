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
import io.timemates.app.authorization.ui.new_account_info.mvi.NewAccountInfoStateMachine
import io.timemates.app.authorization.ui.new_account_info.mvi.NewAccountInfoStateMachine.Event
import io.timemates.app.localization.LocalStrings
import io.timemates.app.style.system.appbar.AppBar
import io.timemates.app.style.system.button.Button
import io.timemates.app.style.system.images.IconPicture
import kotlinx.coroutines.channels.consumeEach

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AfterStartScreen(
    stateMachine: NewAccountInfoStateMachine,
    navigateToConfirmation: (String) -> Unit,
    navigateToStart: () -> Unit,
) {
    LaunchedEffect(Unit) {
        stateMachine.effects.consumeEach { effect ->
            when (effect) {
                is NewAccountInfoStateMachine.Effect.NavigateToAccountConfiguring ->
                    navigateToConfirmation(effect.verificationHash.string)

                NewAccountInfoStateMachine.Effect.NavigateToStart ->
                    navigateToStart()
            }
        }
    }

    Scaffold(
        topBar = {
            AppBar(
                navigationIcon = {
                    IconButton(
                        onClick = { stateMachine.dispatchEvent(Event.OnBackClicked) },
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
                IconPicture(
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
                onClick = { stateMachine.dispatchEvent(Event.OnBackClicked) },
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