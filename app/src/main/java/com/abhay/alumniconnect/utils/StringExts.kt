package com.abhay.alumniconnect.utils

fun String.titlecase(): String {
    return this.replaceFirstChar { it.titlecase() }
}