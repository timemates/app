package org.timemates.app.authorization.ui.configure_account

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material.icons.outlined.PersonOutline
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import org.timemates.app.authorization.ui.configure_account.mvi.ConfigureAccountScreenComponent
import org.timemates.app.authorization.ui.configure_account.mvi.ConfigureAccountScreenComponent.Action
import org.timemates.app.authorization.ui.configure_account.mvi.ConfigureAccountScreenComponent.Intent
import org.timemates.app.feature.common.failures.getDefaultDisplayMessage
import org.timemates.app.feature.common.getFailuresIfPresent
import org.timemates.app.feature.common.isInvalid
import org.timemates.app.localization.compose.LocalStrings
import org.timemates.app.style.system.appbar.AppBar
import org.timemates.app.style.system.button.ButtonWithProgress
import org.timemates.sdk.users.profile.types.value.UserDescription
import org.timemates.sdk.users.profile.types.value.UserName
import pro.respawn.flowmvi.essenty.compose.subscribe

@Composable
fun ConfigureAccountScreen(
    mvi: ConfigureAccountScreenComponent,
    navigateToHome: () -> Unit,
    onBack: () -> Unit,
) {
    val snackBarData = remember { SnackbarHostState() }
    val strings = LocalStrings.current

    val state by mvi.subscribe { action ->
        when (action) {
            is Action.ShowFailure ->
                snackBarData.showSnackbar(message = action.throwable.getDefaultDisplayMessage(strings))

            is Action.NavigateToHomePage -> navigateToHome()
            Action.NavigateToStart -> onBack()
        }
    }

    val nameSize = remember(state.name) { state.name.value.length }
    val aboutYouSize = remember(state.aboutYou) { state.aboutYou.value.length }

    Scaffold(
        topBar = {
            AppBar(
                navigationIcon = {
                    IconButton(
                        onClick = onBack,
                    ) {
                        Icon(Icons.AutoMirrored.Rounded.ArrowBack, contentDescription = null)
                    }
                },
                title = LocalStrings.current.confirmation,
            )
        },
    ) { rootPaddings ->
        val nameSupportText = state.name.getFailuresIfPresent(strings)
        val aboutYouSupportText = state.aboutYou.getFailuresIfPresent(strings)

        Column(
            modifier = Modifier.fillMaxHeight().padding(rootPaddings).padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                leadingIcon = { Icon(Icons.Outlined.PersonOutline, contentDescription = null) },
                value = state.name.value,
                onValueChange = { mvi.store.intent(Intent.NameIsChanged(it)) },
                label = { Text(LocalStrings.current.yourName) },
                isError = state.name.isInvalid() && nameSize > UserName.LENGTH_RANGE.last,
                singleLine = true,
                supportingText = {
                    if (nameSupportText != null) {
                        Text(nameSupportText)
                    } else {
                        Text(
                            modifier = Modifier.fillMaxWidth(),
                            text = "$nameSize / ${UserName.LENGTH_RANGE.last}",
                            textAlign = TextAlign.End
                        )
                    }
                },
                enabled = !state.isLoading,
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Send)
            )

            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = state.aboutYou.value,
                onValueChange = { mvi.store.intent(Intent.DescriptionIsChanged(it)) },
                label = { Text(LocalStrings.current.aboutYou) },
                isError = state.aboutYou.isInvalid() || aboutYouSize > UserDescription.LENGTH_RANGE.last,
                maxLines = 5,
                supportingText = {
                    if (aboutYouSupportText != null) {
                        Text(aboutYouSupportText)
                    } else {
                        Text(
                            modifier = Modifier.fillMaxWidth(),
                            text = "$aboutYouSize / ${UserDescription.LENGTH_RANGE.last}",
                            textAlign = TextAlign.End
                        )
                    }
                },
                enabled = !state.isLoading,
            )

            Spacer(Modifier.weight(1f))

            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                SnackbarHost(
                    hostState = snackBarData,
                ) {
                    Snackbar(it)
                }
                ButtonWithProgress(
                    primary = true,
                    modifier = Modifier.fillMaxWidth(),
                    onClick = { mvi.store.intent(Intent.OnDoneClicked) },
                    enabled = !state.isLoading,
                    isLoading = state.isLoading
                ) {
                    Text(text = LocalStrings.current.done)
                }
            }
        }
    }
}