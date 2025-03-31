package com.abhay.alumniconnect.data.repository

import android.content.Context
import android.util.Log
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import androidx.core.content.edit

class SessionManager @Inject constructor(@ApplicationContext private val context: Context) {

    companion object {
        private const val FILE_NAME = "secure_token_prefs"
        private const val KEY_TOKEN = "user_token"
        private const val TAG = "SessionManager"
    }

    private val masterKey = MasterKey.Builder(context)
        .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
        .build()

    private val encrypterSharedPreferences = EncryptedSharedPreferences.create(
        context,
        FILE_NAME,
        masterKey,
        EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
        EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
    )

    fun saveUserToken(token: String) {
        encrypterSharedPreferences.edit { putString(KEY_TOKEN, token) }
        Log.d(TAG, "saveUserToken: $token")
    }

    fun getUserToken(): String? {
        return encrypterSharedPreferences.getString(KEY_TOKEN, null)
    }

    fun clearUserToken() {
        encrypterSharedPreferences.edit { remove(KEY_TOKEN) }
    }

}