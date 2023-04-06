package io.timemates.client.galaxia.text

import androidx.compose.material.OutlinedTextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import io.timemates.client.galaxia.image.Image

@Composable
fun EditText(
    modifier: Modifier = Modifier,
    trailingIcon: Image.Icon? = null,
    text: Text,
    placeholder: Text,
    onValueChange: (Text) -> Unit,
    textStyle: TextStyle = TextStyle.Default,
    textColor: Color = Color.Unspecified,
    textAlign: TextAlign = TextAlign.Start,
    enabled: Boolean = true,
) {
    OutlinedTextField(
        modifier = modifier,
        value = text.string,
        onValueChange = { onValueChange(text(it)) },
        enabled = enabled,
        label = {
            AtomText(
                text = text,
                textStyle = textStyle,
                textColor = textColor,
                textAlign = textAlign
            )
        },
    )
}