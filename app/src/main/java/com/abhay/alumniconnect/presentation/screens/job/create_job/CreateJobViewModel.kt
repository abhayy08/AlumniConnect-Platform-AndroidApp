package com.abhay.alumniconnect.presentation.screens.job.create_job

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.abhay.alumniconnect.domain.repository.JobsRepository
import com.abhay.alumniconnect.utils.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CreateJobViewModel @Inject constructor(
    private val jobRepository: JobsRepository
) : ViewModel() {

    private val _newJobState = MutableStateFlow(NewJobState())
    val newJobState: StateFlow<NewJobState> = _newJobState.asStateFlow()

    fun onEvent(event: CreateJobScreenActions) {
        when (event) {
            is CreateJobScreenActions.onApplicationDeadlineChange -> updateField {
                it.copy(
                    applicationDeadline = event.applicationDeadline
                )
            }

            is CreateJobScreenActions.onBenefitsOfferedChange -> updateField { it.copy(benefitsOffered = event.benefitsOffered) }
            is CreateJobScreenActions.onCompanyChange -> updateField { it.copy(company = event.company) }
            is CreateJobScreenActions.onDescriptionChange -> updateField { it.copy(description = event.description) }
            is CreateJobScreenActions.onExperienceLevelChange -> updateField { it.copy(experienceLevel = event.experienceLevel) }
            is CreateJobScreenActions.onGraduationYearChange -> updateField { it.copy(graduationYear = event.graduationYear) }
            is CreateJobScreenActions.onJobTypeChange -> updateField { it.copy(jobType = event.jobType) }
            is CreateJobScreenActions.onLocationChange -> updateField { it.copy(location = event.location) }
            is CreateJobScreenActions.onMinExperienceChange -> updateField { it.copy(minExperience = event.minExperience) }
            is CreateJobScreenActions.onRequiredEducationChange -> updateField {
                it.copy(
                    requiredEducation = event.requiredEducation
                )
            }

            is CreateJobScreenActions.onRequiredSkillsChange -> updateField { it.copy(requiredSkills = event.requiredSkills) }
            is CreateJobScreenActions.onCreateJob -> postJob(event.popBack)
            is CreateJobScreenActions.onTitleChange -> updateField { it.copy(title = event.title) }
            is CreateJobScreenActions.resetError -> updateField { it.copy(message = null) }
        }
    }

    private fun updateField(update: (NewJobState) -> NewJobState) {
        _newJobState.value = update(_newJobState.value)
    }

    private fun postJob(popBack: () -> Unit) {
        viewModelScope.launch {
            if (!validateFields()) {
                updateField { it.copy(message = "Required Fields cannot be empty!") }
                return@launch
            }
            updateField { it.copy(message = null) }
            val result = jobRepository.createJob(_newJobState.value.toJob())
            when(result) {
                is Result.Success<*> -> {
                    updateField { it.copy(message = result.data) }
                    popBack()
                }
                is Result.Error<*> -> updateField { it.copy(message = result.message) }
            }
        }
    }

    private fun validateFields(): Boolean {
        var validate = true
        _newJobState.value.apply {
            if (title.isEmpty() || company.isEmpty() || description.isEmpty() || location.isEmpty()
                || jobType.isEmpty() || experienceLevel.isEmpty() || minExperience.isEmpty() ||
                applicationDeadline.isEmpty() || requiredSkills.isEmpty() || requiredEducation.isEmpty() ||
                graduationYear.isEmpty() || benefitsOffered.isEmpty()
            ) {
                validate = false
            }
        }
        return validate
    }

}