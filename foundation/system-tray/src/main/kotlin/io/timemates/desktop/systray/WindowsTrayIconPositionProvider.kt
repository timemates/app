package io.timemates.desktop.systray

import com.sun.jna.Native
import com.sun.jna.Pointer
import com.sun.jna.platform.win32.User32
import com.sun.jna.platform.win32.WinDef
import com.sun.jna.platform.win32.WinUser

class WindowsTrayIconPositionProvider : TrayIconPositionProvider {
    private interface User32Library : User32 {
        override fun EnumWindows(lpEnumFunc: WinUser.WNDENUMPROC?, data: Pointer?): Boolean
        override fun GetWindowRect(hWnd: WinDef.HWND, rect: WinDef.RECT): Boolean
        override fun GetWindowText(hWnd: WinDef.HWND, lpString: CharArray, nMaxCount: Int): Int
    }

    private val user32: User32Library = Native.load("user32", User32Library::class.java)

    override fun getPosition(trayIconInfo: TrayIconInfo): Position? {
        val targetTitle = trayIconInfo.title
        val targetWindowHandles = mutableListOf<WinDef.HWND>()

        val enumWindowsCallback = WinUser.WNDENUMPROC { hWnd, _ ->
            val title = CharArray(512)
            user32.GetWindowText(hWnd, title, 512)
            if (title.joinToString("").contains(targetTitle)) {
                targetWindowHandles.add(hWnd)
            }
            true
        }

        user32.EnumWindows(enumWindowsCallback, null)

        if (targetWindowHandles.isEmpty()) {
            return null
        }

        val rect = WinDef.RECT()
        user32.GetWindowRect(targetWindowHandles.first(), rect)
        return Position(rect.left, rect.top)
    }
}
