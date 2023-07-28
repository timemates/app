package io.timemates.app.authorization.ui.confirmation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import io.timemates.app.authorization.ui.confirmation.mvi.ConfirmAuthorizationStateMachine
import io.timemates.app.authorization.ui.confirmation.mvi.ConfirmAuthorizationStateMachine.Event
import io.timemates.app.localization.LocalStrings
import io.timemates.app.style.system.appbar.AppBar
import io.timemates.app.style.system.button.Button
import kotlinx.coroutines.channels.consumeEach

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ConfirmAuthorizationScreen(
    stateMachine: ConfirmAuthorizationStateMachine,
    onBack: () -> Unit,
    navigateToConfiguring: (String) -> Unit,
    navigateToHome: () -> Unit,
) {
    val state by stateMachine.state.collectAsState()
    val snackbarData = remember { SnackbarHostState() }

    val strings = LocalStrings.current

    LaunchedEffect(Unit) {
        stateMachine.effects.consumeEach { effect ->
            when (effect) {
                is ConfirmAuthorizationStateMachine.Effect.Failure ->
                    snackbarData.showSnackbar(message = strings.unknownFailure)

                is ConfirmAuthorizationStateMachine.Effect.NavigateToCreateAccount ->
                    navigateToConfiguring(effect.verificationHash.string)

                is ConfirmAuthorizationStateMachine.Effect.NavigateToHome ->
                    navigateToHome()

                ConfirmAuthorizationStateMachine.Effect.TooManyAttempts ->
                    snackbarData.showSnackbar(message = strings.tooManyAttempts)

                ConfirmAuthorizationStateMachine.Effect.AttemptIsFailed ->
                    snackbarData.showSnackbar(message = strings.confirmationAttemptFailed)
            }
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
        val supportText = when {
            state.isCodeInvalid -> LocalStrings.current.codeIsInvalid
            state.isCodeSizeInvalid -> LocalStrings.current.codeSizeIsInvalid
            else -> null
        }

        Column(modifier = Modifier.fillMaxHeight().padding(rootPaddings).padding(16.dp)) {
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                leadingIcon = { Icon(Icons.Outlined.Lock, contentDescription = null) },
                value = state.code,
                onValueChange = { stateMachine.dispatchEvent(Event.CodeChange(it)) },
                label = { Text(LocalStrings.current.confirmation) },
                isError = state.isCodeInvalid || state.isCodeSizeInvalid,
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

                Button(
                    primary = true,
                    modifier = Modifier.fillMaxWidth(),
                    onClick = { stateMachine.dispatchEvent(Event.OnConfirmClicked) },
                    enabled = !state.isLoading,
                ) {
                    if (state.isLoading) {
                        CircularProgressIndicator(
                            modifier = Modifier.size(20.dp),
                            strokeWidth = 3.dp
                        )
                    } else {
                        Text(text = LocalStrings.current.nextStep)
                    }
                }
            }
        }
    }
}