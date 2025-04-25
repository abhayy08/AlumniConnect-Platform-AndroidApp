package com.abhay.alumniconnect.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class Connection(
    val _id: String,
    val name: String,
    val email: String,
    val company: String?,
    val jobTitle: String?
)