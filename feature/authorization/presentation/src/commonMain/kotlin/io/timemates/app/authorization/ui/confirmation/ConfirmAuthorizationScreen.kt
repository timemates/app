package io.timemates.app.authorization.ui.confirmation

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import io.timemates.app.authorization.ui.confirmation.mvi.ConfirmAuthorizationStateMachine
import io.timemates.app.authorization.ui.confirmation.mvi.ConfirmAuthorizationStateMachine.*
import io.timemates.app.localization.LocalStrings
import io.timemates.app.style.system.button.Button

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ConfirmAuthorizationScreen(stateMachine: ConfirmAuthorizationStateMachine) {
    val state by stateMachine.state.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(LocalStrings.current.email) },
            )
        },
    ) {
        val supportText = when {
            state.isCodeInvalid -> LocalStrings.current.emailIsInvalid
            else -> null
        }

        Row {
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = state.code,
                onValueChange = { stateMachine.dispatchEvent(Event.CodeChange(it)) },
                label = { Text(LocalStrings.current.confirmation) },
                isError = state.isCodeInvalid,
                supportingText = { if (supportText != null) Text(supportText) },
                enabled = !state.isLoading,
            )

            Spacer(Modifier.weight(1f))

            Button(
                modifier = Modifier.fillMaxWidth(),
                onClick = { stateMachine.dispatchEvent(Event.OnConfirmClicked) },
            ) {
                Text(LocalStrings.current.nextStep)
            }
        }
    }
}