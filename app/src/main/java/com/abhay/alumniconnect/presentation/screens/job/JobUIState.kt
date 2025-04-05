package com.abhay.alumniconnect.presentation.screens.job

sealed class JobUIState {
    data class Success(val message: String? = null) : JobUIState()
    data class Error(val message: String) : JobUIState()
    data object Loading : JobUIState()
}