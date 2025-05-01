package com.abhay.alumniconnect.data.remote

import com.abhay.alumniconnect.data.remote.dto.ApiResponse
import com.abhay.alumniconnect.data.remote.dto.Connection
import com.abhay.alumniconnect.data.remote.dto.CreatePostResponse
import com.abhay.alumniconnect.data.remote.dto.ImageResponse
import com.abhay.alumniconnect.data.remote.dto.job.Application
import com.abhay.alumniconnect.data.remote.dto.job.Job
import com.abhay.alumniconnect.data.remote.dto.post.Comment
import com.abhay.alumniconnect.data.remote.dto.post.Post
import com.abhay.alumniconnect.data.remote.dto.user.PrivacySettings
import com.abhay.alumniconnect.data.remote.dto.user.UserDetails
import com.abhay.alumniconnect.data.remote.dto.user.UserToken
import com.abhay.alumniconnect.data.remote.dto.user.WorkExperience
import com.abhay.alumniconnect.domain.model.User
import okhttp3.MultipartBody
import retrofit2.Response
import javax.inject.Inject

class FakeAlumniApi @Inject constructor() : AlumniApi {

    private fun dummyJob(id: String = "1") = Job(
        _id = id,
        title = "Software Engineer",
        __v = 0,
        benefitsOffered = emptyList(),
        company = "TestCorp",
        createdAt = "",
        description = "Job description",
        experienceLevel = "Entry",
        graduationYear = 2022,
        jobType = "Full-time",
        location = "Remote",
        minExperience = 0,
        postedBy = null,
        requiredEducation = emptyList(),
        applications = emptyList(),
        requiredSkills = listOf("Kotlin"),
        status = "open",
        updatedAt = "",
        alreadyApplied = false,
        applicationDeadline = ""
    )

    override suspend fun login(requestBody: Map<String, String>) =
        Response.success(UserToken("dummy_token"))

    override suspend fun register(requestBody: Map<String, String>) =
        Response.success(UserToken("dummy_token"))

    override suspend fun getCurrentUser() =
        Response.success(
            UserDetails(
                __v = 1,
                _id = "user12345",
                achievements = listOf("Published 3 research papers", "Top 10 in Hackathon"),
                bio = "Aspiring software engineer with a passion for AI and open-source.",
                company = "TechCorp Inc.",
                connections = listOf(
                    Connection(
                        _id = "conn1", name = "Jane Doe",
                        email = "barbara.moody@example.com",
                        company = "intellegebat",
                        jobTitle = "offendit"
                    ),
                    Connection(
                        _id = "conn2", name = "John Smith",
                        email = "barbara.moody@example.com",
                        company = "intellegebat",
                        jobTitle = "offendit"
                    )
                ),
                createdAt = "2024-06-10T14:00:00Z",
                currentJob = "Software Engineer",
                jobTitle = "Backend Developer",
                degree = "B.Sc. Computer Science",
                email = "dummyuser@example.com",
                fieldOfStudy = "Artificial Intelligence",
                graduationYear = 2024,
                interests = listOf("Machine Learning", "Startups", "Gaming"),
                isVerifiedUser = true,
                linkedInProfile = "https://linkedin.com/in/dummyuser",
                location = "San Francisco, CA",
                major = "Computer Science",
                minor = "Mathematics",
                name = "Alex Johnson",
                privacySettings = PrivacySettings(
                    showEmail = true,
                    showLocation = false,
                    showPhone = false,
                ),
                skills = listOf("Kotlin", "Python", "Docker", "Kubernetes"),
                university = "Stanford University",
                updatedAt = "2025-04-25T10:30:00Z",
                workExperience = listOf(
                    WorkExperience(
                        company = "TechCorp Inc.",
                        startDate = "2024-07-01",
                        endDate = null,
                        description = "Working on backend systems using Kotlin and Spring Boot.",
                        _id = "vulputate",
                        position = "nominavi"
                    ),
                    WorkExperience(
                        company = "Innovate Labs",
                        startDate = "2023-06-01",
                        endDate = "2023-08-31",
                        description = "Built automation scripts and contributed to the web platform.",
                        _id = "vulputate",
                        position = "nominavi"
                    )
                ),
                connectionCount = 2,
                isConnected = false,
                profileImage = "https://example.com/images/dummyuser.jpg"
            )
        )

    override suspend fun getUserById(userId: String) =
        Response.success(
            UserDetails(
                __v = 1,
                _id = "user12345",
                achievements = listOf("Published 3 research papers", "Top 10 in Hackathon"),
                bio = "Aspiring software engineer with a passion for AI and open-source.",
                company = "TechCorp Inc.",
                connections = listOf(
                    Connection(
                        _id = "conn1", name = "Jane Doe",
                        email = "barbara.moody@example.com",
                        company = "intellegebat",
                        jobTitle = "offendit"
                    ),
                    Connection(
                        _id = "conn2", name = "John Smith",
                        email = "barbara.moody@example.com",
                        company = "intellegebat",
                        jobTitle = "offendit"
                    )
                ),
                createdAt = "2024-06-10T14:00:00Z",
                currentJob = "Software Engineer",
                jobTitle = "Backend Developer",
                degree = "B.Sc. Computer Science",
                email = "dummyuser@example.com",
                fieldOfStudy = "Artificial Intelligence",
                graduationYear = 2024,
                interests = listOf("Machine Learning", "Startups", "Gaming"),
                isVerifiedUser = true,
                linkedInProfile = "https://linkedin.com/in/dummyuser",
                location = "San Francisco, CA",
                major = "Computer Science",
                minor = "Mathematics",
                name = "Alex Johnson",
                privacySettings = PrivacySettings(
                    showEmail = true,
                    showLocation = false,
                    showPhone = false,
                ),
                skills = listOf("Kotlin", "Python", "Docker", "Kubernetes"),
                university = "Stanford University",
                updatedAt = "2025-04-25T10:30:00Z",
                workExperience = listOf(
                    WorkExperience(
                        company = "TechCorp Inc.",
                        startDate = "2024-07-01",
                        endDate = null,
                        description = "Working on backend systems using Kotlin and Spring Boot.",
                        _id = "vulputate",
                        position = "nominavi"
                    ),
                    WorkExperience(
                        company = "Innovate Labs",
                        startDate = "2023-06-01",
                        endDate = "2023-08-31",
                        description = "Built automation scripts and contributed to the web platform.",
                        _id = "vulputate",
                        position = "nominavi"
                    )
                ),
                connectionCount = 2,
                isConnected = false,
                profileImage = "https://example.com/images/dummyuser.jpg"
            )
        )

    override suspend fun getUserConnections() =
        Response.success(emptyList<Connection>())

    override suspend fun getUserConnectionsByUserId(userId: String) =
        Response.success(emptyList<Connection>())

    override suspend fun connectUser(targetUserId: String) =
        Response.success(ApiResponse("Connected"))

    override suspend fun removeConnection(targetUserId: String) =
        Response.success(ApiResponse("Removed"))

    override suspend fun updateProfile(requestBody: UserDetails): Response<ApiResponse> {
        val dummyUser = UserDetails(
            _id = "user12345",
            name = "Alex Johnson",
            email = "dummyuser@example.com",
            profileImage = "https://example.com/images/dummyuser.jpg",
            bio = "Aspiring software engineer with a passion for AI and open-source.",
            company = "TechCorp Inc.",
            jobTitle = "Backend Developer",
            location = "San Francisco, CA",
            university = "Stanford University",
            degree = "B.Sc. Computer Science",
            major = "Computer Science",
            graduationYear = 2024,
            skills = listOf("Kotlin", "Python", "Docker", "Kubernetes"),
            interests = listOf("Machine Learning", "Startups", "Gaming"),
            achievements = listOf("Published 3 research papers", "Top 10 in Hackathon"),
            workExperience = emptyList(),
            connections = emptyList(),
            connectionCount = 2,
            isConnected = false,
            currentJob = "sententiae",
            fieldOfStudy = "principes",
            minor = "tellus",
            linkedInProfile = "himenaeos",
            isVerifiedUser = false,
            createdAt = "molestie",
            updatedAt = "dictum",
            privacySettings = PrivacySettings(
                showEmail = false,
                showLocation = false,
                showPhone = false
            ),
            __v = 1
        )
        return Response.success(ApiResponse("Updated", dummyUser))
    }

    override suspend fun updateWorkExperienceById(id: String, requestBody: WorkExperience): Response<ApiResponse> {
        val dummyUser = UserDetails(
            _id = "user12345",
            name = "Alex Johnson",
            email = "dummyuser@example.com",
            profileImage = "https://example.com/images/dummyuser.jpg",
            bio = "Aspiring software engineer with a passion for AI and open-source.",
            company = "TechCorp Inc.",
            jobTitle = "Backend Developer",
            location = "San Francisco, CA",
            university = "Stanford University",
            degree = "B.Sc. Computer Science",
            major = "Computer Science",
            graduationYear = 2024,
            skills = listOf("Kotlin", "Python", "Docker", "Kubernetes"),
            interests = listOf("Machine Learning", "Startups", "Gaming"),
            achievements = listOf("Published 3 research papers", "Top 10 in Hackathon"),
            workExperience = emptyList(),
            connections = emptyList(),
            connectionCount = 2,
            isConnected = false,
            currentJob = "sententiae",
            fieldOfStudy = "principes",
            minor = "tellus",
            linkedInProfile = "himenaeos",
            isVerifiedUser = false,
            createdAt = "molestie",
            updatedAt = "dictum",
            privacySettings = PrivacySettings(
                showEmail = false,
                showLocation = false,
                showPhone = false
            ),
            __v = 1
        )

        return Response.success(ApiResponse("Updated", dummyUser))
    }


    override suspend fun addWorkExperience(requestBody: WorkExperience): Response<ApiResponse> {
        val dummyUser = UserDetails(
            _id = "user12345",
            name = "Alex Johnson",
            email = "dummyuser@example.com",
            profileImage = "https://example.com/images/dummyuser.jpg",
            bio = "Aspiring software engineer with a passion for AI and open-source.",
            company = "TechCorp Inc.",
            jobTitle = "Backend Developer",
            location = "San Francisco, CA",
            university = "Stanford University",
            degree = "B.Sc. Computer Science",
            major = "Computer Science",
            graduationYear = 2024,
            skills = listOf("Kotlin", "Python", "Docker", "Kubernetes"),
            interests = listOf("Machine Learning", "Startups", "Gaming"),
            achievements = listOf("Published 3 research papers", "Top 10 in Hackathon"),
            workExperience = emptyList(),
            connections = emptyList(),
            connectionCount = 2,
            isConnected = false,
            currentJob = "sententiae",
            fieldOfStudy = "principes",
            minor = "tellus",
            linkedInProfile = "himenaeos",
            isVerifiedUser = false,
            createdAt = "molestie",
            updatedAt = "dictum",
            privacySettings = PrivacySettings(
                showEmail = false,
                showLocation = false,
                showPhone = false
            ),
            __v = 1
        )

        return Response.success(ApiResponse("Added", dummyUser))
    }


    override suspend fun deleteWorkExperienceById(id: String): Response<ApiResponse> {
        val dummyUser = UserDetails(
            _id = "user12345",
            name = "Alex Johnson",
            email = "dummyuser@example.com",
            profileImage = "https://example.com/images/dummyuser.jpg",
            bio = "Aspiring software engineer with a passion for AI and open-source.",
            company = "TechCorp Inc.",
            jobTitle = "Backend Developer",
            location = "San Francisco, CA",
            university = "Stanford University",
            degree = "B.Sc. Computer Science",
            major = "Computer Science",
            graduationYear = 2024,
            skills = listOf("Kotlin", "Python", "Docker", "Kubernetes"),
            interests = listOf("Machine Learning", "Startups", "Gaming"),
            achievements = listOf("Published 3 research papers", "Top 10 in Hackathon"),
            workExperience = emptyList(),
            connections = emptyList(),
            connectionCount = 2,
            isConnected = false,
            currentJob = "sententiae",
            fieldOfStudy = "principes",
            minor = "tellus",
            linkedInProfile = "himenaeos",
            isVerifiedUser = false,
            createdAt = "molestie",
            updatedAt = "dictum",
            privacySettings = PrivacySettings(
                showEmail = false,
                showLocation = false,
                showPhone = false
            ),
            __v = 1
        )

        return Response.success(ApiResponse("Deleted", dummyUser))
    }


    override suspend fun searchAlumni(filters: Map<String, String?>) =
        Response.success(listOf(
            UserDetails(
                __v = 1,
                _id = "user12345",
                achievements = listOf("Published 3 research papers", "Top 10 in Hackathon"),
                bio = "Aspiring software engineer with a passion for AI and open-source.",
                company = "TechCorp Inc.",
                connections = listOf(
                    Connection(
                        _id = "conn1", name = "Jane Doe",
                        email = "barbara.moody@example.com",
                        company = "intellegebat",
                        jobTitle = "offendit"
                    ),
                    Connection(
                        _id = "conn2", name = "John Smith",
                        email = "barbara.moody@example.com",
                        company = "intellegebat",
                        jobTitle = "offendit"
                    )
                ),
                createdAt = "2024-06-10T14:00:00Z",
                currentJob = "Software Engineer",
                jobTitle = "Backend Developer",
                degree = "B.Sc. Computer Science",
                email = "dummyuser@example.com",
                fieldOfStudy = "Artificial Intelligence",
                graduationYear = 2024,
                interests = listOf("Machine Learning", "Startups", "Gaming"),
                isVerifiedUser = true,
                linkedInProfile = "https://linkedin.com/in/dummyuser",
                location = "San Francisco, CA",
                major = "Computer Science",
                minor = "Mathematics",
                name = "Alex Johnson",
                privacySettings = PrivacySettings(
                    showEmail = true,
                    showLocation = false,
                    showPhone = false,
                ),
                skills = listOf("Kotlin", "Python", "Docker", "Kubernetes"),
                university = "Stanford University",
                updatedAt = "2025-04-25T10:30:00Z",
                workExperience = listOf(
                    WorkExperience(
                        company = "TechCorp Inc.",
                        startDate = "2024-07-01",
                        endDate = null,
                        description = "Working on backend systems using Kotlin and Spring Boot.",
                        _id = "vulputate",
                        position = "nominavi"
                    ),
                    WorkExperience(
                        company = "Innovate Labs",
                        startDate = "2023-06-01",
                        endDate = "2023-08-31",
                        description = "Built automation scripts and contributed to the web platform.",
                        _id = "vulputate",
                        position = "nominavi"
                    )
                ),
                connectionCount = 2,
                isConnected = false,
                profileImage = "https://example.com/images/dummyuser.jpg"
            )
        ))

    override suspend fun uploadProfileImage(image: MultipartBody.Part) =
        Response.success(ImageResponse("image_url"))

    override suspend fun getJobs() =
        Response.success(listOf(dummyJob()))

    override suspend fun getJobsByCurrentUser() =
        Response.success(listOf(dummyJob()))

    override suspend fun getJobById(jobId: String) =
        Response.success(dummyJob(jobId))

    override suspend fun applyForJob(jobId: String, requestBody: Map<String, String>) =
        Response.success(ApiResponse("Applied"))

    override suspend fun deleteJobById(jobId: String) =
        Response.success(ApiResponse("Deleted"))

    override suspend fun getAppliedJobs() =
        Response.success(listOf(dummyJob()))

    override suspend fun getOfferedJobs() =
        Response.success(listOf(dummyJob()))

    override suspend fun getJobsByUserId(userId: String) =
        Response.success(listOf(dummyJob()))

    override suspend fun createJob(requestBody: Job) =
        Response.success(ApiResponse("Created"))

    override suspend fun searchJobs(filters: Map<String, String?>) =
        Response.success(listOf(dummyJob()))

    override suspend fun getApplicantsOfJob(jobId: String) =
        Response.success(emptyList<Application>())

    override suspend fun updateApplicationStatus(jobId: String, requestBody: Map<String, String>) =
        Response.success(ApiResponse("Updated"))

    override suspend fun getPosts(page: Int, limit: Int) =
        Response.success(emptyList<Post>())

    override suspend fun getPostsByUserId(userId: String, page: Int, limit: Int) =
        Response.success(emptyList<Post>())

    override suspend fun createPost(requestBody: Map<String, String>) =
        Response.success(CreatePostResponse("Post Created"))

    override suspend fun createPostWithImage(
        content: MultipartBody.Part,
        image: MultipartBody.Part,
    ) =
        Response.success(CreatePostResponse("Post with image created"))

    override suspend fun likePost(postId: String) =
        Response.success(ApiResponse("Liked"))

    override suspend fun commentOnPost(postId: String, requestBody: Map<String, String>) =
        Response.success(ApiResponse("Commented"))

    override suspend fun getCommentsOnPost(postId: String, page: Int, limit: Int) =
        Response.success(emptyList<Comment>())

    override suspend fun deleteComment(postId: String, commentId: String) =
        Response.success(ApiResponse("Deleted"))

    override suspend fun deletePost(postId: String) =
        Response.success(ApiResponse("Deleted"))
}