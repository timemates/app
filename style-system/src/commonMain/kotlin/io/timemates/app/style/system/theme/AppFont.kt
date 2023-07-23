package io.timemates.app.style.system.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ProvidableCompositionLocal
import androidx.compose.runtime.Stable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.text.font.FontFamily
import dev.icerock.moko.resources.compose.asFont
import io.timemates.app.style.system.Resources

data class AppFonts internal constructor(
    val Inter: FontFamily,
)

@Stable
@Composable
fun appFonts(): AppFonts {
    return AppFonts(
        FontFamily(
            Resources.fonts.Inter.black.asFont()!!,
            Resources.fonts.Inter.bold.asFont()!!,
            Resources.fonts.Inter.extraBold.asFont()!!,
            Resources.fonts.Inter.extraLight.asFont()!!,
            Resources.fonts.Inter.light.asFont()!!,
            Resources.fonts.Inter.medium.asFont()!!,
            Resources.fonts.Inter.regular.asFont()!!,
            Resources.fonts.Inter.semiBold.asFont()!!,
        )
    )
}

val LocalAppFonts: ProvidableCompositionLocal<AppFonts> = staticCompositionLocalOf {
    error("LocalAppFonts is not provided")
}