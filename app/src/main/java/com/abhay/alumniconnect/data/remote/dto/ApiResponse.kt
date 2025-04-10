package com.abhay.alumniconnect.data.remote.dto

import com.abhay.alumniconnect.data.remote.dto.user.UserDetails

data class ApiResponse(
    val message: String? = null,
    val user: UserDetails? = null
)