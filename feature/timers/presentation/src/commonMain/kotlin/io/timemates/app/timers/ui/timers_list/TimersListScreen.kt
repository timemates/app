package io.timemates.app.timers.ui.timers_list

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import dev.icerock.moko.resources.compose.painterResource
import io.github.skeptick.libres.compose.painterResource
import io.timemates.app.feature.common.failures.getDefaultDisplayMessage
import io.timemates.app.foundation.mvi.StateMachine
import io.timemates.app.localization.compose.LocalStrings
import io.timemates.app.style.system.Resources
import io.timemates.app.style.system.appbar.AppBar
import io.timemates.app.timers.ui.PlaceholderTimerItem
import io.timemates.app.timers.ui.TimerItem
import io.timemates.app.style.system.theme.AppTheme
import io.timemates.app.timers.ui.timers_list.mvi.TimersListStateMachine.Effect
import io.timemates.app.timers.ui.timers_list.mvi.TimersListStateMachine.Event
import io.timemates.app.timers.ui.timers_list.mvi.TimersListStateMachine.State
import io.timemates.app.style.system.button.FloatingActionButton
import io.timemates.sdk.timers.types.value.TimerId
import kotlinx.coroutines.channels.consumeEach

@Composable
fun TimersListScreen(
    stateMachine: StateMachine<State, Event, Effect>,
    navigateToSetting: () -> Unit,
    navigateToTimerCreationScreen: () -> Unit,
    navigateToTimer: (TimerId) -> Unit,
) {
    val state by stateMachine.state.collectAsState()
    val snackbarData = remember { SnackbarHostState() }
    val timersListState = rememberLazyListState()

    val painter: Painter = Resources.image.empty_list_image.painterResource()

    val strings = LocalStrings.current

    LaunchedEffect(true) {
        stateMachine.dispatchEvent(Event.Load)

        stateMachine.effects.consumeEach { effect ->
            when (effect) {
                is Effect.Failure ->
                    snackbarData.showSnackbar(message = effect.throwable.getDefaultDisplayMessage(strings))

                else -> {}
            }
        }
    }

    if(state.hasMoreItems) {
        LaunchedEffect(timersListState.layoutInfo.visibleItemsInfo.lastOrNull()) {
            if (timersListState.isScrolledToTheEnd()) {
                stateMachine.dispatchEvent(Event.Load)
            }
        }
    }

    Scaffold(
        topBar = {
            AppBar(
                title = LocalStrings.current.appName,
                action = {
                    IconButton(
                        onClick = { navigateToSetting() },
                        modifier = Modifier,
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.Settings,
                            contentDescription = null,
                        )
                    }
                },
                modifier = Modifier,
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { navigateToTimerCreationScreen() },
                modifier = Modifier,
                containerColor = AppTheme.colors.primary,
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Navigate to Create Timer Screen Button",
                    modifier = Modifier,
                )
            }
        }
    ) { rootPaddings ->
        if (!state.isLoading && state.timersList.isEmpty()) {
            Column(
                modifier = Modifier.fillMaxSize()
                    .padding(rootPaddings)
            ) {
                Column(
                    modifier = Modifier.fillMaxWidth().weight(1f),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
//                    Icon(
//                        modifier = Modifier,
//                        painter = painter,
//                        contentDescription = null,
//                        tint = AppTheme.colors.secondaryVariant,
//                    )
//
//                    Spacer(Modifier.height(8.dp))

                    Text(
                        text = LocalStrings.current.noTimers,
                        color = AppTheme.colors.secondaryVariant,
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.bodyLarge,
                    )
                }
            }
        } else {
            Column(modifier = Modifier.fillMaxSize()) {
                Spacer(Modifier.height(16.dp))

                LazyColumn(
                    modifier = Modifier.fillMaxSize().padding(horizontal = 12.dp),
                    state = timersListState,
                    contentPadding = rootPaddings,
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                ) {
                    items(state.timersList) { timer ->
                        TimerItem(
                            timer = timer,
                            onClick = { navigateToTimer(timer.timerId) }
                        )
                    }

                    if (state.isLoading) {
                        items(5) {
                            PlaceholderTimerItem()
                        }
                    }

                    item {
                        Spacer(Modifier.height(16.dp))
                    }
                }
            }
        }
    }
}

fun LazyListState.isScrolledToTheEnd() : Boolean {
    val lastItem = layoutInfo.visibleItemsInfo.lastOrNull()
    return lastItem == null || lastItem.size + lastItem.offset <= layoutInfo.viewportEndOffset
}
