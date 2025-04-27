package com.abhay.alumniconnect.presentation.screens.home.create_post

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import coil.util.CoilUtils.result
import com.abhay.alumniconnect.domain.repository.PostsRepository
import com.abhay.alumniconnect.utils.AppUtils
import com.abhay.alumniconnect.utils.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.io.File
import javax.inject.Inject

@HiltViewModel
class CreatePostViewModel @Inject constructor(
    private val postsRepository: PostsRepository
) : ViewModel() {
    private val _createPostState = MutableStateFlow(CreatePostState())
    val createPostState: StateFlow<CreatePostState> = _createPostState.asStateFlow()

    fun onContentChange(content: String) {
        _createPostState.value = _createPostState.value.copy(content = content)
    }

    private var imageUri: Uri? = null

    fun onAddImage(uri: Uri?) {
        imageUri = uri
    }

    fun createPost(popBack: () -> Unit) {
        viewModelScope.launch {
            if(_createPostState.value.content.isEmpty()){
                _createPostState.value = _createPostState.value.copy(error = "Content cannot be empty")
                return@launch
            }

            var imageFile: File? = null
            imageFile = imageUri?.let { AppUtils.uriToFile(it) }

            val result = postsRepository.createPost(_createPostState.value.content, imageFile)
            when(result){
                is Result.Success<*> -> {
                    popBack()
                }
                is Result.Error<*> -> {
                    _createPostState.value = _createPostState.value.copy(error = result.message)
                }
            }
        }
    }

    fun resetError() {
        _createPostState.value = _createPostState.value.copy(error = null)
    }
}

data class CreatePostState(
    val content: String = "",
    val error: String? = null
)