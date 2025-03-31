package com.abhay.alumniconnect.presentation.screens.profile.add_edit_work_experience

sealed class AddEditExperienceUiState {

    data object Success : AddEditExperienceUiState()
    data class Error(val message: String) : AddEditExperienceUiState()
    data object Loading : AddEditExperienceUiState()

}