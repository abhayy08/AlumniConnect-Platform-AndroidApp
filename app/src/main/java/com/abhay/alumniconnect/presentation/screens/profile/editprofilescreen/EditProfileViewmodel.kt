package com.abhay.alumniconnect.presentation.screens.profile.editprofilescreen

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.abhay.alumniconnect.domain.model.User
import com.abhay.alumniconnect.domain.repository.AlumniRemoteRepository
import com.abhay.alumniconnect.utils.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.math.log

@HiltViewModel
class EditProfileViewModel @Inject constructor(
    private val alumniRemoteRepository: AlumniRemoteRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<EditProfileUiState>(EditProfileUiState.Loading)
    val uiState: StateFlow<EditProfileUiState> = _uiState.asStateFlow()

    private val _editProfileState = MutableStateFlow(EditProfileState())
    val editProfileState: StateFlow<EditProfileState> = _editProfileState.asStateFlow()

    private var fetchedUser: User? = null

    init {
        viewModelScope.launch {
            _uiState.value = EditProfileUiState.Loading
            when (val result = alumniRemoteRepository.getCurrentUser()) {
                is Result.Success -> {
                    fetchedUser = result.data!!

                    _editProfileState.value = EditProfileState(
                        bio = fetchedUser!!.bio,
                        linkedInProfile = fetchedUser!!.linkedInProfile,
                        skills = fetchedUser!!.skills,
                        interests = fetchedUser!!.interests,
                        achievements = fetchedUser!!.achievements
                    )
                    Log.d("EditProfileViewModel", "Fetched user: $fetchedUser")
                    Log.d("EditProfileViewModel", "Edit Profile State: ${_editProfileState.value}")
                    _uiState.value = EditProfileUiState.Success
                }

                is Result.Error -> {
                    _uiState.value = EditProfileUiState.Error(result.message.toString())
                }
            }
        }
    }

    fun onEvent(event: EditProfileActions) {
        when (event) {
            is EditProfileActions.UpdateBio -> updateState { it.copy(bio = event.bio) }
            is EditProfileActions.UpdateLinkedInProfile -> updateState { it.copy(linkedInProfile = event.linkedInProfile) }
            is EditProfileActions.UpdateSkills -> updateState { it.copy(skills = event.skills) }
            is EditProfileActions.UpdateInterests -> updateState { it.copy(interests = event.interests) }
            is EditProfileActions.AddAchievement -> updateState {
                it.copy(achievements = it.achievements + event.achievement)
            }

            is EditProfileActions.RemoveAchievement -> updateState {
                it.copy(achievements = it.achievements.filter { achievement -> achievement != event.achievement })
            }

            is EditProfileActions.SaveProfile -> saveUserProfile(event.popBack)
        }
    }

    private fun updateState(update: (EditProfileState) -> EditProfileState) {
        _editProfileState.value = update(_editProfileState.value)
    }

    private fun saveUserProfile(popBack: () -> Unit) {
        viewModelScope.launch {
            _uiState.value = EditProfileUiState.Loading
            val currentState = _editProfileState.value
            val updatedUser = fetchedUser!!.copy(
                bio = currentState.bio,
                linkedInProfile = currentState.linkedInProfile,
                skills = currentState.skills,
                interests = currentState.interests,
                achievements = currentState.achievements
            )

            when (val result = alumniRemoteRepository.updateUser(updatedUser)) {
                is Result.Error<*> -> {
                    _uiState.value = EditProfileUiState.Error(result.message.toString())
                }
                is Result.Success<*> -> {
                    _uiState.value = EditProfileUiState.Success
                    popBack()
                }
            }

            Log.d("EditProfileViewModel", "Updated User: $updatedUser")

        }
    }
}

data class EditProfileState(
    val bio: String = "",
    val linkedInProfile: String = "",
    val skills: List<String> = emptyList(),
    val interests: List<String> = emptyList(),
    val achievements: List<String> = emptyList()
)


