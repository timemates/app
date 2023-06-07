package io.timemates.app.style.system.cards

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import io.timemates.app.style.system.AppTheme

@Composable
fun OutlineCard(
    block: @Composable () -> Unit,
) {
    Box(
        modifier = Modifier.fillMaxWidth()
            .padding(vertical = AppTheme.sizes.xs, horizontal = AppTheme.sizes.s),
    ) {
        block()
    }
}