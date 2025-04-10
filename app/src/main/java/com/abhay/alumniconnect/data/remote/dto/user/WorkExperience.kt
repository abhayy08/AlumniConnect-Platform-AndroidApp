package com.abhay.alumniconnect.data.remote.dto.user

import kotlinx.serialization.Serializable

@Serializable
data class WorkExperience(
    val _id: String? = null,
    val company: String,
    val description: String?,
    val endDate: String?,
    val position: String,
    val startDate: String
)