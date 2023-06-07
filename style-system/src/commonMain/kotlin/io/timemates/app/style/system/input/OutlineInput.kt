package io.timemates.app.style.system.input

import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import io.timemates.app.style.system.AppTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OutlineInput(
    modifier: Modifier,
    value: String,
    onInput: (String) -> Unit,
    label: String,
    leadingIcon: (@Composable () -> Unit)? = null,
    trailingIcon: (@Composable () -> Unit)? = null,
    enabled: Boolean = true,
) {
    OutlinedTextField(
        modifier = modifier,
        value = value,
        onValueChange = onInput,
        enabled = enabled,
        label = { Text(label) },
        leadingIcon = leadingIcon,
        trailingIcon = trailingIcon,
        colors = TextFieldDefaults.outlinedTextFieldColors(
            textColor = AppTheme.colors.primary,
            cursorColor = AppTheme.colors.primary,
        ),
    )
}