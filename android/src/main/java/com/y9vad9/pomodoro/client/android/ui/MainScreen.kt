package com.y9vad9.pomodoro.client.android.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.Timer
import androidx.compose.material.icons.outlined.Web
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.y9vad9.pomodoro.client.localization.CurrentStrings

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
@Preview
fun MainScreen() {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = CurrentStrings.appName,
                        fontWeight = FontWeight.ExtraBold
                    )
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .consumedWindowInsets(padding)
        ) {
            Box(modifier = Modifier.weight(1f).fillMaxWidth()) {
                LocalTimersScreen()
            }
            NavigationBar(modifier = Modifier.fillMaxWidth()) {
                NavigationBarItem(
                    selected = true,
                    onClick = {},
                    icon = { Icon(Icons.Outlined.Timer, null) },
                    label = { Text(CurrentStrings.localTimers) }
                )
                NavigationBarItem(
                    selected = false,
                    onClick = {},
                    icon = { Icon(Icons.Outlined.Web, null) },
                    label = { Text(CurrentStrings.onlineTimers) }
                )
                NavigationBarItem(
                    selected = false,
                    onClick = {},
                    icon = { Icon(Icons.Outlined.AccountCircle, null) },
                    label = { Text(CurrentStrings.profile) }
                )
            }
        }
    }
}