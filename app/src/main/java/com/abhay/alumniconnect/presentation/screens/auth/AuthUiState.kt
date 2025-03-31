package com.abhay.alumniconnect.presentation.screens.auth

import androidx.compose.material3.SnackbarData


data class AuthUiState(
    val email: String = "",
    val password: String = "",
    val confirmPassword: String = "",
    val name: String = "",
    val graduationYear: String = "",
    val currentJob: String = "",
    val university: String = "",
    val degree: String = "",
    val major: String = "",
    val emailError: String? = null,
    val passwordError: String? = null,
    val confirmPasswordError: String? = null,
    val nameError: String? = null,
    val graduationYearError: String? = null,
    val universityError: String? = null,
    val degreeError: String? = null,
    val majorError: String? = null,

    val snackbarMessage: String? = null
)
