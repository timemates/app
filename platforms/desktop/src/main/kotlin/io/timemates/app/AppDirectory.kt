package io.timemates.app

import java.nio.file.Path
import java.nio.file.attribute.PosixFilePermission
import java.nio.file.attribute.PosixFilePermissions
import java.util.Locale
import kotlin.io.path.Path
import kotlin.io.path.createDirectory
import kotlin.io.path.exists

/**
 * Lazily initializes the path to the application directory based on the operating system.
 *
 * If folder does not exist yet, it will be created with restricted access on Unix-systems.
 *
 * @return The [Path] to the application directory specific to the current operating system.
 * @throws UnsupportedOperationException if the operating system is not supported.
 */
val AppDirectory: Path by lazy {
    val os = System.getProperty("os.name").lowercase(Locale.ENGLISH)

    when {
        os.contains("win") -> Path(System.getenv("APPDATA") + "TimeMates")
        os.contains("mac") -> Path(System.getProperty("user.home"), "Library/Application Support/TimeMates")
        os.contains("nix") || os.contains("nux") || os.contains("aix") -> Path(System.getProperty("user.home") + ".TimeMates")
        else -> throw UnsupportedOperationException("Unsupported operating system")
    }.also { path ->
        if (path.exists()) return@also

        val permissionAttrs = PosixFilePermissions.asFileAttribute(
            setOf(
                PosixFilePermission.OWNER_READ,
                PosixFilePermission.OWNER_WRITE,
                PosixFilePermission.OWNER_EXECUTE,
            )
        )

        path.createDirectory(permissionAttrs)
    }
}