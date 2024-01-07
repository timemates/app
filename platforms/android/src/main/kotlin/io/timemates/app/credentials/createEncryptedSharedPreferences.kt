package io.timemates.app.credentials

import android.content.Context
import android.content.SharedPreferences
import android.os.Build
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys

internal const val DEFAULT_ENCRYPTED_PREFS_FILE = "credentials_storage.txt"

/**
 * Create an encrypted SharedPreferences.
 *
 * @param context Application context.
 *
 * @suppress Warning! In robolectric test encrypting is disabled
 */
internal fun createSharedPreferences(context: Context, fileName: String = DEFAULT_ENCRYPTED_PREFS_FILE): SharedPreferences {
    return if(Build.FINGERPRINT.lowercase() == "robolectric") // For tests
        context.getSharedPreferences("test", Context.MODE_PRIVATE)
    else EncryptedSharedPreferences.create( // For app
        fileName,
        MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC),
        context,
        EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
        EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
    )
}