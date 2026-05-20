package site.orionhub.music.security

import android.content.Context
import android.content.SharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey

object SecureStorage {
    private const val FILE_NAME = "orion_secure_prefs"

    @Volatile
    private var prefs: SharedPreferences? = null

    fun instance(context: Context): SharedPreferences =
        prefs ?: synchronized(this) {
            prefs ?: create(context).also { prefs = it }
        }

    private fun create(context: Context): SharedPreferences {
        val masterKey = MasterKey.Builder(context)
            .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
            .build()
        return EncryptedSharedPreferences.create(
            context,
            FILE_NAME,
            masterKey,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM,
        )
    }

    fun putString(context: Context, key: String, value: String) {
        instance(context).edit().putString(key, value).apply()
    }

    fun getString(context: Context, key: String, default: String? = null): String? =
        instance(context).getString(key, default)

    fun putBoolean(context: Context, key: String, value: Boolean) {
        instance(context).edit().putBoolean(key, value).apply()
    }

    fun getBoolean(context: Context, key: String, default: Boolean = false): Boolean =
        instance(context).getBoolean(key, default)

    fun remove(context: Context, key: String) {
        instance(context).edit().remove(key).apply()
    }
}
