package io.timemates.app.style.system.text

import androidx.compose.material.LocalTextStyle
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import io.timemates.app.style.system.resources.LocalTypography

@Composable
fun Text(
    modifier: Modifier = Modifier,
    text: String,
    style: TextStyle = LocalTextStyle.current,
) {
    androidx.compose.material3.Text(
        modifier = modifier,
        text = text,
        style = style,
    )
}