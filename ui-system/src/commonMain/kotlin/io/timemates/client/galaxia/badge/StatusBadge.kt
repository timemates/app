package io.timemates.client.galaxia.badge

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import io.timemates.client.galaxia.context.GalaxiaContext
import io.timemates.client.galaxia.context.GalaxiaTheme
import io.timemates.client.galaxia.text.AtomText
import io.timemates.client.galaxia.text.Text
import io.timemates.client.galaxia.text.text
import io.timemates.client.galaxia.text.uppercase

@Composable
fun StatusBadge(
    text: Text,
    statusType: StatusType,
) {
    val color = when(statusType) {
        StatusType.ATTENTION -> GalaxiaContext.colors.figureFocus
        StatusType.RELAX -> GalaxiaContext.colors.figurePause
        StatusType.POSITIVE -> GalaxiaContext.colors.figurePositive
    }

    AtomText(
        modifier = Modifier
            .border(width = 1.dp, color = color, shape = GalaxiaContext.shapes.circle)
            .padding(vertical = GalaxiaContext.sizes.xxxs, horizontal = GalaxiaContext.sizes.xxs),
        text = text.uppercase(),
        textStyle = GalaxiaContext.typography.caption,
        textColor = color,
    )
}

enum class StatusType {
    ATTENTION, RELAX, POSITIVE,
}

@Preview
@Composable
private fun StatusBadgePreview() {
    GalaxiaTheme(darkTheme = false) {
        Column(modifier = Modifier.fillMaxWidth(), verticalArrangement = Arrangement.spacedBy(8.dp)) {
            StatusBadge(
                text = text("Online"),
                statusType = StatusType.POSITIVE,
            )

            StatusBadge(
                text = text("24 minutes"),
                statusType = StatusType.ATTENTION,
            )

            StatusBadge(
                text = text("4 minutes"),
                statusType = StatusType.RELAX,
            )
        }
    }
}