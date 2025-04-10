package com.abhay.alumniconnect.domain.model

import com.abhay.alumniconnect.data.remote.dto.Connection
import com.abhay.alumniconnect.data.remote.dto.user.PrivacySettings
import com.abhay.alumniconnect.data.remote.dto.user.WorkExperience


data class User(
    val id: String,
    val name: String,
    val email: String,
    val bio: String,
    val company: String,
    val currentJob: String,
    val jobTitle: String,
    val degree: String,
    val fieldOfStudy: String,
    val graduationYear: Int,
    val major: String,
    val minor: String,
    val university: String,
    val linkedInProfile: String,
    val location: String,
    val isVerifiedUser: Boolean,
    val createdAt: String,
    val updatedAt: String,
    val achievements: List<String>,
    val interests: List<String>,
    val skills: List<String>,
    val connections: List<Connection>,
    val workExperience: List<WorkExperience>,
    val privacySettings: PrivacySettings
)