package io.timemates.app.style.system.appbar

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.BoxScopeInstance.align
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import io.timemates.app.style.system.AppTheme
import javax.swing.Box

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