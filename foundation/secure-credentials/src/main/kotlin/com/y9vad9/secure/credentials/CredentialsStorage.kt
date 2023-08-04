package com.y9vad9.secure.credentials

import com.y9vad9.secure.credentials.platforms.MacOSCredentialsStorage
import com.y9vad9.secure.credentials.platforms.WindowsCredentialsStorage
import java.util.Locale

/**
 * Interface for storing and retrieving credentials.
 */
interface CredentialsStorage {
    companion object {
        /**
         * Creates an instance of `CredentialsStorage` based on the current platform.
         *
         * @param namespace The namespace for the credentials storage.
         * @return An instance of `CredentialsStorage` corresponding to the current platform, or null if the platform is not supported.
         */
        @JvmStatic
        fun ofCurrentPlatform(namespace: String): CredentialsStorage? {
            val os = System.getProperty("os.name").lowercase(Locale.ENGLISH)

            return when {
                os.contains("win") -> WindowsCredentialsStorage(namespace)
                os.contains("mac") -> MacOSCredentialsStorage(namespace)
                else -> null
            }
        }
    }
    /**
     * Retrieves a string credential associated with the specified key.
     */
    fun getString(key: String): String?

    /**
     * Retrieves an integer credential associated with the specified key.
     */
    fun getInt(key: String): Int?

    /**
     * Retrieves a long credential associated with the specified key.
     */
    fun getLong(key: String): Long?

    /**
     * Retrieves a boolean credential associated with the specified key.
     */
    fun getBoolean(key: String): Boolean?

    /**
     * Stores a string credential with the specified key and value.
     */
    fun setString(key: String, value: String)

    /**
     * Stores an integer credential with the specified key and value.
     */
    fun setInt(key: String, value: Int)

    /**
     * Stores a long credential with the specified key and value.
     */
    fun setLong(key: String, value: Long)

    /**
     * Stores a boolean credential with the specified key and value.
     */
    fun setBoolean(key: String, value: Boolean)

    /**
     * Clears the stored credential associated with the specified key.
     */
    fun clear(key: String)
}

