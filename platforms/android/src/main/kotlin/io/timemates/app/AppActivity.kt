package io.timemates.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.arkivanov.decompose.defaultComponentContext
import io.timemates.app.navigation.TimeMatesAppEntry

class AppActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val componentContent = defaultComponentContext()

        setContent {
            TimeMatesAppEntry()
        }
    }
}