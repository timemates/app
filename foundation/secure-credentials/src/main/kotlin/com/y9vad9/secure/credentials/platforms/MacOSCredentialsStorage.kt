package com.y9vad9.secure.credentials.platforms

import com.sun.jna.Native
import com.sun.jna.Pointer
import com.sun.jna.ptr.PointerByReference
import com.y9vad9.secure.credentials.CredentialsStorage

interface SecurityFramework : com.sun.jna.Library {
    fun SecKeychainFindGenericPassword(
        keychainOrArray: Pointer?,
        serviceNameLength: UInt,
        serviceName: Pointer?,
        accountNameLength: UInt,
        accountName: Pointer?,
        passwordLength: UInt,
        passwordData: PointerByReference?,
        itemRef: PointerByReference?
    ): Int

    fun SecKeychainAddGenericPassword(
        keychain: Pointer?,
        serviceNameLength: UInt,
        serviceName: String?,
        accountNameLength: UInt,
        accountName: String?,
        passwordLength: UInt,
        passwordData: Pointer?,
        itemRef: PointerByReference?,
    ): Int

    fun SecKeychainItemFreeContent(
        attrList: Pointer?,
        data: Pointer?,
    ): Int
}

class MacOSCredentialsStorage(
    private val namespace: String,
) : CredentialsStorage {
    private val securityFramework: SecurityFramework = Native.load("Security", SecurityFramework::class.java)

    private fun readCredentialFromKeychain(key: String): String? {
        val serviceName = "$namespace:$key"
        val accountName = key
        val passwordData = PointerByReference()

        val serviceNamePointer = Native.toCharArray(serviceName)
        val accountNamePointer = Native.toCharArray(accountName)

        val status = securityFramework.SecKeychainFindGenericPassword(
            null,
            serviceName.length.toUInt(),
            serviceNamePointer,
            accountName.length.toUInt(),
            accountNamePointer,
            0U,
            null,
            passwordData
        )

        if (status == 0) {
            val passwordPtr = passwordData.value
            val password = passwordPtr?.getString(0)
            securityFramework.SecKeychainItemFreeContent(null, passwordPtr)
            return password
        }

        return null
    }



    private fun writeCredentialToKeychain(key: String, value: String) {
        val serviceName = "$namespace:$key"
        val accountName = key
        val passwordData = value.toByteArray()
        val passwordLength = passwordData.size.toUInt()

        val passwordPtr = Pointer(passwordData.size.toLong())
        passwordPtr.write(0, passwordData, 0, passwordData.size)

        val status = securityFramework.SecKeychainAddGenericPassword(
            null,
            serviceName.length.toUInt(),
            serviceName,
            accountName.length.toUInt(),
            accountName,
            passwordLength,
            passwordPtr,
            null
        )

        if (status != 0) {
            throw RuntimeException("Failed to store credential")
        }
    }

    override fun getString(key: String): String? {
        return readCredentialFromKeychain(key)
    }

    override fun setString(key: String, value: String) {
        writeCredentialToKeychain(key, value)
    }

    override fun getInt(key: String): Int? {
        return readCredentialFromKeychain(key)?.toIntOrNull()
    }

    override fun setInt(key: String, value: Int) {
        writeCredentialToKeychain(key, value.toString())
    }

    override fun getLong(key: String): Long? {
        return readCredentialFromKeychain(key)?.toLongOrNull()
    }

    override fun setLong(key: String, value: Long) {
        writeCredentialToKeychain(key, value.toString())
    }

    override fun getBoolean(key: String): Boolean? {
        return readCredentialFromKeychain(key)?.toBoolean()
    }

    override fun setBoolean(key: String, value: Boolean) {
        writeCredentialToKeychain(key, value.toString())
    }

    override fun clear(key: String) {
        // Implement clearing a stored credential
    }
}