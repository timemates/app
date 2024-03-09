package org.timemates.app.storage

import com.arkivanov.essenty.parcelable.ParcelableContainer
import java.io.ObjectInputStream
import java.nio.file.Path
import kotlin.io.path.createFile
import kotlin.io.path.deleteIfExists
import kotlin.io.path.exists
import kotlin.io.path.inputStream

fun FileParcelableContainer(path: Path): ParcelableContainer? {
    if (!path.exists()) path.createFile()

    return path.let { file ->
        try {
            ObjectInputStream(file.inputStream()).use(ObjectInputStream::readObject) as ParcelableContainer
        } catch (e: Exception) {
            null
        } finally {
            file.deleteIfExists()
        }
    }
}