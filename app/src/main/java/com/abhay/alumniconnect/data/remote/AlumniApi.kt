package com.abhay.alumniconnect.data.remote

import com.abhay.alumniconnect.data.remote.dto.ApiResponse
import com.abhay.alumniconnect.data.remote.dto.Connection
import com.abhay.alumniconnect.data.remote.dto.CreatePostResponse
import com.abhay.alumniconnect.data.remote.dto.ImageResponse
import com.abhay.alumniconnect.data.remote.dto.job.Application
import com.abhay.alumniconnect.data.remote.dto.job.Job
import com.abhay.alumniconnect.data.remote.dto.post.Comment
import com.abhay.alumniconnect.data.remote.dto.post.Post
import com.abhay.alumniconnect.data.remote.dto.user.UserDetails
import com.abhay.alumniconnect.data.remote.dto.user.UserToken
import com.abhay.alumniconnect.data.remote.dto.user.WorkExperience
import com.abhay.alumniconnect.domain.model.User
import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Part
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.QueryMap


interface AlumniApi {

    // AUTH
    @POST("auth/login")
    suspend fun login(@Body requestBody: Map<String, String>): Response<UserToken>

    @POST("auth/register")
    suspend fun register(@Body requestBody: Map<String, String>): Response<UserToken>

    // PROFILE/USER
    @GET("profile/detailed")
    suspend fun getCurrentUser(): Response<UserDetails>

    @GET("profile/detailed/{userId}")
    suspend fun getUserById(@Path("userId") userId: String): Response<UserDetails>

    @GET("profile/connections")
    suspend fun getUserConnections(): Response<List<Connection>>

    @GET("profile/connections/{id}")
    suspend fun getUserConnectionsByUserId(@Path("id") userId: String): Response<List<Connection>>

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

    @GET("profile/search")
    suspend fun searchAlumni(
        @QueryMap filters: Map<String, String?>
    ): Response<List<UserDetails>>

    @Multipart
    @POST("profile/me/profile-image")
    suspend fun uploadProfileImage(
        @Part image: MultipartBody.Part
    ): Response<ImageResponse>

    // JOBS

    @GET("jobs")
    suspend fun getJobs(): Response<List<Job>>

    @GET("jobs/me")
    suspend fun getJobsByCurrentUser(): Response<List<Job>>

    @GET("jobs/{id}")
    suspend fun getJobById(@Path("id") jobId: String): Response<Job>

    @POST("jobs/{id}/apply")
    suspend fun applyForJob(@Path("id") jobId: String, @Body requestBody: Map<String, String>): Response<ApiResponse>

    @DELETE("jobs/{jobId}")
    suspend fun deleteJobById(@Path("jobId") jobId: String): Response<ApiResponse>

    @GET("jobs/applied")
    suspend fun getAppliedJobs(): Response<List<Job>>

    @GET("jobs/offered")
    suspend fun getOfferedJobs(): Response<List<Job>>

    @GET("jobs/user/{userId}")
    suspend fun getJobsByUserId(@Path("userId") userId: String): Response<List<Job>>

    @POST("jobs")
    suspend fun createJob(@Body requestBody: Job): Response<ApiResponse>

    @GET("jobs/search")
    suspend fun searchJobs(
        @QueryMap filters: Map<String, String?>
    ): Response<List<Job>>

    @GET("jobs/{id}/applicants")
    suspend fun getApplicantsOfJob(@Path("id") jobId: String): Response<List<Application>>

    @PATCH("jobs/{id}/application")
    suspend fun updateApplicationStatus(@Path("id") jobId: String, @Body requestBody: Map<String, String>): Response<ApiResponse>

    // POSTS

    @GET("posts")
    suspend fun getPosts(
        @Query("page") page: Int,
        @Query("limit") limit: Int
    ): Response<List<Post>>

    @GET("posts/{userId}")
    suspend fun getPostsByUserId(
        @Path("userId") userId: String,
        @Query("page") page: Int,
        @Query("limit") limit: Int,
    ): Response<List<Post>>

    @POST("posts")
    suspend fun createPost(@Body requestBody: Map<String, String>): Response<CreatePostResponse>

    @Multipart
    @POST("posts")
    suspend fun createPostWithImage(
        @Part content: MultipartBody.Part,
        @Part image: MultipartBody.Part
    ): Response<CreatePostResponse>

    @POST("posts/{id}/like")
    suspend fun likePost(@Path("id") postId: String): Response<ApiResponse>

    @POST("posts/{id}/comment")
    suspend fun commentOnPost(@Path("id") postId: String, @Body requestBody: Map<String, String>): Response<ApiResponse>

    @GET("posts/{postId}/comment")
    suspend fun getCommentsOnPost(
        @Path("postId") postId: String,
        @Query("page") page: Int,
        @Query("limit") limit: Int
    ): Response<List<Comment>>

    @DELETE("posts/{postId}/comment/{commentId}")
    suspend fun deleteComment(@Path("postId") postId: String, @Path("commentId") commentId: String): Response<ApiResponse>

    @DELETE("posts/{id}")
    suspend fun deletePost(@Path("id") postId: String): Response<ApiResponse>

}