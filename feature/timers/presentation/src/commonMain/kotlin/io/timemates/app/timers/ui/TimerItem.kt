package io.timemates.app.timers.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import io.timemates.app.localization.compose.LocalStrings
import io.timemates.app.style.system.theme.AppTheme
import io.timemates.sdk.timers.types.Timer
import io.timemates.sdk.timers.types.Timer.State.ConfirmationWaiting
import io.timemates.sdk.timers.types.Timer.State.Inactive
import io.timemates.sdk.timers.types.Timer.State.Paused
import io.timemates.sdk.timers.types.Timer.State.Rest
import io.timemates.sdk.timers.types.Timer.State.Running
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.minus
import kotlinx.datetime.toLocalDateTime

@Composable
fun TimerItem(
    timer: Timer,
    onClick: () -> Unit,
) {
    OutlinedCard(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(
                onClick = onClick
            ),
        border = BorderStroke(1.dp, AppTheme.colors.secondary),
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Column(
                modifier = Modifier.padding(12.dp),
            ) {
                Row(
                    modifier = Modifier,
                ) {
                    Text(
                        text = timer.description.string,
                        modifier = Modifier,
                        style = MaterialTheme.typography.titleMedium,
                    )
                    OnlineIndicator(
                        modifier = Modifier.padding(3.dp),
                        isOnline = timer.state is Running,
                    )
                }
                Text(
                    text = timerItemSubtext(timer),
                    modifier = Modifier,
                    style = MaterialTheme.typography.bodyMedium,
                )
            }
            Box(
                modifier = Modifier.fillMaxWidth(),
            ) {
                Icon(
                    imageVector = Icons.Filled.ArrowForward,
                    contentDescription = "Navigate To Timer",
                    modifier = Modifier
                        .padding(12.dp)
                        .align(Alignment.CenterEnd),
                    tint = Color.Gray,
                )
            }
        }
    }
}

@Stable
@Composable
private fun timerItemSubtext(timer: Timer): String {
    return when(timer.state) {
        is Running, is Rest  -> LocalStrings.current.runningTimerDescription(timer.membersCount.int)
        is Paused, is Inactive -> LocalStrings.current.inactiveTimerDescription(daysSinceInactive(timer.state.publishTime))
        is ConfirmationWaiting -> LocalStrings.current.confirmationWaitingTimerDescription
    }
}

@Stable
private fun daysSinceInactive(pausedInstant: Instant): Int {
    val currentInstant = Clock.System.now()
    return (currentInstant.toLocalDateTime(TimeZone.UTC).date.minus(pausedInstant.toLocalDateTime(TimeZone.UTC).date)).days
}

@Composable
private fun OnlineIndicator(
    modifier: Modifier = Modifier,
    isOnline: Boolean,
    indicatorSize: Dp = 6.dp,
    onlineColor: Color = AppTheme.colors.positive,
) {
    Box(
        modifier = modifier
            .size(indicatorSize)
            .background(
                shape = CircleShape,
                brush = Brush.radialGradient(
                    colors = if (isOnline) listOf(onlineColor, onlineColor) else listOf(Color.Transparent, Color.Transparent),
                    radius = 60f
                ),
            ),
    )
}
