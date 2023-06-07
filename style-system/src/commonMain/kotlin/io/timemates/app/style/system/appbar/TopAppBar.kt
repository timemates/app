package io.timemates.app.style.system.appbar

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import io.timemates.app.style.system.AppTheme

@Composable
fun TopAppBar(
    modifier: Modifier = Modifier,
    leadingContent: (@Composable () -> Unit)? = null,
    title: String,
    trailingContent: (() -> Unit)? = null,
) {
    Row(
        modifier = Modifier.fillMaxWidth()
            .then(modifier)
            .padding(horizontal = AppTheme.sizes.s),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // TODO: spacing, text style
        leadingContent?.invoke()
        Text(title)
        trailingContent?.invoke()
    }
}