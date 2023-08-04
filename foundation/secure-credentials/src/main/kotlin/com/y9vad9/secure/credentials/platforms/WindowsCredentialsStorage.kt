package com.y9vad9.secure.credentials.platforms

import com.sun.jna.Memory
import com.sun.jna.Native
import com.sun.jna.Pointer
import com.sun.jna.Structure
import com.sun.jna.platform.win32.Advapi32Util
import com.sun.jna.ptr.PointerByReference
import com.y9vad9.secure.credentials.CredentialsStorage
import java.nio.ByteBuffer

/**
 * Implementation of [CredentialsStorage] for Windows using Windows Credential Manager.
 * This class provides methods to interact with credentials on Windows.
 *
 * @param namespace name of the namespace where credentials will be stored. Usually, it's
 * just name of the application.
 */
class WindowsCredentialsStorage(
    private val namespace: String,
) : CredentialsStorage {

    /**
     * Constants for windows credentials manager.
     *
     * [Microsoft Documentation](https://learn.microsoft.com/en-us/windows/win32/api/wincred/ns-wincred-credentiala).
     */
    private companion object Constants {
        const val CRED_TYPE_GENERIC = 1
        const val CRED_PERSIST_LOCAL_MACHINE = 2
        const val CRED_PERSIST_ENTERPRISE = 3
    }

    private val credentialManager: WindowsCredentialManager = Native.load("Advapi32", WindowsCredentialManager::class.java)

    private fun getValue(key: String): ByteArray? {
        val targetName = "$namespace:${key}"
        val pCredential = PointerByReference()
        if (credentialManager.CredRead(targetName, CRED_TYPE_GENERIC, 0, pCredential)) {
            val credential = CREDENTIAL(pCredential.value)
            val credentialBlobSize = credential.credentialBlobSize
            val credentialBlob = ByteArray(credentialBlobSize)
            credential.credentialBlob.read(0, credentialBlob, 0, credentialBlobSize)
            return credentialBlob
        }

        return null
    }

    override fun getString(key: String): String? {
        return getValue(key)?.let(::String)
    }

    override fun getInt(key: String): Int? {
        return getValue(key)?.let(::byteArrayToInt)
    }

    override fun getLong(key: String): Long? {
        return getValue(key)?.let(::byteArrayToLong)
    }

    override fun getBoolean(key: String): Boolean? {
        return getValue(key)?.let(::byteArrayToBoolean)
    }

    override fun setString(key: String, value: String) {
        val targetName = "$namespace:${key}"
        val credentialBlob = value.toByteArray()
        val credentialBlobSize = credentialBlob.size

        val credential = CREDENTIAL()
        credential.type = CRED_TYPE_GENERIC
        credential.targetName = targetName
        credential.credentialBlobSize = credentialBlobSize
        credential.credentialBlob = Memory(credentialBlobSize.toLong()).apply { write(0, credentialBlob, 0, credentialBlobSize) }
        credential.persist = CRED_PERSIST_LOCAL_MACHINE

        credentialManager.CredWrite(credential, 0)
    }

    override fun setInt(key: String, value: Int) {
        val targetName = "$namespace:${key}"
        val credentialBlob = intToByteArray(value)
        val credentialBlobSize = credentialBlob.size

        val credential = CREDENTIAL()
        credential.type = CRED_TYPE_GENERIC
        credential.targetName = targetName
        credential.credentialBlobSize = credentialBlobSize
        credential.credentialBlob = Memory(credentialBlobSize.toLong()).apply { write(0, credentialBlob, 0, credentialBlobSize) }
        credential.persist = CRED_PERSIST_LOCAL_MACHINE

        credentialManager.CredWrite(credential, 0)
    }

    override fun setLong(key: String, value: Long) {
        val targetName = "$namespace:${key}"
        val credentialBlob = longToByteArray(value)
        val credentialBlobSize = credentialBlob.size

        val credential = CREDENTIAL()
        credential.type = CRED_TYPE_GENERIC
        credential.targetName = targetName
        credential.credentialBlobSize = credentialBlobSize
        credential.credentialBlob = Memory(credentialBlobSize.toLong()).apply { write(0, credentialBlob, 0, credentialBlobSize) }
        credential.persist = CRED_PERSIST_LOCAL_MACHINE

        credentialManager.CredWrite(credential, 0)
    }

    override fun setBoolean(key: String, value: Boolean) {
        val targetName = "$namespace:${key}"
        val credentialBlob = booleanToByteArray(value)
        val credentialBlobSize = credentialBlob.size

        val credential = CREDENTIAL()
        credential.type = CRED_TYPE_GENERIC
        credential.targetName = targetName
        credential.credentialBlobSize = credentialBlobSize
        credential.credentialBlob = Memory(credentialBlobSize.toLong()).apply { write(0, credentialBlob, 0, credentialBlobSize) }
        credential.persist = CRED_PERSIST_LOCAL_MACHINE

        credentialManager.CredWrite(credential, 0)
    }

    override fun clear(key: String) {
        val targetName = "$namespace:${key}"
        credentialManager.CredDelete(targetName, CRED_TYPE_GENERIC, 0)
    }
}

private fun byteArrayToInt(bytes: ByteArray): Int {
    if (bytes.size != 4) {
        throw Exception("wrong len")
    }
    bytes.reverse()
    return ByteBuffer.wrap(bytes).int
}

private fun byteArrayToLong(byteArray: ByteArray): Long {
    var result: Long = 0
    for (i in byteArray.indices) {
        result = result shl 8
        result = result or (byteArray[i].toLong() and 0xFF)
    }
    return result
}

private fun byteArrayToBoolean(byteArray: ByteArray): Boolean {
    for (byte in byteArray) {
        if (byte != 0.toByte()) {
            return true
        }
    }
    return false
}

private fun intToByteArray(value: Int): ByteArray {
    return ByteBuffer.allocate(4).putInt(value).array()
}

private fun longToByteArray(value: Long): ByteArray {
    return ByteBuffer.allocate(8).putLong(value).array()
}

private fun booleanToByteArray(value: Boolean): ByteArray {
    return ByteBuffer.allocate(4).putInt(if (value) 1 else 0).array()
}

private interface WindowsCredentialManager : com.sun.jna.Library {
    fun CredRead(target: String, type: Int, flags: Int, credential: PointerByReference): Boolean
    fun CredWrite(credential: Structure, flags: Int): Boolean
    fun CredDelete(target: String, type: Int, flags: Int): Boolean
}

private class CREDENTIAL : Structure {
    @JvmField
    var flags: Int = 0
    @JvmField
    var type: Int = 0
    @JvmField
    var targetName: String = ""
    @JvmField
    var comment: String = ""
    @JvmField
    var lastWritten: Long = 0
    @JvmField
    var credentialBlobSize: Int = 0
    @JvmField
    var credentialBlob: Pointer = Memory(0)
    @JvmField
    var persist: Int = 0
    @JvmField
    var attributeCount: Int = 0
    @JvmField
    var attributes: Pointer = Pointer.NULL
    @JvmField
    var targetAlias: String = ""
    @JvmField
    var userName: String = ""

    constructor() : super()
    constructor(pointer: Pointer) : super(pointer)
}