package com.abhay.alumniconnect.data.repository

import com.abhay.alumniconnect.data.remote.AlumniApi
import com.abhay.alumniconnect.data.remote.dto.user.UserToken
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.After
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever
import retrofit2.Response
import org.mockito.kotlin.verify
import com.abhay.alumniconnect.utils.Result
import org.mockito.kotlin.*


@ExperimentalCoroutinesApi
class AlumniAuthRepositoryImplTest {

    private lateinit var repository: AlumniAuthRepositoryImpl
    private val api: AlumniApi = mock(AlumniApi::class.java)
    private val sessionManager: SessionManager = mock(SessionManager::class.java)
    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        repository = AlumniAuthRepositoryImpl(api, sessionManager)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `loginUser returns success and saves token`() = runTest(testDispatcher) {
        val token = UserToken("abc123")
        whenever(api.login(any())).thenReturn(Response.success(token))

        val result = repository.loginUser("user@test.com", "pass123")
        testDispatcher.scheduler.advanceUntilIdle()

        verify(sessionManager).saveUserToken("abc123")
        assertTrue(result is Result.Success)
    }

    @Test
    fun `loginUser returns error on failure`() = runTest(testDispatcher) {
        whenever(api.login(any())).thenReturn(Response.error(401, "Unauthorized".toResponseBody()))

        val result = repository.loginUser("user@test.com", "badpass")
        assertTrue(result is Result.Error)
    }

    @Test
    fun `registerUser returns success and saves token`() = runTest(testDispatcher) {
        val token = UserToken("xyz456")
        whenever(api.register(any())).thenReturn(Response.success(token))

        val result = repository.registerUser(
            email = "test@example.com",
            password = "password",
            name = "Test User",
            graduationYear = 2020,
            currentJob = "Engineer",
            university = "Test University",
            degree = "B.Tech",
            major = "CS"
        )
        testDispatcher.scheduler.advanceUntilIdle()

        verify(sessionManager).saveUserToken("xyz456")
        assertTrue(result is Result.Success)
    }

    @Test
    fun `registerUser returns error on failed response`() = runTest(testDispatcher) {
        whenever(api.register(any())).thenReturn(Response.error(400, "Bad Request".toResponseBody()))

        val result = repository.registerUser(
            email = "test@example.com",
            password = "password",
            name = "Test User",
            graduationYear = 2020,
            currentJob = null,
            university = "Test University",
            degree = "B.Tech",
            major = "CS"
        )
        assertTrue(result is Result.Error)
    }

}
