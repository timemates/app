package org.timemates.app.authorization.ui.afterstart

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import io.github.skeptick.libres.compose.painterResource
import org.timemates.app.authorization.ui.afterstart.mvi.AfterStartScreenComponent
import org.timemates.app.authorization.ui.afterstart.mvi.AfterStartScreenComponent.Action
import org.timemates.app.authorization.ui.afterstart.mvi.AfterStartScreenComponent.Intent
import org.timemates.app.localization.compose.LocalStrings
import org.timemates.app.style.system.Resources
import org.timemates.app.style.system.appbar.AppBar
import org.timemates.app.style.system.button.Button
import org.timemates.app.style.system.theme.AppTheme
import pro.respawn.flowmvi.essenty.compose.subscribe

@Composable
fun AfterStartScreen(
    mvi: AfterStartScreenComponent,
    navigateToConfirmation: (String) -> Unit,
    navigateToStart: () -> Unit,
) {
    val painter: Painter = Resources.image.confirm_authorization_info_image.painterResource()

    @Suppress("UNUSED_VARIABLE")
    val state = mvi.subscribe { action ->
        when (action) {
            is Action.NavigateToConfirmation -> navigateToConfirmation(action.verificationHash.string)
            Action.OnChangeEmailClicked -> navigateToStart()
        }
    }

    Scaffold(
        topBar = {
            AppBar(
                navigationIcon = {
                    IconButton(
                        onClick = { mvi.store.intent(Intent.OnChangeEmailClicked) },
                    ) {
                        Icon(Icons.AutoMirrored.Rounded.ArrowBack, contentDescription = null)
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
                Icon(
                    modifier = Modifier,
                    painter = painter,
                    contentDescription = null,
                )

                Spacer(Modifier.height(8.dp))

                Text(
                    text = LocalStrings.current.confirmation,
                    modifier = Modifier,
                    style = MaterialTheme.typography.titleLarge,
                )

                Spacer(Modifier.height(16.dp))

                Text(
                    text = LocalStrings.current.confirmationDescription,
                    modifier = Modifier.padding(horizontal = 8.dp),
                    color = AppTheme.colors.secondaryVariant,
                    style = MaterialTheme.typography.bodyMedium,
                    textAlign = TextAlign.Center,
                )
            }

            Button(
                modifier = Modifier.fillMaxWidth(),
                primary = false,
                onClick = { mvi.store.intent(Intent.OnChangeEmailClicked) },
            ) {
                Text(LocalStrings.current.changeEmail)
            }

            Button(
                modifier = Modifier.fillMaxWidth(),
                primary = true,
                onClick = { mvi.store.intent(Intent.NextClicked) },
            ) {
                Text(LocalStrings.current.nextStep)
            }
        }
    }
}