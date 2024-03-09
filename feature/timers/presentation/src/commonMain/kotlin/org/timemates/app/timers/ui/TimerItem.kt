package org.timemates.app.timers.ui

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
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material3.CardDefaults
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
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.minus
import kotlinx.datetime.toLocalDateTime
import org.timemates.app.core.types.serializable.SerializableTimer
import org.timemates.app.core.types.serializable.SerializableTimer.State.ConfirmationWaiting
import org.timemates.app.core.types.serializable.SerializableTimer.State.Inactive
import org.timemates.app.core.types.serializable.SerializableTimer.State.Paused
import org.timemates.app.core.types.serializable.SerializableTimer.State.Rest
import org.timemates.app.core.types.serializable.SerializableTimer.State.Running
import org.timemates.app.feature.common.providable.LocalTimeProvider
import org.timemates.app.localization.compose.LocalStrings
import org.timemates.app.style.system.theme.AppTheme

@Composable
fun TimerItem(
    timer: SerializableTimer,
    onClick: () -> Unit,
) {
    OutlinedCard(
        modifier = Modifier
            .fillMaxWidth(),
        border = BorderStroke(1.dp, AppTheme.colors.secondary),
        colors = CardDefaults.outlinedCardColors(containerColor = AppTheme.colors.background),
    ) {
        Row(
            modifier = Modifier.fillMaxWidth().clickable(
                onClick = onClick
            ),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Column(
                modifier = Modifier.padding(12.dp).weight(1f),
            ) {
                Row(
                    modifier = Modifier,
                ) {
                    Text(
                        text = timer.name,
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
                modifier = Modifier.padding(12.dp),
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                    contentDescription = "Navigate To Timer",
                    modifier = Modifier
                        .align(Alignment.CenterEnd),
                    tint = Color.Gray,
                )
            }
        }
    }
}

@Stable
@Composable
private fun timerItemSubtext(timer: SerializableTimer): String {
    return when(timer.state) {
        is Running, is Rest -> LocalStrings.current.runningTimerDescription(timer.membersCount)
        is Paused, is Inactive -> LocalStrings.current.inactiveTimerDescription(daysSinceInactive(timer.state.publishTime))
        is ConfirmationWaiting -> LocalStrings.current.confirmationWaitingTimerDescription
    }
}

@Composable
@Stable
private fun daysSinceInactive(pausedInstant: Instant): Int {
    val currentInstant = LocalTimeProvider.current.provide()
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
