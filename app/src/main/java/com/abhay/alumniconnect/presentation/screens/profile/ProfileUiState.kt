package com.abhay.alumniconnect.presentation.screens.profile

sealed class ProfileUiState {
    object Loading : ProfileUiState()
    data class Success(val message: String? = null) : ProfileUiState()
    data class Error(val message: String) : ProfileUiState()
}
