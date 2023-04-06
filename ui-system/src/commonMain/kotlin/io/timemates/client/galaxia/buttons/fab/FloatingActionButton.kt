package io.timemates.client.galaxia.buttons.fab

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import io.timemates.client.galaxia.context.GalaxiaContext
import io.timemates.client.galaxia.context.GalaxiaTheme
import io.timemates.client.galaxia.image.AtomIcon
import io.timemates.client.galaxia.image.Image
import io.timemates.client.galaxia.image.image

@Composable
fun FloatingActionButton(
    onClick: () -> Unit,
    icon: Image.Icon,
    contentDescription: String? = null,
) {
    Box(
        modifier = Modifier.clip(GalaxiaContext.shapes.circle)
            .background(GalaxiaContext.colors.bgHighest)
            .padding(GalaxiaContext.sizes.xs)
            .clickable(onClick = onClick)
    ) {
        AtomIcon(
            modifier = Modifier.size(GalaxiaContext.sizes.l),
            icon = icon,
            tint = GalaxiaContext.colors.figureBase,
            contentDescription = contentDescription,
        )
    }
}

@Preview
@Composable
private fun FloatingActionButtonPreview() {
    Column(
        modifier = Modifier.fillMaxSize().padding(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        GalaxiaTheme(darkTheme = false) {
            FloatingActionButton(
                onClick = {},
                icon = image(Icons.Rounded.Add)
            )
        }

        GalaxiaTheme(darkTheme = true) {
            FloatingActionButton(
                onClick = {},
                icon = image(Icons.Rounded.Add)
            )
        }
    }
}