package com.abhay.alumniconnect.data.repository

import coil.util.CoilUtils.result
import com.abhay.alumniconnect.data.remote.AlumniApi
import com.abhay.alumniconnect.data.remote.dto.user.PrivacySettings
import com.abhay.alumniconnect.data.remote.dto.user.WorkExperience
import com.abhay.alumniconnect.domain.mapper.toUserDetails
import com.abhay.alumniconnect.domain.model.User
import com.abhay.alumniconnect.utils.Result
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject

@ExperimentalCoroutinesApi
@HiltAndroidTest
class AlumniRemoteRepositoryImplAndroidTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Inject
    lateinit var api: AlumniApi

    private lateinit var repository: AlumniRemoteRepositoryImpl
    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setUp() {
        hiltRule.inject()
        Dispatchers.setMain(testDispatcher)
        repository = AlumniRemoteRepositoryImpl(api)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun getCurrentUserReturnsSuccess() = runTest(testDispatcher) {
        val result = repository.getCurrentUser()
        testDispatcher.scheduler.advanceUntilIdle()
        assertTrue(result is Result.Success)
    }

    @Test
    fun getUserByIdReturnsSuccess() = runTest(testDispatcher) {
        val result = repository.getUserById("testUserId")
        testDispatcher.scheduler.advanceUntilIdle()
        assertTrue(result is Result.Success)
    }

    @Test
    fun getUserConnectionsReturnsSuccess() = runTest(testDispatcher) {
        val result = repository.getUserConnections()
        testDispatcher.scheduler.advanceUntilIdle()
        assertTrue(result is Result.Success)
    }

    @Test
    fun getUserConnectionsByUserIdReturnsSuccess() = runTest(testDispatcher) {
        val result = repository.getUserConnectionsByUserId("testUserId")
        testDispatcher.scheduler.advanceUntilIdle()
        assertTrue(result is Result.Success)
    }

    @Test
    fun getOrLoadCurrentUserFlowReturnsStateFlow() = runTest(testDispatcher) {
        // Initialize the flow first by calling getCurrentUser
        repository.getCurrentUser()
        testDispatcher.scheduler.advanceUntilIdle()

        val flow = repository.getOrLoadCurrentUserFlow()
        testDispatcher.scheduler.advanceUntilIdle()
        val result = flow.first()
        assertTrue(result is Result.Success)
    }

    @Test
    fun updateUserReturnsSuccess() = runTest(testDispatcher) {
        // First initialize the current user flow
        repository.getCurrentUser()
        testDispatcher.scheduler.advanceUntilIdle()

        // Create a dummy User object that matches what the FakeAlumniApi expects
        val dummyUser = User(
            id = "user12345",
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
            )
        )

        val result = repository.updateUser(dummyUser)
        testDispatcher.scheduler.advanceUntilIdle()
        assertTrue(result is Result.Success)
    }

    @Test
    fun connectUserReturnsSuccess() = runTest(testDispatcher) {
        val result = repository.connectUser("testUserId")
        testDispatcher.scheduler.advanceUntilIdle()
        assertTrue(result is Result.Success)
    }

    @Test
    fun removeConnectionReturnsSuccess() = runTest(testDispatcher) {
        val result = repository.removeConnection("testUserId")
        testDispatcher.scheduler.advanceUntilIdle()
        assertTrue(result is Result.Success)
    }

    @Test
    fun searchAlumniReturnsSuccess() = runTest(testDispatcher) {
        val query = mapOf("name" to "Test")
        val result = repository.searchAlumni(query)
        testDispatcher.scheduler.advanceUntilIdle()
        assertTrue(result is Result.Success)
    }

    @Test
    fun addWorkExperienceReturnsSuccess() = runTest(testDispatcher) {
        // First initialize the current user flow
        repository.getCurrentUser()
        testDispatcher.scheduler.advanceUntilIdle()

        // Create a work experience that matches what the API expects
        val workExperience = WorkExperience(
            _id = "vulputate",
            company = "TechCorp Inc.",
            position = "Software Engineer",
            startDate = "2024-07-01",
            endDate = null,
            description = "Working on backend systems using Kotlin and Spring Boot."
        )

        val result = repository.addWorkExperience(workExperience)
        testDispatcher.scheduler.advanceUntilIdle()
        assertTrue(result is Result.Success)
    }

    @Test
    fun updateWorkExperienceReturnsSuccess() = runTest(testDispatcher) {
        // First initialize the current user flow
        repository.getCurrentUser()
        testDispatcher.scheduler.advanceUntilIdle()

        // Create a work experience that matches what the API expects
        val workExperience = WorkExperience(
            _id = "vulputate",
            company = "TechCorp Inc.",
            position = "Software Engineer",
            startDate = "2024-07-01",
            endDate = null,
            description = "Working on backend systems using Kotlin and Spring Boot."
        )

        val result = repository.updateWorkExperience("vulputate", workExperience)
        testDispatcher.scheduler.advanceUntilIdle()
        assertTrue(result is Result.Success)
    }

    @Test
    fun deleteWorkExperienceReturnsSuccess() = runTest(testDispatcher) {
        // First initialize the current user flow
        repository.getCurrentUser()
        testDispatcher.scheduler.advanceUntilIdle()

        val result = repository.deleteWorkExperience("vulputate")
        testDispatcher.scheduler.advanceUntilIdle()
        assertTrue(result is Result.Success)
    }
}