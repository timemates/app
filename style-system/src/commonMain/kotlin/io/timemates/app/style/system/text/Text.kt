package io.timemates.app.style.system.text

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle

@Composable
fun Text(
    modifier: Modifier,
    text: String,
    style: TextStyle,
) {
    androidx.compose.material3.Text(
        modifier = modifier,
        text = text,
        style = style,
    )
}