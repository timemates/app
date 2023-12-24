package io.timemates.app.style.system.images

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import io.timemates.app.style.system.theme.AppTheme

@Composable
fun CircleIcon(
    modifier: Modifier = Modifier,
    painter: ImageVector,
    contentDescription: String? = null,
) {
    Box(
        modifier = modifier.size(45.dp)
            .background(AppTheme.colors.primary, shape = CircleShape)
    ) {
        Icon(
            modifier = Modifier.size(24.dp)
                .align(Alignment.Center)
                .background(AppTheme.colors.primary, shape = CircleShape),
            imageVector = painter,
            tint = AppTheme.colors.onPrimary,
            contentDescription = contentDescription,
        )
    }
}