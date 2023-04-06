package io.timemates.client.galaxia.buttons.fab

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import io.timemates.client.galaxia.context.GalaxiaContext
import io.timemates.client.galaxia.context.resources.withDisability
import io.timemates.client.galaxia.image.AtomIcon
import io.timemates.client.galaxia.image.Image
import io.timemates.client.galaxia.text.AtomText
import io.timemates.client.galaxia.text.Text

@Composable
fun SubFloatingActionButton(
    onClick: () -> Unit,
    icon: Image.Icon,
    text: Text,
    enabled: Boolean = true,
    contentDescription: String? = null,
) {
    Row(
        modifier = Modifier.clip(GalaxiaContext.shapes.cornerXs)
            .background(GalaxiaContext.colors.bgHighest)
            .padding(GalaxiaContext.sizes.xxs)
            .clickable(onClick = onClick),
        horizontalArrangement = Arrangement.spacedBy(GalaxiaContext.sizes.xxs),
    ) {
        AtomIcon(
            modifier = Modifier.size(GalaxiaContext.sizes.s)
                .alpha(GalaxiaContext.alpha.withDisability(enabled)),
            icon = icon,
            tint = GalaxiaContext.colors.figureBase,
            contentDescription = contentDescription,
        )

        AtomText(
            modifier = Modifier.alpha(GalaxiaContext.alpha.withDisability(enabled)),
            text = text,
            textStyle = GalaxiaContext.typography.caption
        )
    }
}