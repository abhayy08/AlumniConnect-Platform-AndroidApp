package com.abhay.alumniconnect.data.repository

import android.content.Context
import android.util.Log
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class SessionManagerImpl @Inject constructor(
    @ApplicationContext private val context: Context,
) : SessionManager {

    companion object {
        private const val FILE_NAME = "secure_token_prefs"
        private const val KEY_TOKEN = "user_token"
        private const val TAG = "SessionManager"
    }

    private val masterKey = MasterKey.Builder(context)
        .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
        .build()

    private val encryptedSharedPreferences = EncryptedSharedPreferences.create(
        context,
        FILE_NAME,
        masterKey,
        EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
        EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
    )

    override fun saveUserToken(token: String) {
        encryptedSharedPreferences.edit().putString(KEY_TOKEN, token).apply()
        Log.d(TAG, "saveUserToken: $token")
    }

    override fun getUserToken(): String? {
        return encryptedSharedPreferences.getString(KEY_TOKEN, null)
    }

    override fun clearUserToken() {
        encryptedSharedPreferences.edit().remove(KEY_TOKEN).apply()
    }
}

interface SessionManager {
    fun saveUserToken(token: String)
    fun getUserToken(): String?
    fun clearUserToken()
}
