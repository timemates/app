package io.timemates.client.galaxia.context.resources

import androidx.compose.runtime.*
import androidx.compose.ui.text.PlatformSpanStyle
import androidx.compose.ui.text.PlatformTextStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.BaselineShift
import androidx.compose.ui.text.style.Hyphens
import androidx.compose.ui.unit.sp

@Immutable
data class GalaxiaTypography(
    val h1: TextStyle,
    val h2: TextStyle,
    val h3: TextStyle,
    val subtitle1: TextStyle,
    val subtitle2: TextStyle,
    val body1: TextStyle,
    val body2: TextStyle,
    val button: TextStyle,
    val caption: TextStyle,
    val overline: TextStyle,
)

@Stable
fun galaxiaTypography(): GalaxiaTypography {
    return GalaxiaTypography(
        h1 = TextStyle(fontSize = 24.sp, fontWeight = FontWeight.ExtraBold, fontFamily = Inter),
        h2 = TextStyle(fontSize = 18.sp, fontWeight = FontWeight.Bold, fontFamily = Inter),
        h3 = TextStyle(fontSize = 14.sp, fontWeight = FontWeight.SemiBold, fontFamily = Inter),
        subtitle1 = TextStyle(
            fontWeight = FontWeight.Normal,
            fontSize = 16.sp,
            letterSpacing = 0.15.sp
        ),
        subtitle2 = TextStyle(
            fontWeight = FontWeight.Medium,
            fontSize = 14.sp,
            letterSpacing = 0.1.sp
        ),
        body1 = TextStyle(
            fontWeight = FontWeight.Normal,
            fontSize = 16.sp,
            letterSpacing = 0.5.sp
        ),
        body2 = TextStyle(
            fontWeight = FontWeight.Normal,
            fontSize = 14.sp,
            letterSpacing = 0.25.sp
        ),
        button = TextStyle(
            fontWeight = FontWeight.Medium,
            fontSize = 14.sp,
            letterSpacing = 1.25.sp
        ),
        caption = TextStyle(
            fontWeight = FontWeight.Bold,
            fontSize = 12.sp,
            letterSpacing = 0.5.sp,
            lineHeight = 10.sp,
            fontFeatureSettings = "smcp",
        ),
        overline = TextStyle(
            fontWeight = FontWeight.Normal,
            fontSize = 10.sp,
            letterSpacing = 1.5.sp
        )
    )
}

val LocalTypography: ProvidableCompositionLocal<GalaxiaTypography> = staticCompositionLocalOf { error("Uninitialized") }

expect val Inter: FontFamily