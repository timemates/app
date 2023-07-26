package io.timemates.app.authorization.ui.confirmation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import io.timemates.app.authorization.ui.confirmation.mvi.ConfirmAuthorizationStateMachine
import io.timemates.app.authorization.ui.confirmation.mvi.ConfirmAuthorizationStateMachine.Event
import io.timemates.app.localization.LocalStrings
import io.timemates.app.style.system.appbar.AppBar
import io.timemates.app.style.system.button.Button

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ConfirmAuthorizationScreen(
    stateMachine: ConfirmAuthorizationStateMachine,
    onBack: () -> Unit,
) {
    val state by stateMachine.state.collectAsState()

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
            state.isCodeInvalid -> LocalStrings.current.emailIsInvalid
            else -> null
        }

        Column(modifier = Modifier.fillMaxHeight().padding(rootPaddings).padding(16.dp)) {
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
                primary = true,
                modifier = Modifier.fillMaxWidth(),
                onClick = { stateMachine.dispatchEvent(Event.OnConfirmClicked) },
            ) {
                Text(LocalStrings.current.nextStep)
            }
        }
    }
}