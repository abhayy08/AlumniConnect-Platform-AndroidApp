package com.abhay.alumniconnect.presentation.screens.profile

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.abhay.alumniconnect.data.remote.dto.job.Job
import com.abhay.alumniconnect.data.remote.dto.post.Post
import com.abhay.alumniconnect.domain.model.User
import com.abhay.alumniconnect.domain.repository.AlumniAccountRepository
import com.abhay.alumniconnect.domain.repository.AlumniRemoteRepository
import com.abhay.alumniconnect.domain.repository.JobsRepository
import com.abhay.alumniconnect.domain.repository.PostsRepository
import com.abhay.alumniconnect.utils.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val alumniRemoteRepository: AlumniRemoteRepository,
    private val jobsRepository: JobsRepository,
    private val postsRepository: PostsRepository,
    private val authRepository: AlumniAccountRepository
) : ViewModel() {

    private val _profileState = MutableStateFlow(ProfileState())
    val profileState: StateFlow<ProfileState> = _profileState.asStateFlow()

    private val _jobsState = MutableStateFlow<List<Job>>(emptyList())
    val jobsState = _jobsState.asStateFlow()

    private val _postsState = MutableStateFlow<List<Post>>(emptyList())
    val postsState: StateFlow<List<Post>> = _postsState.asStateFlow()

    val _uiState: MutableStateFlow<ProfileUiState> = MutableStateFlow(ProfileUiState.Loading)
    val uiState: StateFlow<ProfileUiState> = _uiState.asStateFlow()

    init {
        initializeViewModel()
        getPostsByUser()
    }

    fun loadCurrentUserFromMainViewModel(user: User?) {
        _profileState.update { it.copy(user = user) }
    }

    private fun getPostsByUser() {
        viewModelScope.launch {
//            when(val result = postsRepository.getPostsByCurrentUser()) {
//
//            }
        }
    }

//    private fun getJobsByUser() {
//        viewModelScope.launch {
//                            when(val result = jobsRepository.getJobsByCurrentUser()){
//                                is Result.Success<*> -> {
//                                    _jobsState.update { result.data as List<Job> }
//                                    Log.d("ProfileScreenViewModel", "initializeViewModel: ${result.data}")
//                                    _uiState.update {
//                                        ProfileUiState.Success()
//                                    }
//                                }
//                                is Result.Error<*> -> {
//                                    Log.d("ProfileScreenViewModel", "initializeViewModel: ${result.message}")
//                                    _uiState.update {
//                                        ProfileUiState.Error(message = result.message ?: "An Unknown error occurred")
//                    }
//                }
//            }
//        }
//    }

    private fun initializeViewModel() {
        viewModelScope.launch {
            alumniRemoteRepository.getOrLoadCurrentUserFlow().collect { result ->
                result?.let { nonNullResult ->
                    when (nonNullResult) {
                        is Result.Success -> {
                            _profileState.update {
                                it.copy(
                                    user = nonNullResult.data
                                )
                            }
                        }

                        is Result.Error -> {
                            Log.d("ProfileScreenViewModel", "init: ${nonNullResult.message}")
                            _uiState.update {
                                ProfileUiState.Error(message = nonNullResult.message ?: "An Unknown error occurred")
                            }
                        }
                    }
                }
            }
        }
        viewModelScope.launch {
            when(val result = jobsRepository.getJobsByCurrentUser()){
                is Result.Success<*> -> {
                    _jobsState.update { result.data as List<Job> }
                    Log.d("ProfileScreenViewModel", "initializeViewModel: ${result.data}")
                    _uiState.update {
                        ProfileUiState.Success()
                    }
                }
                is Result.Error<*> -> {
                    Log.d("ProfileScreenViewModel", "initializeViewModel: ${result.message}")
                    _uiState.update {
                        ProfileUiState.Error(message = result.message ?: "An Unknown error occurred")
                    }
                }
            }
        }
    }
}