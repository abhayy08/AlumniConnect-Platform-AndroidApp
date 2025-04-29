package com.abhay.alumniconnect.presentation.screens.profile

import android.net.Uri
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.abhay.alumniconnect.data.remote.dto.job.Job
import com.abhay.alumniconnect.data.remote.dto.post.Comment
import com.abhay.alumniconnect.data.remote.dto.post.Post
import com.abhay.alumniconnect.domain.repository.AlumniAccountRepository
import com.abhay.alumniconnect.domain.repository.AlumniRemoteRepository
import com.abhay.alumniconnect.domain.repository.JobsRepository
import com.abhay.alumniconnect.domain.repository.PostsRepository
import com.abhay.alumniconnect.presentation.screens.home.HomeUiState
import com.abhay.alumniconnect.utils.AppUtils
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

    private val _commentsState = MutableStateFlow<List<Comment>>(emptyList())
    val commentsState: StateFlow<List<Comment>> = _commentsState.asStateFlow()

    val _uiState: MutableStateFlow<ProfileUiState> = MutableStateFlow(ProfileUiState.Loading)
    val uiState: StateFlow<ProfileUiState> = _uiState.asStateFlow()

    init {
        initializeViewModel()
    }

    fun updateProfileImage(uri: Uri?) {
        if (uri == null) {
            _uiState.update { ProfileUiState.Error(message = "Unable to fetch image") }
            return
        }
        val imageFile = AppUtils.uriToFile(uri = uri)
        if (imageFile == null) {
            _uiState.update { ProfileUiState.Error(message = "Unable to fetch image") }
            return
        }

        viewModelScope.launch {
            val result = alumniRemoteRepository.uploadProfileImage(image = imageFile)
            when (result) {
                is Result.Success<*> -> {
                    _uiState.update { ProfileUiState.Success() }
                }

                is Result.Error<*> -> {
                    _uiState.update {
                        ProfileUiState.Error(
                            message = result.message ?: "An Unknown error occurred"
                        )
                    }
                }
            }
        }
    }

    fun getComments(postId: String) {

        viewModelScope.launch {


            val result =
                postsRepository.getPostComments(postId, 1, 10)
            when (result) {
                is Result.Success<*> -> {
                    _commentsState.update {
                        result.data as List<Comment>
                    }
                }

                is Result.Error<*> -> {
                    _uiState.update { ProfileUiState.Error(message = result.message.toString()) }
                }
            }
        }
    }

    fun commentOnPost(postId: String, comment: String) {
        viewModelScope.launch {
            val result = postsRepository.commentOnPost(postId, comment)
            when (result) {
                is Result.Success<*> -> {
                    _postsState.update {
                        it.map { post ->
                            if (post._id == postId) {
                                post.copy(
                                    commentsCount = post.commentsCount + 1
                                )
                            } else {
                                post
                            }
                        }
                    }
                    getComments(postId)
                }

                is Result.Error<*> -> {
                    _uiState.update { ProfileUiState.Error(message = result.message.toString()) }
                }
            }
        }
    }

    fun likePost(postId: String) {
        viewModelScope.launch {
            val result = postsRepository.likePost(postId)
            when (result) {
                is Result.Success<*> -> {
                    _postsState.update {
                        it.map { post ->
                            if (post._id == postId) {
                                val wasLiked = post.likedByCurrentUser
                                post.copy(
                                    likedByCurrentUser = !wasLiked,
                                    likesCount = if (wasLiked) post.likesCount - 1 else post.likesCount + 1
                                )
                            } else {
                                post
                            }
                        }
                    }
                }

                is Result.Error<*> -> _uiState.update { ProfileUiState.Error(message = result.message.toString()) }
            }
        }
    }

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
                            getPostsByUserId(nonNullResult.data?.id!!)
                        }

                        is Result.Error -> {
                            Log.d("ProfileScreenViewModel", "init: ${nonNullResult.message}")
                            _uiState.update {
                                ProfileUiState.Error(
                                    message = nonNullResult.message
                                        ?: "An Unknown error occurred"
                                )
                            }
                        }
                    }
                }
            }
        }
        viewModelScope.launch {
            when (val result = jobsRepository.getJobsByCurrentUser()) {
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
                        ProfileUiState.Error(
                            message = result.message ?: "An Unknown error occurred"
                        )
                    }
                }
            }
        }
    }

    fun getPostsByUserId(userId: String) {
        viewModelScope.launch {
            when (val result = postsRepository.getPostsById(userId)) {
                is Result.Success<*> -> {
                    _postsState.update { result.data as List<Post> }
                }

                is Result.Error<*> -> {
                    Log.d("ProfileScreenViewModel", "initializeViewModel: ${result.message}")
                    _uiState.update {
                        ProfileUiState.Error(
                            message = result.message ?: "An Unknown error occurred"
                        )
                    }
                }
            }

        }
    }
}