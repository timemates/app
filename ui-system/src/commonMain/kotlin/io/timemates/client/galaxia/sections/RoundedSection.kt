package io.timemates.client.galaxia.sections

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.LocalContentAlpha
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import io.timemates.client.galaxia.context.GalaxiaContext
import io.timemates.client.galaxia.context.resources.withDisability

@Composable
internal fun RoundedSection(
    onClick: () -> Unit,
    enabled: Boolean = true,
    modifier: Modifier = Modifier,
    content: @Composable ColumnScope.() -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(GalaxiaContext.shapes.cornerXxs)
            .background(GalaxiaContext.colors.bgHigh)
            .padding(GalaxiaContext.sizes.xs)
            .clickable(enabled, onClick = onClick)
            .then(modifier)
    ) {
        CompositionLocalProvider(LocalContentAlpha provides GalaxiaContext.alpha.withDisability(enabled)) {
            content()
        }
    }
}