package com.abhay.alumniconnect.presentation.screens.search

data class JobFilters(
    val jobLocation: JobLocation? = null,
    val minExperience: Int? = null,
    val graduationYear: Int? = null,
    val jobType: JobType? = null,
    val branch: String = "",
    val degree: DegreeType? = null
)