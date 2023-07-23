//package io.timemates.desktop.systray
//
//import com.sun.jna.Native
//import com.sun.jna.platform.mac.Carbon
//import com.sun.jna.platform.mac.CoreFoundation
//import com.sun.jna.platform.mac.CoreGraphics
//import com.sun.jna.ptr.PointerByReference
//import java.awt.Rectangle
//
//class MacOSSystemTrayIconPositionProvider : TrayIconPositionProvider {
//    override fun getPosition(trayIconInfo: TrayIconInfo): Position? {
//        val targetTitle = trayIconInfo.title
//        val trayIconBounds = getTrayIconBounds()
//
//        val screenBounds = getScreenBounds()
//        val snapshot = captureScreenSnapshot(screenBounds)
//
//        for (y in trayIconBounds.y until screenBounds.height) {
//            val pixelColor = getPixelColor(snapshot, trayIconBounds.x, y)
//            if (pixelColor.rgb != 0) {
//                return Position(trayIconBounds.x, y - 1)
//            }
//        }
//
//        return null
//    }
//
//    private fun getTrayIconBounds(): Rectangle {
//        val trayIconBounds = Rectangle()
//        val trayIcon = UIManager.getIcon("InternalFrame.icon")
//
//        trayIconBounds.width = trayIcon.iconWidth
//        trayIconBounds.height = trayIcon.iconHeight
//
//        return trayIconBounds
//    }
//
//    private fun getScreenBounds(): Rectangle {
//        val screenBounds = Rectangle()
//        val displayID = Carbon.INSTANCE.CGMainDisplayID()
//        val frame = Carbon.INSTANCE.CGDisplayBounds(displayID)
//
//        screenBounds.x = frame.x
//        screenBounds.y = frame.y
//        screenBounds.width = frame.width
//        screenBounds.height = frame.height
//
//        return screenBounds
//    }
//
//    private fun captureScreenSnapshot(screenBounds: Rectangle): CoreGraphics.CGImageRef {
//        val displayID = Carbon.INSTANCE.CGMainDisplayID()
//        val frame = CoreGraphics.CGRect(screenBounds.x.toDouble(), screenBounds.y.toDouble(), screenBounds.width.toDouble(), screenBounds.height.toDouble())
//        return CoreGraphics.INSTANCE.CGWindowListCreateImage(frame, CoreGraphics.kCGWindowListOptionOnScreenOnly, CoreGraphics.kCGNullWindowID, CoreGraphics.kCGWindowImageDefault)
//    }
//
//    private fun getPixelColor(snapshot: CoreGraphics.CGImageRef, x: Int, y: Int): CoreGraphics.CGColor {
//        val context = CoreGraphics.INSTANCE.CGBitmapContextCreate(null, 1, 1, 8, 0, CoreGraphics.INSTANCE.kCGColorSpaceGenericRGB, CoreGraphics.INSTANCE.kCGImageAlphaNoneSkipLast)
//        CoreGraphics.INSTANCE.CGContextSetBlendMode(context, CoreGraphics.INSTANCE.kCGBlendModeCopy)
//        CoreGraphics.INSTANCE.CGContextTranslateCTM(context, -x.toDouble(), -(snapshot.height - y).toDouble())
//        CoreGraphics.INSTANCE.CGContextDrawImage(context, CoreGraphics.CGRect(0.0, 0.0, snapshot.width.toDouble(), snapshot.height.toDouble()), snapshot)
//
//        val pixelData = CoreGraphics.INSTANCE.CGBitmapContextGetData(context)
//        val colorSpace = CoreGraphics.INSTANCE.CGBitmapContextGetColorSpace(context)
//        val components = ByteArray(4)
//        CoreGraphics.INSTANCE.CGDataProviderCopyData(CoreGraphics.INSTANCE.CGDataProviderCreateWithData(null, pixelData, 4, null)).use {
//            it.read(0, components, 0, components.size)
//        }
//
//        val color = CoreGraphics.INSTANCE.CGColorCreate(colorSpace, components)
//        CoreGraphics.INSTANCE.CGContextRelease(context)
//
//        return color
//    }
//}