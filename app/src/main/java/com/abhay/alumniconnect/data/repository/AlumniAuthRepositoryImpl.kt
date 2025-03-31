package com.abhay.alumniconnect.data.repository

import com.abhay.alumniconnect.data.remote.AlumniApi
import com.abhay.alumniconnect.domain.repository.AlumniAccountRepository
import com.abhay.alumniconnect.utils.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class AlumniAuthRepositoryImpl @Inject constructor(
    private val api: AlumniApi,
    private val sessionManager: SessionManager
) : AlumniAccountRepository {

    override suspend fun loginUser(email: String, password: String): Result<Unit> =
        withContext(Dispatchers.IO) {
            try {
                val requestBody = mapOf(
                    "email" to email,
                    "password" to password
                )

                val response = api.login(requestBody = requestBody)
                if (!response.isSuccessful) {
                    return@withContext Result.Error(extractErrorMessage(response, ERROR_TAG))
                }

                val userToken = response.body()
                    ?: return@withContext Result.Error("Invalid response from server")

                sessionManager.saveUserToken(userToken.token)

                Result.Success(Unit)
            } catch (e: Exception) {
                Result.Error(e.message ?: "Something went wrong: ${e.localizedMessage}")
            }
        }


    override suspend fun registerUser(
        email: String,
        password: String,
        name: String,
        graduationYear: Int,
        currentJob: String?,
        university: String,
        degree: String,
        major: String
    ): Result<Unit> = withContext(Dispatchers.IO) {

        try {
            val requestBody = mutableMapOf(
                "email" to email,
                "password" to password,
                "name" to name,
                "graduationYear" to graduationYear.toString(),
                "major" to major,
                "degree" to degree,
                "university" to university
            ).apply {
                currentJob?.let { put("currentJob", it) }
            }

            val response = api.register(requestBody)
            if (!response.isSuccessful) return@withContext Result.Error(
                extractErrorMessage(
                    response,
                    ERROR_TAG
                )
            )

            val userToken =
                response.body() ?: return@withContext Result.Error("Invalid response from server")

            sessionManager.saveUserToken(userToken.token)
            Result.Success(Unit)
        } catch (e: Exception) {
            Result.Error(e.message ?: "Something went wrong")
        }
    }

    override suspend fun logoutUser() {
        sessionManager.clearUserToken()
    }

    companion object {
        private const val ERROR_TAG = "AlumniAuthRepository"
    }
}


