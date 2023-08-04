@file:JvmName("CredentialsStorageUtils")
package com.y9vad9.secure.credentials

/**
 * Retrieves a string credential associated with the specified key or returns the default value if not found.
 */
fun CredentialsStorage.getStringOrDefault(key: String, defaultValue: () -> String): String {
    return getString(key) ?: defaultValue()
}

/**
 * Retrieves an integer credential associated with the specified key or returns the default value if not found.
 */
fun CredentialsStorage.getIntOrDefault(key: String, defaultValue: () -> Int): Int {
    return getInt(key) ?: defaultValue()
}

/**
 * Retrieves a long credential associated with the specified key or returns the default value if not found.
 */
fun CredentialsStorage.getLongOrDefault(key: String, defaultValue: () -> Long): Long {
    return getLong(key) ?: defaultValue()
}

/**
 * Retrieves a boolean credential associated with the specified key or returns the default value if not found.
 */
fun CredentialsStorage.getBooleanOrDefault(key: String, defaultValue: () -> Boolean): Boolean {
    return getBoolean(key) ?: defaultValue()
}

/**
 * Retrieves a string credential associated with the specified key. If not found, sets the default value and returns it.
 */
fun CredentialsStorage.getStringOrSet(key: String, defaultValue: () -> String): String {
    return getString(key) ?: defaultValue().also { setString(key, it) }
}

/**
 * Retrieves an integer credential associated with the specified key. If not found, sets the default value and returns it.
 */
fun CredentialsStorage.getIntOrSet(key: String, defaultValue: () -> Int): Int {
    return getInt(key) ?: defaultValue().also { setInt(key, it) }
}

/**
 * Retrieves a long credential associated with the specified key. If not found, sets the default value and returns it.
 */
fun CredentialsStorage.getLongOrSet(key: String, defaultValue: () -> Long): Long {
    return getLong(key) ?: defaultValue().also { setLong(key, it) }
}

/**
 * Retrieves a boolean credential associated with the specified key. If not found, sets the default value and returns it.
 */
fun CredentialsStorage.getBooleanOrSet(key: String, defaultValue: () -> Boolean): Boolean {
    return getBoolean(key) ?: defaultValue().also { setBoolean(key, it) }
}
