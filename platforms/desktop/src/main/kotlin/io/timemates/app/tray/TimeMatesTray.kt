package io.timemates.app.tray

import java.awt.Image
import java.awt.PopupMenu
import java.awt.SystemTray
import java.awt.Toolkit
import java.awt.TrayIcon
import java.awt.event.MouseAdapter
import java.awt.event.MouseEvent
import javax.swing.SwingUtilities

internal object TimeMatesTray {
    fun initialize(image: Image, onClick: (x: Int, y: Int) -> Unit) {
        createTrayIcon(image, onClick)
    }

    private fun createTrayIcon(
        image: Image,
        onClick: (x: Int, y: Int) -> Unit,
    ): TrayIcon {
        val systemTray = SystemTray.getSystemTray()

        val trayIcon = TrayIcon(image, "Open TimeMates")

        trayIcon.popupMenu = PopupMenu("TimeMates")

        trayIcon.addMouseListener(createTrayMouseListener { x, y ->
            onClick(x, y)
        })

        trayIcon.addActionListener {}

        systemTray.add(trayIcon)

        return trayIcon
    }

    private fun createTrayMouseListener(
        onClick: (x: Int, y: Int) -> Unit,
    ): MouseAdapter {
        return object : MouseAdapter() {
            override fun mouseClicked(e: MouseEvent?) {
                e ?: return

                if (SwingUtilities.isLeftMouseButton(e)) {
                    println("${e.x} & ${e.y}")
                    onClick(calculateWindowX(e.x), calculateWindowY(e.y))
                }
            }
        }
    }

    private fun calculateWindowX(
        trayCenterX: Int,
        windowWidth: Int = 350,
        screenWidth: Int = Toolkit.getDefaultToolkit().screenSize.width,
    ): Int {
        val halfWindowWidth = windowWidth / 2

        return when {
            trayCenterX - halfWindowWidth < 0 -> 0 // Tray icon is closer to the left edge
            trayCenterX + halfWindowWidth > screenWidth -> screenWidth - windowWidth // Tray icon is closer to the right edge
            else -> trayCenterX - halfWindowWidth // Tray icon is in the middle of the screen
        }
    }

    private fun calculateWindowY(
        trayCenterY: Int,
        windowHeight: Int = 650,
        screenHeight: Int = Toolkit.getDefaultToolkit().screenSize.height,
    ): Int {
        val halfWindowHeight = windowHeight / 2

        return when {
            trayCenterY - halfWindowHeight < 0 -> 0 // Tray icon is closer to the top edge
            trayCenterY + halfWindowHeight > screenHeight -> screenHeight - windowHeight // Tray icon is closer to the bottom edge
            else -> trayCenterY - halfWindowHeight // Tray icon is in the middle of the screen
        }
    }
}