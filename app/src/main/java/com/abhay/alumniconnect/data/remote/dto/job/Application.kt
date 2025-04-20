package com.abhay.alumniconnect.data.remote.dto.job

data class Application(
    val _applicationId: String,
    val _userId: String,
    val name: String,
    val email: String,
    val appliedAt: String,
    val resumeLink: String,
    val status: String
)
