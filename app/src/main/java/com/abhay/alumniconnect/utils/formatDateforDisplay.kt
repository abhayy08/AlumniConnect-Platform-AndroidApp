package com.abhay.alumniconnect.utils

import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

fun formatDateForDisplay(isoDateString: String): String {
    return try {
        val dateTime = ZonedDateTime.parse(isoDateString)
        val formatter = DateTimeFormatter.ofPattern("MMM dd, yyyy")
        dateTime.format(formatter)
    } catch (e: Exception) {
        isoDateString
    }
}