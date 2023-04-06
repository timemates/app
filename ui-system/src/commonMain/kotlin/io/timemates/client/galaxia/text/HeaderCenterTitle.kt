package io.timemates.client.galaxia.text

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import io.timemates.client.galaxia.context.GalaxiaContext
import io.timemates.client.galaxia.context.GalaxiaTheme

@Composable
fun HeaderCenterTitle(text: Text) {
    AtomText(
        modifier = Modifier.fillMaxWidth().background(GalaxiaContext.colors.bgHighest),
        text = text,
        textStyle = GalaxiaContext.typography.subtitle1,
        textColor = GalaxiaContext.colors.figureBase,
        textAlign = TextAlign.Center,
    )
}

@Preview
@Composable
private fun HeaderCenterTitlePreview(text: Text) {
    GalaxiaTheme(darkTheme = false) {
        HeaderCenterTitle(text)
    }
}