package org.timemates.app.authorization.ui.confirmation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material.icons.rounded.ArrowBack
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
import androidx.compose.ui.unit.dp
import org.timemates.app.authorization.ui.confirmation.mvi.ConfirmAuthorizationScreenComponent
import org.timemates.app.authorization.ui.confirmation.mvi.ConfirmAuthorizationScreenComponent.Action
import org.timemates.app.authorization.ui.confirmation.mvi.ConfirmAuthorizationScreenComponent.Intent
import org.timemates.app.feature.common.failures.getDefaultDisplayMessage
import org.timemates.app.feature.common.getFailuresIfPresent
import org.timemates.app.localization.compose.LocalStrings
import org.timemates.app.style.system.appbar.AppBar
import org.timemates.app.style.system.button.ButtonWithProgress
import pro.respawn.flowmvi.essenty.compose.subscribe

@Composable
fun ConfirmAuthorizationScreen(
    mvi: ConfirmAuthorizationScreenComponent,
    onBack: () -> Unit,
    navigateToConfiguring: (String) -> Unit,
    navigateToHome: () -> Unit,
) {
    val strings = LocalStrings.current
    val snackbarData = remember { SnackbarHostState() }
    val state by mvi.subscribe { action ->
        when (action) {
            is Action.Failure ->
                snackbarData.showSnackbar(message = action.throwable.getDefaultDisplayMessage(strings))

            is Action.NavigateToCreateAccount ->
                navigateToConfiguring(action.verificationHash.string)

            is Action.NavigateToHome ->
                navigateToHome()

            Action.TooManyAttempts ->
                snackbarData.showSnackbar(message = strings.tooManyAttempts)

            Action.AttemptIsFailed ->
                snackbarData.showSnackbar(message = strings.confirmationAttemptFailed)
        }
    }

    Scaffold(
        topBar = {
            AppBar(
                navigationIcon = {
                    IconButton(
                        onClick = onBack,
                    ) {
                        Icon(Icons.Rounded.ArrowBack, contentDescription = null)
                    }
                },
                title = LocalStrings.current.confirmation,
            )
        },
    ) { rootPaddings ->
        val supportText = state.code.getFailuresIfPresent(strings)

        Column(modifier = Modifier.fillMaxHeight().padding(rootPaddings).padding(16.dp)) {
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                leadingIcon = { Icon(Icons.Outlined.Lock, contentDescription = null) },
                value = state.code.value,
                onValueChange = { mvi.store.intent(Intent.CodeChange(it)) },
                label = { Text(LocalStrings.current.confirmation) },
                isError = supportText != null,
                supportingText = { if (supportText != null) Text(supportText) },
                enabled = !state.isLoading,
            )

            Spacer(Modifier.weight(1f))

            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                SnackbarHost(
                    hostState = snackbarData,
                ) {
                    Snackbar(it)
                }

                ButtonWithProgress(
                    primary = true,
                    modifier = Modifier.fillMaxWidth(),
                    onClick = { mvi.store.intent(Intent.OnConfirmClicked) },
                    enabled = !state.isLoading && state.canSendRequest,
                    isLoading = state.isLoading
                ) {
                    Text(text = LocalStrings.current.nextStep)
                }
            }
        }
    }
}
