package com.abhay.alumniconnect.data.repository

import android.util.Log
import com.abhay.alumniconnect.data.remote.AlumniApi
import com.abhay.alumniconnect.data.remote.dto.Job
import com.abhay.alumniconnect.domain.repository.JobsRepository
import com.abhay.alumniconnect.utils.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class JobsRepositoryImpl @Inject constructor(
    private val api: AlumniApi
) : JobsRepository {
    override suspend fun getJobs(): Result<List<Job>> =
        withContext(Dispatchers.IO) {
            try {
                val response = api.getJobs()
                if (!response.isSuccessful) {
                    return@withContext Result.Error(
                        message = extractErrorMessage(response, ERROR_TAG)
                    )
                }
                response.body()?.let {
                    Log.d(ERROR_TAG, "getJobs: $it")
                    return@withContext Result.Success(it)
                }
                Result.Error(message = "An unknown error has occurred!")

            } catch (e: Exception) {
                Result.Error(message = e.message ?: "An unknown error has occurred!")
            }
        }

    override suspend fun getJobById(id: String): Result<Job> =
        withContext(Dispatchers.IO) {
            try {
                val response = api.getJobById(id)
                if (!response.isSuccessful) {
                    return@withContext Result.Error(
                        message = extractErrorMessage(response, ERROR_TAG)
                    )
                }

                response.body()?.let { job ->
                    return@withContext Result.Success(job)
                }

                Result.Error(message = "An unknown error has occurred!")
            } catch (e: Exception) {
                Result.Error(message = "An unknown error has occurred!")
            }
        }

    override suspend fun getAppliedJobs(): Result<List<Job>> =
        withContext(Dispatchers.IO) {
            try {
                val response = api.getAppliedJobs()
                if(!response.isSuccessful) {
                    return@withContext Result.Error(
                        message = extractErrorMessage(response, ERROR_TAG)
                    )
                }
                response.body()?.let {
                    return@withContext Result.Success(it)
                }
                Result.Error(message = "An unknown error has occurred!")

            }catch(e: java.lang.Exception) {
                Result.Error(message = "An unknown error has occurred!")
            }
        }

    override suspend fun applyForJob(jobId: String, resumeLink: String): Result<String> =
        withContext(Dispatchers.IO) {
            try {
                val requestBody =  mapOf("resumeLink" to resumeLink)
                val response = api.applyForJob(jobId, requestBody)
                if (!response.isSuccessful) {
                    return@withContext Result.Error(
                        message = extractErrorMessage(response, ERROR_TAG)
                    )
                }

                response.body()?.let {
                    Log.d(ERROR_TAG, "applyForJob: ${it.message}")
                    return@withContext Result.Success(it.message)
                }

                Result.Error(message = "An unknown error has occurred!")
            } catch (e: Exception) {
                Result.Error(message = "An unknown error has occurred!")
            }
        }


    companion object {
        const val ERROR_TAG = "JobsRepository"
    }
}