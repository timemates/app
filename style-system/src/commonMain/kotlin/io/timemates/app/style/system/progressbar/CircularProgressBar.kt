package io.timemates.app.style.system.progressbar

import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import io.timemates.app.style.system.AppTheme

@Composable
fun CircularProgressBar(
    modifier: Modifier = Modifier,
    color: Color = AppTheme.colors.onBackground,
) {
    CircularProgressIndicator(
        modifier = Modifier,
        color = color,
    )
}