package io.timemates.app.timers.ui.create_timer

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import io.timemates.app.localization.compose.LocalStrings
import io.timemates.app.style.system.appbar.AppBar
import io.timemates.app.style.system.button.ButtonWithProgress
import io.timemates.app.timers.ui.create_timer.mvi.CreateTimerStateMachine
import io.timemates.app.timers.ui.create_timer.mvi.CreateTimerStateMachine.Event

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateTimerScreen(
    stateMachine: CreateTimerStateMachine
) {
    val state by stateMachine.state.collectAsState()
    val strings = LocalStrings.current

    Scaffold(
        topBar = {
            AppBar(
                navigationIcon = {
                    IconButton(
                        onClick = { stateMachine.dispatchEvent(Event.OnBackClick)  },
                    ) {
                        Icon(Icons.Rounded.ArrowBack, contentDescription = null)
                    }
                },
                title = strings.addTimer,
            )
        }
    ) { rootPaddings ->
        Column(
            modifier = Modifier.fillMaxSize()
                .padding(rootPaddings)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            Column {
                ButtonWithProgress(
                    primary = true,
                    modifier = Modifier.fillMaxWidth(),
                    onClick = { stateMachine.dispatchEvent(Event.OnCreateClick) },
                    enabled = false,
                    isLoading = false
                ) {
                    Text(text = LocalStrings.current.done)
                }
            }
        }
    }
}