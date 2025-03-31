package com.abhay.alumniconnect.data.repository

import android.util.Log
import org.json.JSONObject
import retrofit2.Response

fun extractErrorMessage(response: Response<*>, ERROR_TAG: String): String {
    return try {
        val errorBody = response.errorBody()?.string()
        Log.d(ERROR_TAG, "extractErrorMessage: $errorBody ${response.code()}")
        if (!errorBody.isNullOrEmpty()) {
            JSONObject(errorBody).getString("error")
        } else {
            "Unknown error occurred"
        }
    } catch (e: Exception) {
        e.localizedMessage ?: ""
    }
}