package com.abhay.alumniconnect.presentation.screens.search

data class AlumniFilters(
    val name: String = "",
    val graduationYear: Int? = null,
    val major: String = "",
    val company: String = "",
    val jobTitle: String = "",
    val skills: String = "",
    val university: String = "",
)