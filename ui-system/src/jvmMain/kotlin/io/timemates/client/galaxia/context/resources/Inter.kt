package io.timemates.client.galaxia.context.resources

import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.platform.Font

actual val Inter: FontFamily = FontFamily(
    Font("font/InterExtraBold.ttf", FontWeight.ExtraBold, style = FontStyle.Normal),
    Font("font/InterBold.ttf", FontWeight.Bold, style = FontStyle.Normal),
    Font("font/InterMedium.ttf", FontWeight.Medium, style = FontStyle.Normal),
    Font("font/InterRegular.ttf", FontWeight.Normal, style = FontStyle.Normal),
    Font("font/InterSemiBold.ttf", FontWeight.SemiBold, style = FontStyle.Normal),
)