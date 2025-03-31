package com.abhay.alumniconnect.presentation.screens.profile

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.abhay.alumniconnect.domain.model.User
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
class ProfileScreenViewModel @Inject constructor(
    private val alumniRemoteRepository: AlumniRemoteRepository
) : ViewModel() {

    private val _profileUiState = MutableStateFlow(ProfileUiState())
    val profileUiState: StateFlow<ProfileUiState> = _profileUiState.asStateFlow()

    init {
        viewModelScope.launch {
            alumniRemoteRepository.getOrLoadCurrentUserFlow().collect { result ->
                result?.let { nonNullResult ->
                    when (nonNullResult) {
                        is Result.Success -> {
                            _profileUiState.update {
                                it.copy(
                                    user = nonNullResult.data,
                                    error = null
                                )
                            }
                        }

                        is Result.Error -> {
                            Log.d("ProfileScreenViewModel", "init: ${nonNullResult.message}")
                            _profileUiState.update { it.copy(error = nonNullResult.message) }
                        }
                    }
                }
            }
        }
    }

}

data class ProfileUiState(
    val user: User? = null,
    val error: String? = null
)