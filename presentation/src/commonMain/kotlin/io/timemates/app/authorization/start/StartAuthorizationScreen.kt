package io.timemates.app.authorization.start

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Snackbar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import io.timemates.app.authorization.start.mvi.StartAuthorizationStateMachine
import io.timemates.app.authorization.start.mvi.StartAuthorizationStateMachine.*
import io.timemates.app.localization.LocalStrings
import io.timemates.app.style.system.appbar.TopAppBar
import io.timemates.app.style.system.button.Button
import io.timemates.app.style.system.input.OutlineInput
import io.timemates.app.style.system.scaffold.Scaffold
import io.timemates.app.style.system.text.Text
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

    val strings = LocalStrings


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

    Scaffold(
        topBar = {
            TopAppBar(
                titleText = LocalStrings.email,
            )
        },
        snackbarHost = {
            if (isSnackBarShown) {
                Snackbar {
                    Text(
                        text = snackbarText,
                    )
                }
            }

        },
    ) {
        Row {
            OutlineInput(
                modifier = Modifier.fillMaxWidth(),
                value = state.email,
                onInput = { stateMachine.dispatchEvent(Event.EmailChange(it)) },
                label = LocalStrings.email,
                isError = state.isEmailInvalid || state.isEmailLengthSizeInvalid,
                supportText = when {
                    state.isEmailInvalid -> LocalStrings.emailIsInvalid
                    state.isEmailLengthSizeInvalid -> LocalStrings.emailSizeIsInvalid
                    else -> null
                },
                isEnabled = !state.isLoading,
            )

            Spacer(Modifier.weight(1f))

            Button(
                modifier = Modifier.fillMaxWidth(),
                primary = true,
                text = LocalStrings.start,
                onClick = { stateMachine.dispatchEvent(Event.OnStartClick) },
            )
        }
    }
}