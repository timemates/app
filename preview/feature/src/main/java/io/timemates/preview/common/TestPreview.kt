package io.timemates.preview.common

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun Something() {
    Text("Hello World")
}

@Preview(showBackground = true)
@Composable
fun PreviewSomething() {
    Something()
}