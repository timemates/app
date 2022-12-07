package com.y9vad9.pomodoro.client.android.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.y9vad9.pomodoro.client.android.R
import com.y9vad9.pomodoro.client.android.di.GetLocalTimersUseCase
import com.y9vad9.pomodoro.client.localization.CurrentStrings
import com.y9vad9.pomodoro.client.time.toTimeAgo
import com.y9vad9.pomodoro.client.types.LocalTimer
import com.y9vad9.pomodoro.client.viewmodels.LocalTimersViewModel

@Composable
fun LocalTimersScreen(
    viewModel: LocalTimersViewModel = viewModel(initializer = { LocalTimersViewModel(GetLocalTimersUseCase.current) })
) {
    val isLoading by viewModel.isLoading.collectAsState()
    val list by viewModel.timers.collectAsState()

    BoxWithConstraints(modifier = Modifier.fillMaxSize()) {
        if(isLoading)
            LoadingView()
        else if(list.isEmpty())
            NoTimers()
        else LocalTimersList(list)

        FloatingActionButton(
            modifier = Modifier.align(Alignment.BottomEnd).padding(16.dp),
            onClick = {}
        ) {
            Icon(Icons.Rounded.Add, null)
        }
    }
}

@Composable
private fun BoxWithConstraintsScope.NoTimers() {
    Column(Modifier.align(Alignment.Center), horizontalAlignment = Alignment.CenterHorizontally) {
        Image(
            painterResource(R.drawable.alarm_clock_running),
            contentDescription = null
        )
        Text(
            text = CurrentStrings.noTimers,
            fontWeight = FontWeight.Light
        )
    }
}

@Composable
private fun BoxWithConstraintsScope.LoadingView() {
    CircularProgressIndicator(Modifier.align(Alignment.Center))
}

@Composable
private fun LocalTimersList(list: List<LocalTimer>) {
    LazyColumn(Modifier.fillMaxSize()) {
        items(list) { item ->
            LocalTimerItem(item)
        }
    }
}

@Composable
private fun LocalTimerItem(localTimer: LocalTimer) {
    Row(
        modifier = Modifier.fillMaxWidth()
            .padding(8.dp)
            .background(Color.Gray, RectangleShape),
    ) {
        Text(localTimer.name.string, fontWeight = FontWeight.Bold)
        Text(
            localTimer.lastTimeUsed.toTimeAgo() ?: CurrentStrings.timerNotUsed,
            fontWeight = FontWeight.Light
        )
    }
}