package io.timemates.desktop.systray

/**
 * Interface for providing the position of a tray icon.
 */
interface TrayIconPositionProvider {
    companion object {
        /**
         * Creates an instance of [TrayIconPositionProvider] based on the current system.
         *
         * @return An instance of [TrayIconPositionProvider] specific to the current system.
         * @throws IllegalStateException if the current system is not supported.
         */
        fun ofCurrentSystem(): TrayIconPositionProvider {
            return when (System.getProperty("os.name")) {
                "Linux" -> LinuxTrayIconPositionProvider()
                "Windows" -> WindowsTrayIconPositionProvider()
                else -> throw IllegalStateException("Tray icon position provider is not available for MacOS.")
            }
        }
    }

    /**
     * Retrieves the position of a tray icon based on the provided tray icon information.
     *
     * @param trayIconInfo The information of the tray icon.
     * @return The position of the tray icon, or null if the position cannot be determined.
     */
    fun getPosition(trayIconInfo: TrayIconInfo): Position?
}
