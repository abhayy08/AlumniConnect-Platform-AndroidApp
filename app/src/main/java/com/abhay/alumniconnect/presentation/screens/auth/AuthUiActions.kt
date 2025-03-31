package com.abhay.alumniconnect.presentation.screens.auth

sealed class AuthUiActions {
    data class onEmailChange(val email: String): AuthUiActions()
    data class onPasswordChange(val password: String): AuthUiActions()
    data class onConfirmPasswordChange(val confirmPassword: String): AuthUiActions()
    data class onNameChange(val name: String): AuthUiActions()
    data class onGraduationYearChange(val graduationYear: String): AuthUiActions()
    data class onCurrentJobChange(val currentJob: String): AuthUiActions()
    data class onUniversityChange(val university: String): AuthUiActions()
    data class onDegreeChange(val degree: String): AuthUiActions()
    data class onMajorChange(val major: String): AuthUiActions()

    data object ResetState: AuthUiActions()

    data class Login(val openAndPopUp: (navigateTo: Any, popUp: Any) -> Unit): AuthUiActions()
    data class SignUp(val openAndPopUp: (navigateTo: Any, popUp: Any) -> Unit): AuthUiActions()
    object ClearSnackBarMessage : AuthUiActions()
}