package io.timemates.app.style.system.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.ProvidableCompositionLocal
import androidx.compose.runtime.Stable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.sp

@Immutable
data class AppTypography(
    val labelSuperLarge: TextStyle
)

@Stable
@Composable
fun appTypography(): AppTypography {
    return AppTypography(MaterialTheme.typography.labelLarge.copy(fontSize = 16.sp))
}

val LocalAppTypography: ProvidableCompositionLocal<AppTypography> = staticCompositionLocalOf {
    error("LocalAppTypography is not provided.")
}