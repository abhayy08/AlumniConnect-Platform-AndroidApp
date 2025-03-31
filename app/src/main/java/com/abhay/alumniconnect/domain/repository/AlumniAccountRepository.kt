package com.abhay.alumniconnect.domain.repository

import com.abhay.alumniconnect.utils.Result

interface AlumniAccountRepository {

    suspend fun loginUser(email: String, password: String): Result<Unit>

    suspend fun registerUser(
        email: String,
        password: String,
        name: String,
        graduationYear: Int,
        currentJob: String?,
        university: String,
        degree: String,
        major: String
    ): Result<Unit>

    suspend fun logoutUser()

}