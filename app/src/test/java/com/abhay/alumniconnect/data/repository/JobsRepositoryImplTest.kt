package com.abhay.alumniconnect.data.repository

import com.abhay.alumniconnect.data.remote.AlumniApi
import com.abhay.alumniconnect.data.remote.dto.ApiResponse
import com.abhay.alumniconnect.data.remote.dto.job.Job
import com.abhay.alumniconnect.utils.Result
import org.junit.Assert.assertTrue
import org.junit.Assert.assertEquals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever
import org.mockito.kotlin.*
import retrofit2.Response

@ExperimentalCoroutinesApi
class JobsRepositoryImplTest {

    private lateinit var repository: JobsRepositoryImpl
    private val api: AlumniApi = mock()
    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        repository = JobsRepositoryImpl(api)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    private fun sampleJob() = Job(
        _id = "1", title = "Developer", __v = 0, benefitsOffered = listOf(), company = "TestCorp",
        createdAt = "", description = "", experienceLevel = "", graduationYear = 2022, jobType = "",
        location = "", minExperience = 1, postedBy = null, requiredEducation = listOf(), applications = listOf(),
        requiredSkills = listOf(), status = "", updatedAt = "", alreadyApplied = false, applicationDeadline = ""
    )

    @Test
    fun `getJobs returns success`() = runTest(testDispatcher) {
        whenever(api.getJobs()).thenReturn(Response.success(listOf(sampleJob())))
        val result = repository.getJobs()
        testDispatcher.scheduler.advanceUntilIdle()
        assertTrue(result is Result.Success)
    }

    @Test
    fun `getJobs returns error`() = runTest(testDispatcher) {
        whenever(api.getJobs()).thenReturn(Response.error(400, "Error".toResponseBody()))
        val result = repository.getJobs()
        testDispatcher.scheduler.advanceUntilIdle()
        assertTrue(result is Result.Error)
    }

    @Test
    fun `getJobById returns success`() = runTest(testDispatcher) {
        whenever(api.getJobById("1")).thenReturn(Response.success(sampleJob()))
        val result = repository.getJobById("1")
        testDispatcher.scheduler.advanceUntilIdle()
        assertTrue(result is Result.Success)
    }

    @Test
    fun `getAppliedJobs returns success`() = runTest(testDispatcher) {
        whenever(api.getAppliedJobs()).thenReturn(Response.success(listOf(sampleJob())))
        val result = repository.getAppliedJobs()
        testDispatcher.scheduler.advanceUntilIdle()
        assertTrue(result is Result.Success)
    }

    @Test
    fun `getOfferedJobs returns success`() = runTest(testDispatcher) {
        whenever(api.getOfferedJobs()).thenReturn(Response.success(listOf(sampleJob())))
        val result = repository.getOfferedJobs()
        testDispatcher.scheduler.advanceUntilIdle()
        assertTrue(result is Result.Success)
    }

    @Test
    fun `applyForJob returns success`() = runTest(testDispatcher) {
        whenever(api.applyForJob(any(), any())).thenReturn(Response.success(ApiResponse(message = "Applied")))
        val result = repository.applyForJob("1", "resume", "cover")
        testDispatcher.scheduler.advanceUntilIdle()
        assertTrue(result is Result.Success)
    }

    @Test
    fun `deleteJobById returns success`() = runTest(testDispatcher) {
        whenever(api.deleteJobById("1")).thenReturn(Response.success(ApiResponse(message = "Deleted")))
        val result = repository.deleteJobById("1")
        testDispatcher.scheduler.advanceUntilIdle()
        assertTrue(result is Result.Success)
    }

    @Test
    fun `createJob returns success`() = runTest(testDispatcher) {
        whenever(api.createJob(any())).thenReturn(Response.success(ApiResponse(message = "Created")))
        val result = repository.createJob(sampleJob())
        testDispatcher.scheduler.advanceUntilIdle()
        assertTrue(result is Result.Success)
    }

    @Test
    fun `searchJobs returns success`() = runTest(testDispatcher) {
        whenever(api.searchJobs(any())).thenReturn(Response.success(listOf(sampleJob())))
        val result = repository.searchJobs(mapOf("q" to "developer"))
        testDispatcher.scheduler.advanceUntilIdle()
        assertTrue(result is Result.Success)
    }
}
