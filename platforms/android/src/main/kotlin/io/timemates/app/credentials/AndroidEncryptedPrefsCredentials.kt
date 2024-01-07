package io.timemates.app.credentials

import android.content.Context
import android.content.SharedPreferences
import io.timemates.credentials.CredentialsStorage

/**
 * Android encrypted shared preferences implementation of credentials manager
 *
 * TODO: remove when https://github.com/vanniktech/gradle-maven-publish-plugin/issues/705 is fixed and we will be able to use library for android target.
 *
 * @param context Application context.
 *
 * @suppress Warning! In robolectric test encrypting is disabled
 * @see createSharedPreferences
 */
class AndroidEncryptedPrefsCredentials(
    context: Context,
    fileName: String = DEFAULT_ENCRYPTED_PREFS_FILE,
) : CredentialsStorage {
    private val prefs: SharedPreferences = createSharedPreferences(context, fileName)

    override fun getString(key: String): String? {
        return prefs.getString(key, null)
    }

    override fun getInt(key: String): Int? {
        return prefs.getString(key, null)?.toIntOrNull()
    }

    override fun getLong(key: String): Long? {
        return prefs.getString(key, null)?.toLongOrNull()
    }

    override fun getBoolean(key: String): Boolean? {
        return prefs.getString(key, null)?.toBooleanStrictOrNull()
    }

    override fun setString(key: String, value: String) {
        prefs.edit().apply { putString(key, value) }.apply()
    }

    override fun setInt(key: String, value: Int) {
        prefs.edit().apply { putString(key, value.toString()) }.apply()
    }

    override fun setLong(key: String, value: Long) {
        prefs.edit().apply { putString(key, value.toString()) }.apply()
    }

    override fun setBoolean(key: String, value: Boolean) {
        prefs.edit().apply { putString(key, value.toString()) }.apply()
    }
}