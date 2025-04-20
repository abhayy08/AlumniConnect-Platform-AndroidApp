package com.abhay.alumniconnect.presentation.screens.profile.applicants

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.abhay.alumniconnect.data.remote.dto.job.Applicant
import com.abhay.alumniconnect.data.remote.dto.job.Application
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
class ApplicantsViewModel @Inject constructor(
    private val jobRepository: JobsRepository
) : ViewModel() {

    private val _applicantsState = MutableStateFlow(emptyList<Application>())
    val applicantsState: StateFlow<List<Application>> = _applicantsState.asStateFlow()

    private val _messageState = MutableStateFlow<String?>(null)
    val messageState: StateFlow<String?> = _messageState.asStateFlow()

    fun getApplicantsOfJob(jobId: String) {
        viewModelScope.launch {
            val result = jobRepository.getJobApplicants(jobId)

            when(result) {
                is Result.Success<*> -> {
                    _applicantsState.update { result.data!! }
                }
                is Result.Error<*> -> {
                    _messageState.update { result.message }
                }
            }
        }
    }

    fun updateApplicationState(jobId: String, applicationId: String, status: String) {
        Log.d("ApplicantsViewModel", "updateApplicationState: $jobId, $applicationId, $status")
        viewModelScope.launch {
            val result = jobRepository.updateApplicationStatus(jobId, applicationId, status)
            when(result) {
                is Result.Success<*> -> {
                    _messageState.update { result.data }
                }
                is Result.Error<*> -> {
                    _messageState.update { result.message }
                }
            }
        }
    }

    fun resetMessageState() {
        _messageState.update { null }
    }

}