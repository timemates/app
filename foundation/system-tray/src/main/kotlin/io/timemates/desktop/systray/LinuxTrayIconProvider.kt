package io.timemates.desktop.systray

import com.sun.jna.Library
import com.sun.jna.Native
import com.sun.jna.Pointer
import com.sun.jna.ptr.IntByReference
import com.sun.jna.ptr.PointerByReference

interface AppIndicatorLibrary : Library {
    fun app_indicator_new(
        id: String,
        icon: String,
        category: Int
    ): Pointer

    fun app_indicator_get_icon_geometry(
        self: Pointer,
        x: Pointer,
        y: Pointer,
        width: Pointer,
        height: Pointer
    )

    fun app_indicator_set_status(self: Pointer, status: Int)
    fun app_indicator_set_menu(self: Pointer, menu: Pointer)
    fun app_indicator_get_id(id: Int): Pointer
    fun app_indicator_set_icon_full(
        self: Pointer,
        icon: String,
        label: String
    )

    companion object {
        val INSTANCE: AppIndicatorLibrary = Native.load("appindicator3", AppIndicatorLibrary::class.java)
    }
}

/**
 * Provides the position of a tray icon on Linux systems using the AppIndicator library.
 */
class LinuxTrayIconPositionProvider : TrayIconPositionProvider {
    /**
     * Retrieves the position of a tray icon based on the provided [trayIconInfo].
     *
     * @param trayIconInfo The information of the tray icon.
     * @return The position of the tray icon, or null if the position cannot be determined.
     */
    override fun getPosition(trayIconInfo: TrayIconInfo): Position? {
        // Get the target title to match
        val targetTitle = trayIconInfo.title

        // Collect the indicator IDs
        val indicatorIds = mutableListOf<String>()
        val indicatorId = PointerByReference()
        var i = 0
        while (true) {
            indicatorId.value = AppIndicatorLibrary.INSTANCE.app_indicator_get_id(i)
            if (indicatorId.value == null) {
                break
            }
            indicatorIds.add(indicatorId.value.getString(0))
            i++
        }

        // Find the matching indicator based on title
        for (indicatorIdStr in indicatorIds) {
            val indicator = AppIndicatorLibrary.INSTANCE.app_indicator_new(indicatorIdStr, "", 0)
            val title = indicatorIdStr.substringAfterLast(":") // Extract the title from the indicator ID

            if (title == targetTitle) {
                // Set the status to ACTIVE to make the indicator visible
                AppIndicatorLibrary.INSTANCE.app_indicator_set_status(indicator, 0)

                // Get the geometry (position and size) of the indicator
                val x = IntByReference()
                val y = IntByReference()
                val width = IntByReference()
                val height = IntByReference()
                AppIndicatorLibrary.INSTANCE.app_indicator_get_icon_geometry(indicator, x.pointer, y.pointer, width.pointer, height.pointer)

                val iconX = x.value
                val iconY = y.value

                return Position(iconX, iconY)
            }
        }

        return null
    }
}
