package io.timemates.client.galaxia.navigation

import androidx.compose.foundation.layout.RowScope
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import io.timemates.client.galaxia.annotations.GalaxiaDsl
import io.timemates.client.galaxia.context.GalaxiaContext

@GalaxiaDsl
@JvmInline
value class NavigationBarScope internal constructor(val rowScope: RowScope)

@Composable
fun NavigationBar(
    modifier: Modifier = Modifier,
    items: @Composable NavigationBarScope.() -> Unit,
) {
    androidx.compose.material3.NavigationBar(
        contentColor = GalaxiaContext.colors.figureSecondary,
        containerColor = GalaxiaContext.colors.bgBase,
        tonalElevation = 0.dp,
    ) {
        items(NavigationBarScope(this))
    }
}