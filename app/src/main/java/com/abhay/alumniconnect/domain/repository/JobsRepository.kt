package com.abhay.alumniconnect.domain.repository

import com.abhay.alumniconnect.data.remote.dto.job.Job
import com.abhay.alumniconnect.utils.Result

interface JobsRepository {
    suspend fun getJobs(): Result<List<Job>>
    suspend fun getJobsByCurrentUser(): Result<List<Job>>
    suspend fun getJobById(id: String): Result<Job>
    suspend fun getAppliedJobs(): Result<List<Job>>
    suspend fun getOfferedJobs(): Result<List<Job>>
    suspend fun applyForJob(jobId: String, resumeLink: String, coverLetter: String): Result<String>

    suspend fun createJob(job: Job): Result<String>

}