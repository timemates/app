package io.timemates.client.galaxia.navigation

import androidx.compose.foundation.layout.RowScope
import androidx.compose.material3.NavigationBarItem
import androidx.compose.runtime.Composable
import io.timemates.client.galaxia.context.GalaxiaContext
import io.timemates.client.galaxia.image.AtomIcon
import io.timemates.client.galaxia.image.Image

@Composable
fun NavigationBarScope.NavigationBarItem(
    selected: Boolean,
    icon: Image.Icon,
    onSelected: () -> Unit,
): Unit = with(rowScope) {
    NavigationBarItem(
        selected = selected,
        icon = {
            AtomIcon(
                icon = icon,
                tint = if(selected) GalaxiaContext.colors.figureInverted else GalaxiaContext.colors.figureSecondary
            )
        },
        onClick = onSelected,
    )
}