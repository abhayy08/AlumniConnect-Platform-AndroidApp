package com.abhay.alumniconnect.utils

import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

fun formatDateForDisplay(isoDateString: String, showTime: Boolean = false): String {
    return try {
        val pattern = if (showTime) "MMM dd, yyyy 'at' hh:mm a" else "MMM dd, yyyy"
        val dateTime = ZonedDateTime.parse(isoDateString)
        val formatter = DateTimeFormatter.ofPattern(pattern)
        dateTime.format(formatter)
    } catch (e: Exception) {
        isoDateString
    }
}