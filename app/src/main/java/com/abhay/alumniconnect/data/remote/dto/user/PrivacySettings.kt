package com.abhay.alumniconnect.data.remote.dto.user

import kotlinx.serialization.Serializable

@Serializable
data class PrivacySettings(
    val showEmail: Boolean,
    val showLocation: Boolean,
    val showPhone: Boolean
)