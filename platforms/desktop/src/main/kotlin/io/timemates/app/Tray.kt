package io.timemates.app

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.toAwtImage
import androidx.compose.ui.graphics.vector.VectorPainter
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.LayoutDirection
import java.awt.SystemTray
import java.awt.Toolkit
import java.awt.TrayIcon
import java.awt.event.MouseAdapter
import java.awt.event.MouseEvent

/**
 * Initializes the application's system tray with a custom tray icon and click handler.
 *
 * @param image The [VectorPainter] to be used as the tray icon.
 * @param onClick The click handler function that will be called when the tray icon is clicked.
 *               The function will receive the X and Y coordinates of the click position as parameters.
 */
@Composable
fun AppTray(
    image: VectorPainter,
    onClick: (x: Int, y: Int) -> Unit,
) {
    val systemTray = remember { SystemTray.getSystemTray() }
    val trayIconImage = image.toAwtImage(LocalDensity.current, LayoutDirection.Ltr)

    val calculateWindowX: (Int) -> Int = { trayCenterX ->
        val windowWidth = AppConstants.APP_WIDTH
        val screenWidth = Toolkit.getDefaultToolkit().screenSize.width
        val halfWindowWidth = windowWidth / 2
        when {
            trayCenterX - halfWindowWidth < 0 -> 0
            trayCenterX + halfWindowWidth > screenWidth -> screenWidth - windowWidth
            else -> trayCenterX - halfWindowWidth
        }
    }

    val calculateWindowY: (Int) -> Int = { trayCenterY ->
        val windowHeight = AppConstants.APP_HEIGHT
        val screenHeight = Toolkit.getDefaultToolkit().screenSize.height
        val halfWindowHeight = windowHeight / 2
        when {
            trayCenterY - halfWindowHeight < 0 -> 0
            trayCenterY + halfWindowHeight > screenHeight -> screenHeight - windowHeight
            else -> trayCenterY - halfWindowHeight
        }
    }

    DisposableEffect(systemTray, trayIconImage) {
        val trayIcon = TrayIcon(trayIconImage, "TimeMates").apply {
            addActionListener {}
        }

        trayIcon.addMouseListener(object : MouseAdapter() {
            override fun mouseClicked(e: MouseEvent?) {
                e ?: return
                if (e.button == MouseEvent.BUTTON1) {
                    onClick(calculateWindowX(e.x), calculateWindowY(e.y))
                }
            }
        })

        trayIcon.isImageAutoSize = true
        trayIcon.toolTip = "TimeMates"

        try {
            systemTray.add(trayIcon)
        } catch (e: Exception) {
            e.printStackTrace()
        }

        onDispose {
            systemTray.remove(trayIcon)
        }
    }
}