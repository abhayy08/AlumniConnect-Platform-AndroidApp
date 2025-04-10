package com.abhay.alumniconnect.utils

fun String.capitalize(): String {
    return this.split("-").joinToString(" ") { it.replaceFirstChar { char -> char.uppercase() } }
}