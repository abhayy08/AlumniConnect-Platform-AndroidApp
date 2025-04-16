package com.abhay.alumniconnect.presentation.screens.job

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
class JobViewModel @Inject constructor(
    private val jobsRepository: JobsRepository
) : ViewModel() {

    private val _jobScreenState = MutableStateFlow(JobScreenState())
    val jobScreenState: StateFlow<JobScreenState> = _jobScreenState.asStateFlow()

    private val _jobUiState = MutableStateFlow<JobUIState>(JobUIState.Loading)
    val jobUiState: StateFlow<JobUIState> = _jobUiState.asStateFlow()

    init {
        getJobs()
        getAppliedJobs()
        getOfferedJobs()
    }

    private val _selectedJob = MutableStateFlow(SelectedJobState())
    val selectedJob: StateFlow<SelectedJobState> = _selectedJob.asStateFlow()

    private fun getJobs() {
        _jobUiState.value = JobUIState.Loading
        viewModelScope.launch {
            when(val result = jobsRepository.getJobs()) {
                is Result.Success<*> -> {
                    _jobScreenState.value = _jobScreenState.value.copy(
                        jobs = result.data as List<Job>
                    )
                    _jobUiState.value = JobUIState.Success()
                }
                is Result.Error<*> -> {
                    _jobUiState.value = JobUIState.Error(result.message.toString())
                }
            }
        }
    }

    private fun getAppliedJobs() {
        _jobUiState.value = JobUIState.Loading
        viewModelScope.launch {
            when(val result = jobsRepository.getAppliedJobs()) {
                is Result.Success<*> -> {
                    _jobScreenState.value = _jobScreenState.value.copy(
                        jobsAppliedTo = result.data as List<Job>
                    )
                    _jobUiState.value = JobUIState.Success()
                }
                is Result.Error<*> -> {
                    _jobUiState.value = JobUIState.Error(result.message.toString())
                }
            }
        }
    }

    private fun getOfferedJobs() {
        _jobUiState.value = JobUIState.Loading
        viewModelScope.launch {
            when(val result = jobsRepository.getOfferedJobs()) {
                is Result.Success<*> -> {
                    _jobScreenState.value = _jobScreenState.value.copy(
                        jobsOffered = result.data as List<Job>
                    )
                    Log.d("JobViewModel", "getOfferedJobs: ${_jobScreenState.value.jobsOffered}")
                    _jobUiState.value = JobUIState.Success()
                }
                is Result.Error<*> -> {
                    _jobUiState.value = JobUIState.Error(result.message.toString())
                }
            }
        }
    }

    fun getJobById(id: String, isApplied: Boolean) {
        if(isApplied) {
            getJobByIdFromAppliedJobList(id)
        }else {
            getJobByIdFromJobList(id)
        }
    }

    private fun getJobByIdFromAppliedJobList(id: String) {
        if(_selectedJob.value.job == null) {
            _jobUiState.value = JobUIState.Loading
            val appliedJob = _jobScreenState.value.jobsAppliedTo.find { it._id == id }
            if (appliedJob != null) {
                val deadline = LocalDateTime.parse(
                    appliedJob.applicationDeadline,
                    DateTimeFormatter.ISO_DATE_TIME
                )
                val now = LocalDateTime.now()
                val daysBetween = ChronoUnit.DAYS.between(now, deadline)

                val isInDeadline = daysBetween >= 0

                _selectedJob.value = _selectedJob.value.copy(
                    job = appliedJob,
                    isInDeadline = isInDeadline
                )
                _jobUiState.value = JobUIState.Success()
            } else {
                _jobUiState.value = JobUIState.Error("Job not found")
            }
        }
    }

    private fun getJobByIdFromJobList(id: String) {
        if(_selectedJob.value.job == null) {
            _jobUiState.value = JobUIState.Loading
            val job = _jobScreenState.value.jobs.find { it._id == id }
            if (job != null) {
                val deadline = LocalDateTime.parse(
                    job.applicationDeadline,
                    DateTimeFormatter.ISO_DATE_TIME
                )
                val now = LocalDateTime.now()
                val daysBetween = ChronoUnit.DAYS.between(now, deadline)

                val isInDeadline = daysBetween >= 0

                _selectedJob.value = _selectedJob.value.copy(
                    job = job,
                    isInDeadline = isInDeadline
                )
                _jobUiState.value = JobUIState.Success()
            } else {
                _jobUiState.value = JobUIState.Error("Job not found")
            }
        }
    }

    fun refreshData() {
        _selectedJob.value = SelectedJobState()
        getJobs()
        getAppliedJobs()
    }

    fun onNavigateBack(onBackClick: () -> Unit) {
        _selectedJob.value = SelectedJobState()
        onBackClick()
    }

}

data class SelectedJobState(
    val job: Job? = null,
    val isInDeadline: Boolean = true
)
