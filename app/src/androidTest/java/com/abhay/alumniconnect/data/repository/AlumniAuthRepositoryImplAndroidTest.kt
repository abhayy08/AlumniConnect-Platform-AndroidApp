package com.abhay.alumniconnect.data.repository

import com.abhay.alumniconnect.data.remote.AlumniApi
import com.abhay.alumniconnect.utils.Result
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
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
class AlumniAuthRepositoryImplAndroidTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Inject
    lateinit var api: AlumniApi

    @Inject
    lateinit var sessionManager: SessionManager

    private lateinit var repository: AlumniAuthRepositoryImpl
    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setUp() {
        hiltRule.inject()
        Dispatchers.setMain(testDispatcher)
        repository = AlumniAuthRepositoryImpl(api, sessionManager)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun loginUserReturnsSuccess() = runTest(testDispatcher) {
        val result = repository.loginUser("test@example.com", "password")
        testDispatcher.scheduler.advanceUntilIdle()
        assertTrue(result is Result.Success)
    }

    @Test
    fun registerUserReturnsSuccess() = runTest(testDispatcher) {
        val result = repository.registerUser(
            email = "test@example.com",
            password = "password",
            name = "Test User",
            graduationYear = 2022,
            currentJob = "Software Engineer",
            university = "Test University",
            degree = "Bachelor's",
            major = "Computer Science"
        )
        testDispatcher.scheduler.advanceUntilIdle()
        assertTrue(result is Result.Success)
    }
}