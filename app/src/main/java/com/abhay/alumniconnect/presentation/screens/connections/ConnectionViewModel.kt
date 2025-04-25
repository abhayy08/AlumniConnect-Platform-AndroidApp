package com.abhay.alumniconnect.presentation.screens.connections

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.abhay.alumniconnect.data.remote.dto.Connection
import com.abhay.alumniconnect.domain.repository.AlumniRemoteRepository
import com.abhay.alumniconnect.utils.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ConnectionViewModel @Inject constructor(
    private val alumniRemoteRepository: AlumniRemoteRepository
) : ViewModel() {

    private val _connectionsState = MutableStateFlow<List<Connection>>(emptyList())
    val connectionsState: StateFlow<List<Connection>> = _connectionsState.asStateFlow()

    private val _errorState = MutableStateFlow<String?>(null)
    val errorState: StateFlow<String?> = _errorState.asStateFlow()

    fun initialize(userId: String?) {
        viewModelScope.launch {
            when (val result = alumniRemoteRepository.getUserConnectionsByUserId(userId!!)) {
                is Result.Success<*> -> {
                    _connectionsState.update { result.data!! }
                }

                is Result.Error<*> -> {
                    _errorState.update { result.message }
                }
            }
        }
    }

}