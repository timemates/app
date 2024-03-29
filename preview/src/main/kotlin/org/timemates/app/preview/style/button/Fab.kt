package org.timemates.app.preview.style.button

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import org.timemates.app.style.system.button.FloatingActionButton
import org.timemates.app.style.system.theme.AppTheme

@Preview
@Composable
private fun Test() {
    AppTheme(false) {
        FloatingActionButton(
            onClick = {},
            content = {
                Icon(imageVector = Icons.Filled.Add, contentDescription = "Add")
            },
        )
    }
}