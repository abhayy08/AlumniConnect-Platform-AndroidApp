package com.abhay.alumniconnect.presentation.screens.profile.add_edit_work_experience

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.abhay.alumniconnect.domain.repository.AlumniRemoteRepository
import com.abhay.alumniconnect.utils.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddEditExperienceViewModel @Inject constructor(
    private val repository: AlumniRemoteRepository
) : ViewModel() {
    private val _addEditExperienceState = MutableStateFlow(AddEditExperienceState())
    val addEditExperienceState: StateFlow<AddEditExperienceState> =
        _addEditExperienceState.asStateFlow()

    private val _uiState =
        MutableStateFlow<AddEditExperienceUiState>(AddEditExperienceUiState.Loading)
    val uiState: StateFlow<AddEditExperienceUiState> = _uiState.asStateFlow()

    private var storedExperienceId: String? = null

    fun init(
        experienceId: String?,
        company: String?,
        description: String?,
        endDate: String?,
        position: String?,
        startDate: String?
    ) {
        if (storedExperienceId != null) return

        if (experienceId != null) {
            updateState {
                it.copy(
                    id = experienceId,
                    company = company ?: "",
                    description = description ?: "",
                    endDate = endDate ?: "",
                    position = position ?: "",
                    startDate = startDate ?: "",
                    isEditMode = true
                )
            }

            storedExperienceId = experienceId
        }
        _uiState.value = AddEditExperienceUiState.Success
    }

    fun onEvent(event: AddEditExperienceActions) {
        when (event) {
            is AddEditExperienceActions.onCancel -> event.popBack()
            is AddEditExperienceActions.onCompanyChange -> updateState { it.copy(company = event.company) }
            is AddEditExperienceActions.onDescriptionChange -> updateState { it.copy(description = event.description) }
            is AddEditExperienceActions.onEndDateChange -> updateState { it.copy(endDate = event.endDate) }
            is AddEditExperienceActions.onPositionChange -> updateState { it.copy(position = event.position) }
            is AddEditExperienceActions.onStartDateChange -> updateState { it.copy(startDate = event.startDate) }
            is AddEditExperienceActions.onSaveExperience -> saveExperience(event.popBack)
            is AddEditExperienceActions.onDeleteExperience -> deleteExperience(event.popBack)
        }
    }

    private fun updateState(update: (AddEditExperienceState) -> AddEditExperienceState) {
        _addEditExperienceState.value = update(_addEditExperienceState.value)
    }

    private fun deleteExperience(popBack: () -> Unit) {
        viewModelScope.launch {
            val result = repository.deleteWorkExperience(_addEditExperienceState.value.id!!)
            when (result) {
                is Result.Error<*> -> {
                    _uiState.value = AddEditExperienceUiState.Error(result.message.toString())
                }

                is Result.Success<*> -> {
                    _uiState.value = AddEditExperienceUiState.Success
                    popBack()
                }
            }
        }
    }

    private fun saveExperience(popBack: () -> Unit) {
        _uiState.value = AddEditExperienceUiState.Loading
        viewModelScope.launch {
            var result: Result<*>? = null
            result = if (addEditExperienceState.value.isEditMode) {
                repository.updateWorkExperience(
                    experienceId = _addEditExperienceState.value.id!!,
                    experience = _addEditExperienceState.value.toExperience()
                )
            } else {
                repository.addWorkExperience(
                    experience = _addEditExperienceState.value.toExperience()
                )
            }
            when (result) {
                is Result.Error<*> -> {
                    _uiState.value = AddEditExperienceUiState.Error(result.message.toString())
                    Log.d("AddEditExperienceViewModel", "saveExperience: ${result.message}")
                }

                is Result.Success<*> -> {
                    _uiState.value = AddEditExperienceUiState.Success
                    Log.d("AddEditExperienceViewModel", "saveExperience: ${result.data}")
                    popBack()
                }
            }

        }
    }

}

