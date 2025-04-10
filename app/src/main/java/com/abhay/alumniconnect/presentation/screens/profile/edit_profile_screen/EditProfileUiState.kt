package com.abhay.alumniconnect.presentation.screens.profile.edit_profile_screen

sealed class EditProfileUiState {
    object Loading : EditProfileUiState()
    object Success : EditProfileUiState()
    data class Error(val message: String) : EditProfileUiState()
}