package com.abhay.alumniconnect.presentation.screens.profile.editprofilescreen

sealed class EditProfileUiState {
    object Loading : EditProfileUiState()
    object Success : EditProfileUiState()
    data class Error(val message: String) : EditProfileUiState()
}