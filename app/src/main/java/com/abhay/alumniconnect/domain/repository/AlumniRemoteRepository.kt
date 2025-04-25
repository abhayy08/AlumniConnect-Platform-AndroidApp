package com.abhay.alumniconnect.domain.repository

import com.abhay.alumniconnect.data.remote.dto.Connection
import com.abhay.alumniconnect.data.remote.dto.user.WorkExperience
import com.abhay.alumniconnect.domain.model.User
import com.abhay.alumniconnect.utils.Result
import kotlinx.coroutines.flow.StateFlow
import retrofit2.http.Query

interface AlumniRemoteRepository {

    val currentUserFlow: StateFlow<Result<User>?>

    suspend fun getOrLoadCurrentUserFlow(): StateFlow<Result<User>?>

    suspend fun getCurrentUser(): Result<User>

    suspend fun getUserById(id: String): Result<User>

    suspend fun connectUser(targetUserId: String): Result<String>

    suspend fun removeConnection(targetUserId: String): Result<String>

    suspend fun getUserConnections(): Result<List<Connection>>

    suspend fun getUserConnectionsByUserId(userId: String): Result<List<Connection>>

    suspend fun updateUser(user: User): Result<String>

    suspend fun updateWorkExperience(experienceId: String, experience: WorkExperience): Result<String>

    suspend fun addWorkExperience(experience: WorkExperience): Result<String>

    suspend fun deleteWorkExperience(experienceId: String): Result<String>

    suspend fun searchAlumni(query: Map<String, String?>): Result<List<User>>

}