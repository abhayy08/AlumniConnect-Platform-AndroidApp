package com.abhay.alumniconnect.presentation.screens.job

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.abhay.alumniconnect.data.remote.dto.Job
import com.abhay.alumniconnect.domain.repository.JobsRepository
import com.abhay.alumniconnect.utils.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class JobViewModel @Inject constructor(
    private val jobsRepository: JobsRepository
) : ViewModel() {

    private val _jobScreenState = MutableStateFlow(JobScreenState())
    val jobScreenState: StateFlow<JobScreenState> = _jobScreenState.asStateFlow()

    init {
        getJobs()
    }

    private fun getJobs() {
        viewModelScope.launch {
            when(val result = jobsRepository.getJobs()) {
                is Result.Success<*> -> {
                    _jobScreenState.value = _jobScreenState.value.copy(
                        jobs = result.data as List<Job>
                    )
                }
                is Result.Error<*> -> {

                }
            }
        }
    }

    fun applyForJob(id: String, resumeLink: String) {
        viewModelScope.launch {
            val result = jobsRepository.applyForJob(id, resumeLink)
            when(result) {
                is Result.Success<*> -> {
                    _jobScreenState.value = _jobScreenState.value.copy(message = result.data)
                }
                is Result.Error<*> -> {
                    _jobScreenState.value = _jobScreenState.value.copy(message = result.message)
                }
            }
        }
    }

}

data class JobScreenState(
    val jobs: List<Job> = emptyList(),
    val message: String? = null
)
