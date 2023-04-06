package io.timemates.client.galaxia.text

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign

/**
 * This is an immutable value class for representing text strings.
 * @property string The actual text string value.
 */
@Immutable
@JvmInline
value class Text(val string: String)

@Stable
internal fun Text.uppercase(): Text = text(string.uppercase())

@Stable
fun text(string: String): Text = io.timemates.client.galaxia.text.Text(string)

/**
 * This is a composable function for rendering a text field with the given text and style.
 * @param modifier A Modifier instance to apply to the text field.
 * @param text A Text instance to display in the text field.
 * @param textStyle A TextStyle instance to apply to the text in the text field.
 * @param textColor A Color instance representing the color of the text in the text field.
 */
@Composable
fun AtomText(
    modifier: Modifier = Modifier,
    text: Text,
    textStyle: TextStyle,
    textColor: Color = Color.Unspecified,
    textAlign: TextAlign = TextAlign.Start,
) {
    Text(
        modifier = modifier,
        text = text.string,
        style = textStyle,
        color = textColor,
        textAlign = textAlign,
    )
}