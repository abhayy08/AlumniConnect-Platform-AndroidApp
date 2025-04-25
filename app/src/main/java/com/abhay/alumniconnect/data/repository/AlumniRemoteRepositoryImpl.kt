package com.abhay.alumniconnect.data.repository

import android.util.Log
import com.abhay.alumniconnect.data.remote.AlumniApi
import com.abhay.alumniconnect.data.remote.dto.Connection
import com.abhay.alumniconnect.data.remote.dto.user.WorkExperience
import com.abhay.alumniconnect.domain.mapper.toUser
import com.abhay.alumniconnect.domain.mapper.toUserDetails
import com.abhay.alumniconnect.domain.model.User
import com.abhay.alumniconnect.domain.repository.AlumniRemoteRepository
import com.abhay.alumniconnect.utils.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.withContext
import javax.inject.Inject

class AlumniRemoteRepositoryImpl @Inject constructor(
    private val api: AlumniApi
) : AlumniRemoteRepository {

    private val _currentUserFlow = MutableStateFlow<Result<User>?>(null)
    override val currentUserFlow: StateFlow<Result<User>?> = _currentUserFlow.asStateFlow()

    // Initialize the flow with data on first access
    private var initialized = false
    private suspend fun initializeIfNeeded() {
        if (!initialized) {
            initialized = true
            val result = getCurrentUser()
            // Only update if the flow is still null to avoid overwriting changes
            _currentUserFlow.compareAndSet(null, result)
        }
    }

    // Function to get the flow with auto-initialization
    override suspend fun getOrLoadCurrentUserFlow(): StateFlow<Result<User>?> {
        initializeIfNeeded()
        return currentUserFlow
    }

    override suspend fun getCurrentUser(): Result<User> = withContext(Dispatchers.IO) {
        try {
            val response = api.getCurrentUser()
            if (!response.isSuccessful) return@withContext Result.Error(
                extractErrorMessage(
                    response, ERROR_TAG
                )
            )

            response.body()?.let { userDetails ->
                Log.d(ERROR_TAG, "getCurrentUser: $userDetails")
                _currentUserFlow.update { Result.Success(userDetails.toUser()) }
                return@withContext Result.Success(userDetails.toUser())
            }

            Result.Error("Invalid response from server")
        } catch (e: Exception) {
            Result.Error(e.message ?: "Something went wrong")
        }
    }

    override suspend fun getUserById(id: String): Result<User> =
        withContext(Dispatchers.IO) {
            try {
                val response = api.getUserById(id)
                Log.d(ERROR_TAG, "getUserById: $response")
                if (!response.isSuccessful) return@withContext Result.Error(
                    extractErrorMessage(
                        response, ERROR_TAG
                    )
                )

                response.body()?.let {
                    return@withContext Result.Success(it.toUser())
                }

                Result.Error("Invalid response from server")
            } catch (e: Exception) {
                Result.Error(e.message ?: "Something went wrong")
            }
        }

    override suspend fun getUserConnections(): Result<List<Connection>> =
        withContext(Dispatchers.IO) {
            try {
                val response = api.getUserConnections()
                if (!response.isSuccessful) return@withContext Result.Error(
                    extractErrorMessage(
                        response, ERROR_TAG
                    )
                )

                response.body()?.let {
                    return@withContext Result.Success(it)
                }

                Result.Error("Invalid response from server")
            } catch (e: Exception) {
                Result.Error(e.message ?: "Something went wrong")
            }
        }

    override suspend fun getUserConnectionsByUserId(userId: String): Result<List<Connection>> =
        withContext(Dispatchers.IO) {
            try {
                val response = api.getUserConnectionsByUserId(userId = userId)
                if (!response.isSuccessful) return@withContext Result.Error(
                    extractErrorMessage(
                        response, ERROR_TAG
                    )
                )
                response.body()?.let {
                    return@withContext Result.Success(it)
                }
                Result.Error("Something went wrong, Try again later!")
            }catch(e: java.lang.Exception) {
                Result.Error(e.message ?: "Something went wrong, Try again later!")
            }
        }

    override suspend fun updateUser(user: User): Result<String> =
        withContext(Dispatchers.IO) {
            try {
                val requestBody = user.toUserDetails()
                val response = api.updateProfile(requestBody)

                if (!response.isSuccessful) return@withContext Result.Error(
                    extractErrorMessage(
                        response, ERROR_TAG
                    )
                )

                response.body()?.let { data ->
                    _currentUserFlow.update { Result.Success(data.user!!.toUser()) }
                    return@withContext Result.Success(data = data.message)
                }

                Result.Error("Invalid response from server")
            } catch (e: Exception) {
                Result.Error(e.message ?: "Something went wrong")
            }
        }

    override suspend fun updateWorkExperience(
        experienceId: String,
        experience: WorkExperience
    ): Result<String> = withContext(Dispatchers.IO) {
        try {
            val response = api.updateWorkExperienceById(experienceId, experience)
            if (!response.isSuccessful) return@withContext Result.Error(
                extractErrorMessage(
                    response, ERROR_TAG
                )
            )
            response.body()?.let { data ->
                _currentUserFlow.update { Result.Success(data.user!!.toUser()) }
                return@withContext Result.Success(data = data.message)
            }
            Result.Error("Invalid response from server")
        } catch (e: Exception) {
            Result.Error(e.message ?: "Something went wrong")
        }
    }

    override suspend fun addWorkExperience(
        experience: WorkExperience
    ): Result<String> = withContext(Dispatchers.IO) {
        try {
            val response = api.addWorkExperience(experience)
            if (!response.isSuccessful) return@withContext Result.Error(
                extractErrorMessage(
                    response, ERROR_TAG
                )
            )
            response.body()?.let { data ->
                Log.d(ERROR_TAG, "addWorkExperience: ${data}")
                _currentUserFlow.update { Result.Success(data.user!!.toUser()) }
                return@withContext Result.Success(data = data.message)
            }
            Result.Error("Invalid response from server")
        } catch (e: Exception) {
            Result.Error(e.message ?: "Something went wrong")
        }
    }

    override suspend fun deleteWorkExperience(experienceId: String): Result<String> =
        withContext(Dispatchers.IO) {
            try {
                val response = api.deleteWorkExperienceById(experienceId)
                if (!response.isSuccessful) return@withContext Result.Error(
                    extractErrorMessage(
                        response, ERROR_TAG
                    )
                )
                response.body()?.let { data ->
                    _currentUserFlow.update { Result.Success(data.user!!.toUser()) }
                    return@withContext Result.Success(data = data.message)
                }
                Result.Error("Invalid response from server")
            } catch (e: Exception) {
                Result.Error(e.message ?: "Something went wrong")
            }
        }

    override suspend fun searchAlumni(query: Map<String, String?>): Result<List<User>> =
        withContext(Dispatchers.IO) {
            try {
                val response = api.searchAlumni(query)
                if (!response.isSuccessful) return@withContext Result.Error(
                    extractErrorMessage(
                        response, ERROR_TAG
                    )
                )
                response.body()?.let { data ->
                    val mappedData = data.map { it.toUser() }
                    return@withContext Result.Success(mappedData)
                }
                Result.Error("Invalid response from server")
            } catch (e: java.lang.Exception) {
                Result.Error(e.message ?: "Something went wrong")
            }
        }


    override suspend fun connectUser(targetUserId: String): Result<String> {
        TODO("Not yet implemented")
    }

    override suspend fun removeConnection(targetUserId: String): Result<String> {
        TODO("Not yet implemented")
    }

    companion object {
        private const val ERROR_TAG = "AlumniRemoteRepository"
    }
}