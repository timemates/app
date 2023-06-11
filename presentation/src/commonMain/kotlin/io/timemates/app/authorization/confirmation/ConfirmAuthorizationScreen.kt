package io.timemates.app.authorization.confirmation

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Settings
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import io.timemates.app.authorization.confirmation.mvi.ConfirmAuthorizationStateMachine
import io.timemates.app.authorization.confirmation.mvi.ConfirmAuthorizationStateMachine.*
import io.timemates.app.localization.LocalStrings
import io.timemates.app.style.system.appbar.TopAppBar
import io.timemates.app.style.system.button.Button
import io.timemates.app.style.system.input.OutlineInput
import io.timemates.app.style.system.scaffold.Scaffold

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ConfirmAuthorizationScreen(stateMachine: ConfirmAuthorizationStateMachine) {
    val state by stateMachine.state.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = LocalStrings.email,
            )
        },
    ) {
        Row {
            OutlineInput(
                modifier = Modifier.fillMaxWidth(),
                value = state.code,
                onInput = { stateMachine.dispatchEvent(Event.CodeChange(it)) },
                label = LocalStrings.email,
                isError = state.isCodeInvalid,
                supportText = when {
                    state.isCodeInvalid -> LocalStrings.emailIsInvalid
                    else -> null
                },
                isEnabled = !state.isLoading,
            )

            Spacer(Modifier.weight(1f))

            Button(
                modifier = Modifier.fillMaxWidth(),
                primary = true,
                text = LocalStrings.start,
                onClick = { stateMachine.dispatchEvent(Event.OnConfirmClicked) },
            )
        }
    }
}