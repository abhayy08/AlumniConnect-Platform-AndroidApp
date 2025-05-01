package com.abhay.alumniconnect.data.repository

import com.abhay.alumniconnect.data.remote.AlumniApi
import com.abhay.alumniconnect.data.remote.dto.job.Job
import com.abhay.alumniconnect.data.remote.dto.job.RequiredEducation
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
class JobsRepositoryImplAndroidTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Inject
    lateinit var api: AlumniApi

    private lateinit var repository: JobsRepositoryImpl
    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setUp() {
        hiltRule.inject()
        Dispatchers.setMain(testDispatcher)
        repository = JobsRepositoryImpl(api)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun getJobsReturnsSuccess() = runTest(testDispatcher) {
        val result = repository.getJobs()
        testDispatcher.scheduler.advanceUntilIdle()
        assertTrue(result is Result.Success)
    }

    @Test
    fun getJobsByCurrentUserReturnsSuccess() = runTest(testDispatcher) {
        val result = repository.getJobsByCurrentUser()
        testDispatcher.scheduler.advanceUntilIdle()
        assertTrue(result is Result.Success)
    }

    @Test
    fun getJobByIdReturnsSuccess() = runTest(testDispatcher) {
        val result = repository.getJobById("testJobId")
        testDispatcher.scheduler.advanceUntilIdle()
        assertTrue(result is Result.Success)
    }

    @Test
    fun getAppliedJobsReturnsSuccess() = runTest(testDispatcher) {
        val result = repository.getAppliedJobs()
        testDispatcher.scheduler.advanceUntilIdle()
        assertTrue(result is Result.Success)
    }

    @Test
    fun getOfferedJobsReturnsSuccess() = runTest(testDispatcher) {
        val result = repository.getOfferedJobs()
        testDispatcher.scheduler.advanceUntilIdle()
        assertTrue(result is Result.Success)
    }

    @Test
    fun applyForJobReturnsSuccess() = runTest(testDispatcher) {
        val result = repository.applyForJob(
            jobId = "testJobId",
            resumeLink = "https://example.com/resume.pdf",
            coverLetter = "Test cover letter"
        )
        testDispatcher.scheduler.advanceUntilIdle()
        assertTrue(result is Result.Success)
    }

    @Test
    fun deleteJobByIdReturnsSuccess() = runTest(testDispatcher) {
        val result = repository.deleteJobById("testJobId")
        testDispatcher.scheduler.advanceUntilIdle()
        assertTrue(result is Result.Success)
    }

    @Test
    fun getJobsByUserIdReturnsSuccess() = runTest(testDispatcher) {
        val result = repository.getJobsByUserId("testUserId")
        testDispatcher.scheduler.advanceUntilIdle()
        assertTrue(result is Result.Success)
    }

    @Test
    fun createJobReturnsSuccess() = runTest(testDispatcher) {
        // Create a dummy Job object for testing
        val dummyJob = Job(
            _id = "testId",
            title = "Test Job",
            company = "Test Company",
            location = "Test Location",
            description = "Test Description",
            jobType = "Full-time",
            experienceLevel = "Entry-level",
            minExperience = 0,
            requiredSkills = listOf("Skill1", "Skill2"),
            requiredEducation = listOf(
                RequiredEducation(
                    branch = "sonet", degree = "definiebas"
                )
            ),
            benefitsOffered = listOf("Benefit1", "Benefit2"),
            status = "Open",
            postedBy = null,
            applications = emptyList(),
            createdAt = "2022-01-01",
            updatedAt = "2022-01-01",
            __v = 0,
            graduationYear = 2022,
            alreadyApplied = false,
            applicationDeadline = "2022-12-31"
        )

        val result = repository.createJob(dummyJob)
        testDispatcher.scheduler.advanceUntilIdle()
        assertTrue(result is Result.Success)
    }

    @Test
    fun searchJobsReturnsSuccess() = runTest(testDispatcher) {
        val query = mapOf("title" to "Test")
        val result = repository.searchJobs(query)
        testDispatcher.scheduler.advanceUntilIdle()
        assertTrue(result is Result.Success)
    }

    @Test
    fun getJobApplicantsReturnsSuccess() = runTest(testDispatcher) {
        val result = repository.getJobApplicants("testJobId")
        testDispatcher.scheduler.advanceUntilIdle()
        assertTrue(result is Result.Success)
    }

    @Test
    fun updateApplicationStatusReturnsSuccess() = runTest(testDispatcher) {
        val result = repository.updateApplicationStatus(
            jobId = "testJobId",
            applicationId = "testApplicationId",
            status = "Accepted"
        )
        testDispatcher.scheduler.advanceUntilIdle()
        assertTrue(result is Result.Success)
    }
}