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

    fun getApplicantsOfJob(jobId: String) {
        viewModelScope.launch {
            val result = jobRepository.getJobApplicants(jobId)

            when(result) {
                is Result.Success<*> -> {
                    _applicantsState.update { result.data!! }
                }
                is Result.Error<*> -> {
                    Log.d("ApplicantsViewModel", "getApplicantsOfJob: ${result.message}")
                }
            }
        }
    }

}