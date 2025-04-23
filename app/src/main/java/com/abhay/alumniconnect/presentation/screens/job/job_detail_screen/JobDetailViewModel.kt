package com.abhay.alumniconnect.presentation.screens.job.job_detail_screen

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.abhay.alumniconnect.data.remote.dto.job.Job
import com.abhay.alumniconnect.domain.repository.JobsRepository
import com.abhay.alumniconnect.utils.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit
import javax.inject.Inject

@HiltViewModel
class JobDetailViewModel @Inject constructor(
    private val jobsRepository: JobsRepository
) : ViewModel() {
    private val _jobDetailsState = MutableStateFlow(JobDetailsState())
    val jobDetailsState: StateFlow<JobDetailsState> = _jobDetailsState.asStateFlow()

    private var jobId: String? = null

    fun setJobId(jobId: String) {
        this.jobId = jobId
        loadJobDetails()
    }

    private fun loadJobDetails() {
        _jobDetailsState.value = _jobDetailsState.value.copy(isLoading = true)
        viewModelScope.launch {
            val result = jobsRepository.getJobById(jobId!!)
            when(result){
                is Result.Success<*> -> {

                    val deadline = LocalDateTime.parse(
                        result.data!!.applicationDeadline,
                        DateTimeFormatter.ISO_DATE_TIME
                    )
                    val now = LocalDateTime.now()
                    val daysBetween = ChronoUnit.DAYS.between(now, deadline)

                    val isInDeadline = daysBetween >= 0

                    _jobDetailsState.value = _jobDetailsState.value.copy(
                        job = result.data,
                        isInDeadline = isInDeadline,
                        isLoading = false
                    )

                }
                is Result.Error<*> -> {
                    Log.e("JobDetailViewModel", "Error loading job details ${result.message}")
                    _jobDetailsState.value = _jobDetailsState.value.copy(
                        error = result.message,
                        isLoading = false
                    )
                }
            }
        }
    }

    fun resetError() {
        _jobDetailsState.value = _jobDetailsState.value.copy(error = null)
    }


}

data class JobDetailsState(
    val job: Job? = null,
    val isInDeadline: Boolean = true,
    val isLoading: Boolean = false,
    val error: String? = null,
)
