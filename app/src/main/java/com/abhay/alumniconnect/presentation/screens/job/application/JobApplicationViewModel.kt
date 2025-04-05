package com.abhay.alumniconnect.presentation.screens.job.application

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.abhay.alumniconnect.domain.repository.JobsRepository
import com.abhay.alumniconnect.presentation.navigation.routes.Route
import com.abhay.alumniconnect.utils.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class JobApplicationViewModel @Inject constructor(
    private val jobsRepository: JobsRepository
) : ViewModel() {

    private val _applicationState = MutableStateFlow(JobApplicationState())
    val applicationState: StateFlow<JobApplicationState> = _applicationState.asStateFlow()

    fun init(jobId: String) {
        _applicationState.value = _applicationState.value.copy(jobId = jobId)
    }

    fun onEvent(event: JobApplicationEvents) {
        when (event) {
            is JobApplicationEvents.ApplyForJob -> applyForJob(event.navigateAndPopUp)
            is JobApplicationEvents.ResetErrorMessage -> resetErrorMessage()
            is JobApplicationEvents.UpdateCoverLetter -> updateCoverLetter(event.coverLetter)
            is JobApplicationEvents.UpdateResumeLink -> updateResumeLink(event.resumeLink)
        }
    }

    private fun updateResumeLink(resumeLink: String) {
        _applicationState.value = _applicationState.value.copy(resumeLink = resumeLink, resumeError = false)
    }

    private fun updateCoverLetter(coverLetter: String) {
        _applicationState.value = _applicationState.value.copy(coverLetter = coverLetter)
    }

    private fun applyForJob(navigateAndPopUp: (Any, Any) -> Unit) {
        viewModelScope.launch {
            if(_applicationState.value.resumeLink.isEmpty()) {
                _applicationState.value = _applicationState.value.copy(resumeError = true, message = "Please enter a valid resume link")
            } else {
                val result = jobsRepository.applyForJob(
                    jobId = _applicationState.value.jobId,
                    resumeLink = _applicationState.value.resumeLink,
                    coverLetter = _applicationState.value.coverLetter
                )

                when(result) {
                    is Result.Success<*> -> {
                        Log.d("JobApplicationViewModel", "applyForJob: ${result.data}")
                        _applicationState.value = _applicationState.value.copy(
                            message = result.data.toString()
                        )
                        navigateAndPopUp(Route.MainRoute.Jobs.JobsLists, Route.MainRoute.Jobs.JobDetails)
                    }
                    is Result.Error<*> -> {
                        Log.d("JobApplicationViewModel", "applyForJob: ${result.message}")
                        _applicationState.value = _applicationState.value.copy(message = result.message)
                    }
                }
            }
        }
    }

    private fun resetErrorMessage() {
        _applicationState.value = _applicationState.value.copy(message = null)
    }


}


sealed class JobApplicationEvents {
    data class UpdateResumeLink(val resumeLink: String) : JobApplicationEvents()
    data class UpdateCoverLetter(val coverLetter: String) : JobApplicationEvents()
    data class ApplyForJob(val navigateAndPopUp: (Any, Any) -> Unit) : JobApplicationEvents()
    object ResetErrorMessage : JobApplicationEvents()
}

data class JobApplicationState(
    val jobId: String = "",
    val resumeLink: String = "",
    val coverLetter: String = "",
    val resumeError: Boolean = false,
    val message: String? = null
)