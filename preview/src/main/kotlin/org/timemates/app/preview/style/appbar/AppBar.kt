package org.timemates.app.preview.style.appbar

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material.icons.rounded.ArrowBackIosNew
import androidx.compose.material.icons.rounded.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.timemates.app.style.system.appbar.AppBar
import org.timemates.app.style.system.theme.AppTheme

@Preview
@Composable
private fun AppBarPreview() {
    AppTheme {
        Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
            AppBar(
                title = "TimeMates",
            )

            AppBar(
                title = "TimeMates",
                navigationIcon = {
                    IconButton(
                        onClick = {},
                    ) {
                        Icon(imageVector = Icons.Rounded.ArrowBack, contentDescription = null)
                    }
                }
            )

            AppBar(
                title = "TimeMates",
                navigationIcon = {
                    IconButton(
                        onClick = {},
                    ) {
                        Icon(imageVector = Icons.Rounded.ArrowBackIosNew, contentDescription = null)
                    }
                },
                action = {
                    IconButton(
                        onClick = {},
                    ) {
                        Icon(imageVector = Icons.Rounded.Settings, contentDescription = null)
                    }
                }
            )
        }
    }
}