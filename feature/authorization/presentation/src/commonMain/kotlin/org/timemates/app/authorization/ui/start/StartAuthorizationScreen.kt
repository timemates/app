package org.timemates.app.authorization.ui.start

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.timemates.sdk.authorization.email.types.value.VerificationHash
import kotlinx.coroutines.channels.consumeEach
import org.timemates.app.authorization.ui.start.mvi.StartAuthorizationScreenComponent.Effect
import org.timemates.app.authorization.ui.start.mvi.StartAuthorizationScreenComponent.Event
import org.timemates.app.authorization.ui.start.mvi.StartAuthorizationScreenComponent.State
import org.timemates.app.feature.common.failures.getDefaultDisplayMessage
import org.timemates.app.foundation.mvi.MVI
import org.timemates.app.localization.compose.LocalStrings
import org.timemates.app.style.system.appbar.AppBar
import org.timemates.app.style.system.button.ButtonWithProgress

@Composable
fun StartAuthorizationScreen(
    mvi: MVI<State, Event, Effect>,
    onNavigateToConfirmation: (VerificationHash) -> Unit,
) {
    val state by mvi.state.collectAsState()
    val snackbarData = remember { SnackbarHostState() }

    val strings = LocalStrings.current

    LaunchedEffect(true) {
        mvi.effects.consumeEach { effect ->
            when (effect) {
                is Effect.Failure -> {
                    effect.throwable.printStackTrace()

                    snackbarData.showSnackbar(
                        message = effect.throwable.getDefaultDisplayMessage(strings),
                        actionLabel = strings.dismiss,
                        duration = SnackbarDuration.Long,
                    )
                }

                is Effect.NavigateToConfirmation -> onNavigateToConfirmation(effect.verificationHash)
                Effect.TooManyAttempts -> {
                    snackbarData.showSnackbar(
                        message = strings.tooManyAttempts,
                        actionLabel = strings.dismiss,
                        duration = SnackbarDuration.Long,
                    )
                }
            }
        }
    }
    Scaffold(
        topBar = {
            AppBar(
                title = LocalStrings.current.authorization
            )
        },
    ) { rootPadding ->
        Box(modifier = Modifier.fillMaxSize().padding(rootPadding).padding(16.dp)) {
            Column(
                modifier = Modifier.padding(bottom = ButtonDefaults.MinHeight),
                verticalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterVertically),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                val supportText = when {
                    state.isEmailInvalid -> LocalStrings.current.emailIsInvalid
                    state.isEmailLengthSizeInvalid -> LocalStrings.current.emailSizeIsInvalid
                    else -> null
                }

                OutlinedTextField(
                    modifier = Modifier.fillMaxWidth(),
                    leadingIcon = { Icon(Icons.Outlined.Email, contentDescription = null) },
                    value = state.email,
                    onValueChange = { mvi.dispatchEvent(Event.EmailChange(it)) },
                    label = { Text(LocalStrings.current.email) },
                    isError = state.isEmailInvalid || state.isEmailLengthSizeInvalid,
                    supportingText = { if (supportText != null) Text(supportText) },
                    enabled = !state.isLoading,
                    singleLine = true,
                )
            }


            Column(
                modifier = Modifier.fillMaxWidth().align(Alignment.BottomEnd),
                verticalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                SnackbarHost(
                    hostState = snackbarData,
                ) {
                    Snackbar(it)
                }

                ButtonWithProgress(
                    enabled = !state.isLoading,
                    primary = true,
                    modifier = Modifier.fillMaxWidth(),
                    onClick = { mvi.dispatchEvent(Event.OnStartClick) },
                    isLoading = state.isLoading
                ) {
                    Text(text = LocalStrings.current.start)
                }
            }
        }
    }
}