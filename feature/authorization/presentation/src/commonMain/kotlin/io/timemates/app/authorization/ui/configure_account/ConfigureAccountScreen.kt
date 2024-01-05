package io.timemates.app.authorization.ui.configure_account

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.PersonOutline
import androidx.compose.material.icons.rounded.ArrowBack
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
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import io.timemates.app.authorization.ui.configure_account.mvi.ConfigureAccountStateMachine.Effect
import io.timemates.app.authorization.ui.configure_account.mvi.ConfigureAccountStateMachine.Event
import io.timemates.app.authorization.ui.configure_account.mvi.ConfigureAccountStateMachine.State
import io.timemates.app.feature.common.failures.getDefaultDisplayMessage
import io.timemates.app.foundation.mvi.StateMachine
import io.timemates.app.localization.compose.LocalStrings
import io.timemates.app.style.system.appbar.AppBar
import io.timemates.app.style.system.button.ButtonWithProgress
import io.timemates.sdk.users.profile.types.value.UserDescription
import io.timemates.sdk.users.profile.types.value.UserName
import kotlinx.coroutines.channels.consumeEach

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ConfigureAccountScreen(
    stateMachine: StateMachine<State, Event, Effect>,
    navigateToHome: () -> Unit,
    onBack: () -> Unit,
) {
    val state by stateMachine.state.collectAsState()
    val snackbarData = remember { SnackbarHostState() }

    val nameSize = remember(state.name) { state.name.length }
    val aboutYouSize = remember(state.aboutYou) { state.aboutYou.length }

    val strings = LocalStrings.current

    LaunchedEffect(Unit) {
        stateMachine.effects.consumeEach { effect ->
            when (effect) {
                is Effect.Failure ->
                    snackbarData.showSnackbar(message = effect.throwable.getDefaultDisplayMessage(strings))

                is Effect.NavigateToHomePage -> navigateToHome()
                Effect.NavigateToStart -> onBack()
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
        val nameSupportText = when {
            state.isNameSizeInvalid -> LocalStrings.current.nameSizeIsInvalid
            else -> null
        }
        val aboutYouSupportText = when {
            state.isAboutYouSizeInvalid -> LocalStrings.current.aboutYouSizeIsInvalid
            else -> null
        }

        Column(
            modifier = Modifier.fillMaxHeight().padding(rootPaddings).padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                leadingIcon = { Icon(Icons.Outlined.PersonOutline, contentDescription = null) },
                value = state.name,
                onValueChange = { stateMachine.dispatchEvent(Event.NameIsChanged(it)) },
                label = { Text(LocalStrings.current.yourName) },
                isError = state.isNameSizeInvalid || nameSize > UserName.SIZE_RANGE.last,
                singleLine = true,
                supportingText = {
                    if (nameSupportText != null) {
                        Text(nameSupportText)
                    } else {
                        Text(
                            modifier = Modifier.fillMaxWidth(),
                            text = "$nameSize / ${UserName.SIZE_RANGE.last}",
                            textAlign = TextAlign.End
                        )
                    }
                },
                enabled = !state.isLoading,
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Send)
            )

            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = state.aboutYou,
                onValueChange = { stateMachine.dispatchEvent(Event.DescriptionIsChanged(it)) },
                label = { Text(LocalStrings.current.aboutYou) },
                isError = state.isAboutYouSizeInvalid || aboutYouSize > UserDescription.SIZE_RANGE.last,
                maxLines = 5,
                supportingText = {
                    if (aboutYouSupportText != null) {
                        Text(aboutYouSupportText)
                    } else {
                        Text(
                            modifier = Modifier.fillMaxWidth(),
                            text = "$aboutYouSize / ${UserDescription.SIZE_RANGE.last}",
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
                    hostState = snackbarData,
                ) {
                    Snackbar(it)
                }
                ButtonWithProgress(
                    primary = true,
                    modifier = Modifier.fillMaxWidth(),
                    onClick = { stateMachine.dispatchEvent(Event.OnDoneClicked) },
                    enabled = !state.isLoading,
                    isLoading = state.isLoading
                ) {
                    Text(text = LocalStrings.current.done)
                }
            }
        }
    }
}