package com.abhay.alumniconnect.data.remote.dto

import com.abhay.alumniconnect.data.remote.dto.user.UserDetails

data class ApiResponse(
    val message: String? = null,
    val user: UserDetails? = null
)

data class ImageResponse(
    val message: String? = null,
    val profileImage: String? = null,
    val error: String? = null
)