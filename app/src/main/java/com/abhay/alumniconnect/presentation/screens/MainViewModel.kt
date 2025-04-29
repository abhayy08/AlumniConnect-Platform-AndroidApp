package com.abhay.alumniconnect.presentation.screens

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.abhay.alumniconnect.data.repository.SessionManager
import com.abhay.alumniconnect.domain.model.User
import com.abhay.alumniconnect.domain.repository.AlumniAccountRepository
import com.abhay.alumniconnect.domain.repository.AlumniRemoteRepository
import com.abhay.alumniconnect.domain.repository.JobsRepository
import com.abhay.alumniconnect.utils.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val remoteRepository: AlumniRemoteRepository,
    private val authRepository: AlumniAccountRepository
) : ViewModel() {
    private val _currentUser = MutableStateFlow<User?>(null)
    val currentUser: StateFlow<User?> = _currentUser.asStateFlow()

    init {
        viewModelScope.launch {
            remoteRepository.getOrLoadCurrentUserFlow().collect {result ->
                if (result is Result.Success) {
                    _currentUser.update { result.data }
                }
            }
        }
    }

    fun onLogout(onLogout: () -> Unit) {
        viewModelScope.launch {
            authRepository.logoutUser()
            onLogout()
        }
    }
}