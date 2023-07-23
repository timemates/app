package io.timemates.app.authorization.ui.start

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import io.timemates.app.authorization.ui.start.mvi.StartAuthorizationStateMachine
import io.timemates.app.authorization.ui.start.mvi.StartAuthorizationStateMachine.*
import io.timemates.app.localization.LocalStrings
import io.timemates.app.style.system.button.Button
import io.timemates.sdk.authorization.email.types.value.VerificationHash
import kotlinx.coroutines.channels.consumeEach

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StartAuthorizationScreen(
    stateMachine: StartAuthorizationStateMachine,
    onNavigateToConfirmation: (VerificationHash) -> Unit,
) {
    val state by stateMachine.state.collectAsState()
    val (isSnackBarShown, setIsSnackBarShown) = remember { mutableStateOf(false) }
    val (snackbarText, setSnackbarText) = remember { mutableStateOf("") }

    val strings = LocalStrings.current


    LaunchedEffect(true) {
        stateMachine.effects.consumeEach { effect ->
            when (effect) {
                is Effect.Failure -> {
                    setIsSnackBarShown(true)
                    setSnackbarText(strings.unknownFailure)
                }

                is Effect.NavigateToConfirmation -> onNavigateToConfirmation(effect.verificationHash)
                Effect.TooManyAttempts -> {
                    setIsSnackBarShown(true)
                    setSnackbarText(strings.tooManyAttempts)
                }
            }
        }
    }
    Scaffold {
        Box(modifier = Modifier.fillMaxSize().padding(16.dp)) {
            Column(
                modifier = Modifier.align(Alignment.Center).padding(bottom = ButtonDefaults.MinHeight),
                verticalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterVertically),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Text(
                    text = LocalStrings.current.authorization,
                    style = MaterialTheme.typography.displaySmall,
                    color = MaterialTheme.colorScheme.primary,
                )
                Text(
                    modifier = Modifier.alpha(0.6f),
                    text = LocalStrings.current.authorizationDescription,
                    style = MaterialTheme.typography.bodySmall,
                    textAlign = TextAlign.Center,
                )
                val supportText = when {
                    state.isEmailInvalid -> LocalStrings.current.emailIsInvalid
                    state.isEmailLengthSizeInvalid -> LocalStrings.current.emailSizeIsInvalid
                    else -> null
                }

                OutlinedTextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = state.email,
                    onValueChange = { stateMachine.dispatchEvent(Event.EmailChange(it)) },
                    label = { Text(LocalStrings.current.email) },
                    isError = state.isEmailInvalid || state.isEmailLengthSizeInvalid,
                    supportingText = { if (supportText != null) Text(supportText) },
                    readOnly = state.isLoading,
                )
            }

            Button(
                modifier = Modifier.fillMaxWidth().align(Alignment.BottomCenter),
                onClick = { stateMachine.dispatchEvent(Event.OnStartClick) },
            ) {
                Text(text = LocalStrings.current.start)
            }
        }
    }
}