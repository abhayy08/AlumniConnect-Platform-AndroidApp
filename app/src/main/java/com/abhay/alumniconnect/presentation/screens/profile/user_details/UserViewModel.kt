package com.abhay.alumniconnect.presentation.screens.profile.user_details

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.abhay.alumniconnect.data.remote.dto.job.Job
import com.abhay.alumniconnect.domain.model.User
import com.abhay.alumniconnect.domain.repository.AlumniRemoteRepository
import com.abhay.alumniconnect.domain.repository.JobsRepository
import com.abhay.alumniconnect.presentation.screens.profile.ProfileState
import com.abhay.alumniconnect.presentation.screens.profile.ProfileUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import com.abhay.alumniconnect.utils.Result
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class UserProfileViewModel @Inject constructor(
    private val alumniRemoteRepository: AlumniRemoteRepository,
    private val jobsRepository: JobsRepository
) : ViewModel() {

    private val _profileState = MutableStateFlow(ProfileState())
    val profileState: StateFlow<ProfileState> = _profileState.asStateFlow()

    private val _jobsState = MutableStateFlow<List<Job>>(emptyList())
    val jobsState = _jobsState.asStateFlow()

    private val _uiState = MutableStateFlow<ProfileUiState>(ProfileUiState.Loading)
    val uiState: StateFlow<ProfileUiState> = _uiState.asStateFlow()

    fun loadUserProfile(userId: String) {
        viewModelScope.launch {
            _uiState.value = ProfileUiState.Loading
            when(val result = alumniRemoteRepository.getUserById(userId)) {
                is Result.Success<*> -> {
                    _profileState.value = ProfileState(result.data as User)
                    _uiState.value = ProfileUiState.Success()
                    Log.d("UserProfileViewModel", "loadUserProfile User: ${result.data}")
                }
                is Result.Error<*> -> {
                    _uiState.value = ProfileUiState.Error(result.message ?: "An Unknown error occurred")
                }
            }
        }
        viewModelScope.launch {
            when(val result = jobsRepository.getJobsByUserId(userId)) {
                is Result.Success<*> -> {
                    _jobsState.value = result.data as List<Job>
                    Log.d("UserProfileViewModel", "loadUserProfile Jobs: ${result.data}")
                }
                is Result.Error<*> -> {
                    _uiState.value = ProfileUiState.Error(result.message ?: "An Unknown error occurred")
                }
            }
        }
    }

    fun addConnection(userId: String) {
        viewModelScope.launch {
            when(val result = alumniRemoteRepository.connectUser(userId)){
                is Result.Success<*> -> {
                    _uiState.update { ProfileUiState.Success(message = result.data) }
                }
                is Result.Error<*> -> {
                    _uiState.update { ProfileUiState.Error(message = result.message!!) }
                }
            }
        }
    }
}
