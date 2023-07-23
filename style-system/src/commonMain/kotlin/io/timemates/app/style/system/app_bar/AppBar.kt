package io.timemates.app.style.system.app_bar

 import androidx.compose.foundation.layout.Box
 import androidx.compose.foundation.layout.fillMaxWidth
 import androidx.compose.foundation.layout.padding
 import androidx.compose.foundation.layout.size
 import androidx.compose.material3.IconButton
 import androidx.compose.material3.Text
 import androidx.compose.runtime.Composable
 import androidx.compose.ui.Alignment
 import androidx.compose.ui.Modifier
 import androidx.compose.ui.unit.dp

@Composable
fun AppBar(
    title: @Composable () -> Unit,
    modifier: Modifier = Modifier,
    navigationIcon: @Composable () -> Unit = {},
    action: @Composable () -> Unit = {}
) {
    Box(
        modifier = modifier.fillMaxWidth(),
    ) {
        IconButton(
            onClick = {},
            modifier = Modifier
                .size(36.dp)
                .padding(8.dp)
        ) {
            navigationIcon()
        }
        AppBarText(title, Modifier
            .padding(52.dp)
            .align(Alignment.Center)
        )
        IconButton(
            onClick = {},
            modifier = Modifier
                .size(32.dp)
                .padding(8.dp)
        ) {
            action()
        }
    }
}

@Composable
fun AppBarText(title: @Composable () -> Unit, modifier: Modifier) {
    Text(
        text = title.toString(),
        modifier = modifier
    )
}