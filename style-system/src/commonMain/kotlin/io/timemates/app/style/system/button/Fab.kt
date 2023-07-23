package io.timemates.app.style.system.button

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.FloatingActionButtonElevation
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import io.timemates.app.style.system.theme.AppTheme

@Composable
fun FloatingActionButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    shape: Shape = MaterialTheme.shapes.small.copy(CornerSize(percent = 50)),
    containerColor: Color = MaterialTheme.colorScheme.primary,
    contentColor: Color = contentColorFor(MaterialTheme.colorScheme.onPrimary),
    elevation: FloatingActionButtonElevation = FloatingActionButtonDefaults.elevation(),
    content: @Composable () -> Unit
) {
    androidx.compose.material3.FloatingActionButton(
        onClick = onClick,
        modifier = modifier,
        interactionSource = interactionSource,
        shape = shape,
        containerColor = containerColor,
        contentColor = contentColor,
        elevation = elevation,
        content = content
    )
}

@Preview
@Composable
fun Test() {
    AppTheme {
        FloatingActionButton(
            onClick = {},
            content = {
                Icon(imageVector = Icons.Filled.Add, contentDescription = "Add")
            }
        )
    }
}