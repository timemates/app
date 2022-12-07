package com.y9vad9.pomodoro.client.android.ui

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AccountCircle
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.y9vad9.pomodoro.client.localization.CurrentStrings

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
@Preview
fun AuthorizationScreen() {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = { Text(CurrentStrings.authorization) }
            )
        }
    ) { padding ->
        BoxWithConstraints(Modifier.consumedWindowInsets(padding).fillMaxSize()) {
            Button(
                modifier = Modifier.align(Alignment.Center),
                onClick = {}
            ) {
                Icon(Icons.Rounded.AccountCircle, CurrentStrings.signInViaGoogle)
                Spacer(Modifier.size(8.dp))
                Text(CurrentStrings.signInViaGoogle)
            }
        }
    }
}