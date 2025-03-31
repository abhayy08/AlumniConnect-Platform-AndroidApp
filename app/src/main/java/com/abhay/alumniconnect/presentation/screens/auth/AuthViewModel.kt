package com.abhay.alumniconnect.presentation.screens.auth

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.abhay.alumniconnect.data.repository.SessionManager
import com.abhay.alumniconnect.domain.repository.AlumniAccountRepository
import com.abhay.alumniconnect.presentation.navigation.routes.Route
import com.abhay.alumniconnect.utils.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val remoteRepository: AlumniAccountRepository,
    private val sessionManager: SessionManager
) : ViewModel() {

    private val _uiState = MutableStateFlow(AuthUiState())
    val authUiState: StateFlow<AuthUiState> = _uiState.asStateFlow()

    private val _isLoggedIn = MutableStateFlow(false)
    val isLoggedIn: StateFlow<Boolean> = _isLoggedIn.asStateFlow()

    init {
        viewModelScope.launch {
            if (!sessionManager.getUserToken().isNullOrEmpty()) {
                _isLoggedIn.value = true
            }
        }
    }

    fun onEvent(event: AuthUiActions) {
        when (event) {
            is AuthUiActions.onEmailChange -> updateField {
                it.copy(
                    email = event.email, emailError = null
                )
            }

            is AuthUiActions.onPasswordChange -> updateField {
                it.copy(
                    password = event.password, passwordError = null
                )
            }

            is AuthUiActions.onConfirmPasswordChange -> updateField {
                it.copy(
                    confirmPassword = event.confirmPassword, confirmPasswordError = null
                )
            }

            is AuthUiActions.onNameChange -> updateField {
                it.copy(
                    name = event.name, nameError = null
                )
            }

            is AuthUiActions.onGraduationYearChange -> updateField {
                it.copy(
                    graduationYear = event.graduationYear, graduationYearError = null
                )
            }

            is AuthUiActions.onCurrentJobChange -> updateField { it.copy(currentJob = event.currentJob) }
            is AuthUiActions.onUniversityChange -> updateField {
                it.copy(
                    university = event.university, universityError = null
                )
            }

            is AuthUiActions.onDegreeChange -> updateField {
                it.copy(
                    degree = event.degree, degreeError = null
                )
            }

            is AuthUiActions.onMajorChange -> updateField {
                it.copy(
                    major = event.major, majorError = null
                )
            }

            is AuthUiActions.Login -> login(event.openAndPopUp)
            is AuthUiActions.SignUp -> signUp(event.openAndPopUp)
            is AuthUiActions.ResetState -> resetState()
            is AuthUiActions.ClearSnackBarMessage -> updateField { it.copy(snackbarMessage = null) }
        }
    }

    private fun updateField(update: (AuthUiState) -> AuthUiState) {
        _uiState.value = update(_uiState.value)
    }

    private fun resetState() {
        _uiState.value = AuthUiState()
    }

    private fun login(openAndPopUp: (navigateTo: Any, popUp: Any) -> Unit) {

        viewModelScope.launch {

            if (_uiState.value.email.isEmpty()) {
                _uiState.value = _uiState.value.copy(emailError = "Email cannot be empty")
                return@launch
            } else if (_uiState.value.password.isEmpty()) {
                _uiState.value = _uiState.value.copy(passwordError = "Password cannot be empty")
                return@launch
            }

            val result = remoteRepository.loginUser(
                email = authUiState.value.email, password = authUiState.value.password
            )
            when (result) {
                is Result.Success -> {
                    Log.d(TAG, "logged In Successfully")
                    updateField { it.copy(snackbarMessage = "Login Successful") }
                    openAndPopUp(Route.MainRoute, Route.AuthRoute)
                }

                is Result.Error -> {
                    Log.d(TAG, "Error: ${result.message}")
                    updateField { it.copy(snackbarMessage = "An error occurred: ${result.message}") }
                }
            }
        }
    }

    private fun signUp(openAndPopUp: (navigateTo: Any, popUp: Any) -> Unit) {
        viewModelScope.launch {
            if (!validateFields()) return@launch
            val result = remoteRepository.registerUser(
                email = authUiState.value.email,
                password = authUiState.value.password,
                name = authUiState.value.name,
                graduationYear = authUiState.value.graduationYear.toInt(),
                currentJob = if (authUiState.value.currentJob.isEmpty()) null else authUiState.value.currentJob,
                university = authUiState.value.university,
                degree = authUiState.value.degree,
                major = authUiState.value.major

            )

            when (result) {
                is Result.Success -> {
                    Log.d(TAG, "logged In Successfully")
                    updateField { it.copy(snackbarMessage = "SignUp Successful") }
                    openAndPopUp(Route.MainRoute, Route.AuthRoute)
                }

                is Result.Error -> {
                    Log.d(TAG, "Error: ${result.message}")
                    updateField { it.copy(snackbarMessage = "An error occurred: ${result.message}") }
                }
            }
        }
    }

    private fun validateFields(): Boolean {
        val fieldErrors = mapOf(
            "email" to _uiState.value.email.isBlank(),
            "password" to _uiState.value.password.isBlank(),
            "confirmPassword" to (_uiState.value.password != _uiState.value.confirmPassword),
            "name" to _uiState.value.name.isBlank(),
            "graduationYear" to _uiState.value.graduationYear.isBlank(),
            "university" to _uiState.value.university.isBlank(),
            "degree" to _uiState.value.degree.isBlank(),
            "major" to _uiState.value.major.isBlank()
        )

        var isValid = true
        fieldErrors.forEach { (field, hasError) ->
            if (hasError) {
                isValid = false
                _uiState.value = when (field) {
                    "email" -> _uiState.value.copy(emailError = "Email cannot be empty")
                    "password" -> _uiState.value.copy(passwordError = "Password cannot be empty")
                    "confirmPassword" -> _uiState.value.copy(confirmPasswordError = "Passwords do not match")
                    "name" -> _uiState.value.copy(nameError = "Name cannot be empty")
                    "graduationYear" -> _uiState.value.copy(graduationYearError = "Graduation year cannot be empty")
                    "university" -> _uiState.value.copy(universityError = "University cannot be empty")
                    "degree" -> _uiState.value.copy(degreeError = "Degree cannot be empty")
                    "major" -> _uiState.value.copy(majorError = "Major cannot be empty")
                    else -> _uiState.value
                }
            }
        }
        return isValid
    }

}

private const val TAG = "AuthViewModel"
