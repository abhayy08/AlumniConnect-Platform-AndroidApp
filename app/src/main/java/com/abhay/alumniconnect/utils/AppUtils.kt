package com.abhay.alumniconnect.utils

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.core.net.toUri
import com.abhay.alumniconnect.App
import java.io.File

object AppUtils {

    fun openLink(url: String) {
        val context = App.instance.applicationContext
        val fixedUrl = if (!url.startsWith("http://") && !url.startsWith("https://") && !url.startsWith("mailto:") &&
            !url.startsWith("tel:") && !url.startsWith("geo:")) {
            "https://$url"
        } else {
            url
        }

        val intent = Intent(Intent.ACTION_VIEW).apply {
            data = fixedUrl.toUri()
            flags = Intent.FLAG_ACTIVITY_NEW_TASK
        }

        try {
            context.startActivity(intent)
        } catch (e: ActivityNotFoundException) {
            Toast.makeText(context, "No app can handle this request.", Toast.LENGTH_SHORT).show()
        } catch (e: Exception) {
            Toast.makeText(context, "Error opening link: ${e.localizedMessage}", Toast.LENGTH_SHORT).show()
        }
    }

    fun uriToFile(uri: Uri): File? {
        val context = App.instance.applicationContext
        val inputStream = context.contentResolver.openInputStream(uri) ?: return null
        val tempFile = File.createTempFile("profile_image", ".jpg", context.cacheDir)
        tempFile.outputStream().use { output ->
            inputStream.copyTo(output)
        }
        return tempFile
    }


}


