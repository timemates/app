package io.timemates.app.style.system.input

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import io.timemates.app.style.system.AppTheme
import io.timemates.app.style.system.text.Text

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
    isError: Boolean = false,
    supportText: String? = null,
    isEnabled: Boolean = true,
) {
    OutlinedTextField(
        modifier = modifier,
        value = value,
        onValueChange = onInput,
        enabled = enabled,
        label = { Text(text = label, style = AppTheme.typography.labelSmall) },
        leadingIcon = leadingIcon,
        trailingIcon = trailingIcon,
        colors = TextFieldDefaults.outlinedTextFieldColors(
            textColor = AppTheme.colors.primary,
            cursorColor = AppTheme.colors.primary,
        ),
        isError = isError,
        supportingText = if (supportText != null) @Composable {
            Text(text = supportText, style = AppTheme.typography.labelSmall)
        } else null,
        isEnabled = isEnabled,
    )
}