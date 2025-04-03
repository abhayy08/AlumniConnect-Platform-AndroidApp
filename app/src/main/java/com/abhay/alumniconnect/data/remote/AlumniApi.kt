package com.abhay.alumniconnect.data.remote

import com.abhay.alumniconnect.data.remote.dto.ApiResponse
import com.abhay.alumniconnect.data.remote.dto.Connection
import com.abhay.alumniconnect.data.remote.dto.Job
import com.abhay.alumniconnect.data.remote.dto.UserDetails
import com.abhay.alumniconnect.data.remote.dto.UserToken
import com.abhay.alumniconnect.data.remote.dto.WorkExperience
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path


interface AlumniApi {

    @POST("auth/login")
    suspend fun login(@Body requestBody: Map<String, String>): Response<UserToken>

    @POST("auth/register")
    suspend fun register(@Body requestBody: Map<String, String>): Response<UserToken>

    @GET("profile/detailed")
    suspend fun getCurrentUser(): Response<UserDetails>

    @GET("profile/detailed/{userId}")
    suspend fun getUserById(@Path("userId") userId: String): Response<UserDetails>

    @GET("profile/connections")
    suspend fun getUserConnections(): Response<List<Connection>>

    @POST("profile/connect/{connectionId}")
    suspend fun connectUser(@Path("connectionId") targetUserId: String): Response<ApiResponse>

    @DELETE("profile/connect/{targetUserId}")
    suspend fun removeConnection(@Path("targetUserId") targetUserId: String): Response<ApiResponse>

    @PUT("profile/me") // updates bio, skills, interests, achievements
    suspend fun updateProfile(@Body requestBody: UserDetails): Response<ApiResponse>

    @PUT("profile/work-experience/{id}")
        suspend fun updateWorkExperienceById(@Path("id") experienceId: String, @Body requestBody: WorkExperience): Response<ApiResponse>

    @POST("profile/work-experience")
    suspend fun addWorkExperience(@Body requestBody: WorkExperience): Response<ApiResponse>

    @DELETE("profile/work-experience/{id}")
    suspend fun deleteWorkExperienceById(@Path("id") experienceId: String): Response<ApiResponse>

    @GET("jobs")
    suspend fun getJobs(): Response<List<Job>>

    @GET("jobs/{id}")
    suspend fun getJobById(@Path("id") jobId: String): Response<Job>

    @POST("jobs/{id}/apply")
    suspend fun applyForJob(@Path("id") jobId: String, @Body requestBody: Map<String, String>): Response<ApiResponse>

}