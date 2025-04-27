package com.abhay.alumniconnect.data.remote.dto.user

import com.abhay.alumniconnect.data.remote.dto.Connection
import kotlinx.serialization.Serializable

@Serializable
data class UserDetails(
    val __v: Int?,
    val _id: String,
    val achievements: List<String>,
    val bio: String?,
    val company: String?,
    val connections: List<Connection>,
    val createdAt: String,
    val currentJob: String?,
    val jobTitle: String?,
    val degree: String,
    val email: String,
    val fieldOfStudy: String?,
    val graduationYear: Int,
    val interests: List<String>,
    val isVerifiedUser: Boolean,
    val linkedInProfile: String?,
    val location: String?,
    val major: String,
    val minor: String?,
    val name: String,
    val privacySettings: PrivacySettings,
    val skills: List<String>,
    val university: String,
    val updatedAt: String,
    val workExperience: List<WorkExperience>,
    val connectionCount: Int = 0,
    val isConnected: Boolean?,
    val profileImage: String?
)