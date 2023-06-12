package io.timemates.app.style.system.appbar

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.size
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import io.timemates.app.style.system.AppTheme

@Composable
fun TopAppBar(
    modifier: Modifier = Modifier,
    leadingContent: (@Composable BoxScope.() -> Unit)? = null,
    title: String,
    trailingContent: (@Composable BoxScope.() -> Unit)? = null,
) {
    Row(
        modifier = Modifier.fillMaxWidth()
            .then(modifier)
            .padding(horizontal = AppTheme.sizes.s)
            .requiredHeight(AppBarSize),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // TODO: spacing, text style
        leadingContent?.let {
            Box(modifier = Modifier.size(AppBarSize)) {
                it()
            }
        }
        Text(title)
        trailingContent?.let {
            Box(modifier = Modifier.size(AppBarSize)) {
                it()
            }
        }
    }
}

@Composable
fun TopAppBar(
    modifier: Modifier = Modifier,
    homeButtonIcon: ImageVector? = null,
    titleText: String,
    trailingButtonIcon: ImageVector? = null,
) {
    TopAppBar(
        modifier,
        leadingContent = {
            IconButton(
                modifier = Modifier.align(Alignment.Center),
            )
        }
    )
}

private val AppBarSize = 64.dp