package com.timemates.app.preview.style.item

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import io.timemates.app.timers.ui.PlaceholderTimerItem
import io.timemates.app.style.system.theme.AppTheme

@Preview
@Composable
private fun Test() {
    AppTheme(false) {
        Column (
            modifier = Modifier.padding(16.dp)
        ) {
            OutlinedCard(
                modifier = Modifier.fillMaxWidth(),
                border = BorderStroke(1.dp, AppTheme.colors.secondary),
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(1f),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Column(
                        modifier = Modifier.padding(12.dp),
                    ) {
                        Row(
                            modifier = Modifier,
                        ) {
                            Text(
                                text = "TimeMates developing",
                                modifier = Modifier,
                                style = MaterialTheme.typography.titleMedium,
                            )

                            OnlineIndicator(
                                modifier = Modifier.padding(3.dp),
                                isOnline = true,
                            )
                        }

                        Text(
                            text = "You and 2 other people are using this timer.",
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

            Spacer(modifier = Modifier.padding(16.dp))

            PlaceholderTimerItem()
        }
    }
}

@Composable
fun OnlineIndicator(
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
                )
            ),
    )
}
