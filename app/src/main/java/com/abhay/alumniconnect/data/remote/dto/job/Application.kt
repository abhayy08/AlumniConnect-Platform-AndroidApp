package com.abhay.alumniconnect.data.remote.dto.job

data class Application(
    val _id: String,
    val applicant: Any,
    val appliedAt: String,
    val resumeLink: String,
    val status: String
)
