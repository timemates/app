package io.timemates.app.style.system.button

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.Indication
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp
import io.timemates.app.style.system.AppTheme
import io.timemates.app.style.system.progressbar.CircularProgressBar

@Composable
fun Button(
    modifier: Modifier = Modifier,
    primary: Boolean,
    content: @Composable BoxScope.() -> Unit,
    onClick: () -> Unit,
) {
    val interactionSource = remember { MutableInteractionSource() }

    Box(
        modifier = Modifier.fillMaxWidth()
            .then(modifier)
            .height(42.dp)
            .background(
                if (primary)
                    AppTheme.colors.primary
                else AppTheme.colors.secondary,
            )
            .clickable(
                interactionSource = interactionSource,
                indication = rememberRipple(),
                role = Role.Button,
                onClick = onClick,
            ),
    ) {
        content()
    }
}

@Composable
fun Button(
    modifier: Modifier = Modifier,
    primary: Boolean,
    text: String,
    onClick: () -> Unit,
    isLoading: Boolean = false,
) {
    val interactionSource = remember { MutableInteractionSource() }

    Button(
        modifier = modifier,
        primary = primary,
        content = {
            if (!isLoading) {
                Text(
                    modifier = Modifier.align(Alignment.Center),
                    text = text,
                    color = if (primary)
                        AppTheme.colors.onPrimary
                    else AppTheme.colors.onSecondary,
                    style = AppTheme.typography.labelHuge,
                )
            } else {
                CircularProgressBar(
                    color = if (primary) AppTheme.colors.onPrimary else AppTheme.colors.onSecondary,
                )
            }
        },
        onClick = onClick
    )
}

@Preview
@Composable
private fun ButtonPreview() {
    val configuration = listOf(
        // is dark mode to is primary
        false to true,
        false to false,
        true to true,
        true to false,
    )

    Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
        configuration.forEach { (isDarkMode, isPrimary) ->
            AppTheme(isDarkMode) {
                Button(
                    primary = isPrimary,
                    text = "Submit",
                    onClick = {}
                )
            }
        }
    }
}