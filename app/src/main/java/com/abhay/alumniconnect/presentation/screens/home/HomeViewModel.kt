package com.abhay.alumniconnect.presentation.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.abhay.alumniconnect.data.remote.dto.post.Comment
import com.abhay.alumniconnect.data.remote.dto.post.Post
import com.abhay.alumniconnect.domain.repository.PostsRepository
import com.abhay.alumniconnect.utils.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

// Handles posts
@HiltViewModel
class HomeViewModel @Inject constructor(
    private val postsRepository: PostsRepository
) : ViewModel() {

    private val _postsState = MutableStateFlow(PostsState())
    val postsState: StateFlow<PostsState> = _postsState.asStateFlow()

    private val _commentsState = MutableStateFlow(CommentsState())
    val commentsState: StateFlow<CommentsState> = _commentsState.asStateFlow()

    private val _uiState = MutableStateFlow<HomeUiState>(HomeUiState.Loading)
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    private var pageForPosts = 1
    private var limitForPosts = 30
    private var pageForComments = 1
    private var limitForComments = 15

    init {
        getPosts()
    }

    fun onEvent(events: HomeUiEvents) {
        when (events) {
            is HomeUiEvents.GetMorePost -> getPosts(isLoadingMore = true)
            is HomeUiEvents.GetComments -> getComments(events.postId)
            is HomeUiEvents.GetMoreComments -> getComments(events.postId, true)
            is HomeUiEvents.Refresh -> {
                //TODO
            }

            is HomeUiEvents.LikePost -> likePost(events.postId)
            is HomeUiEvents.CommentOnPost -> commentOnPost(events.postId, events.content)
        }
    }

    private fun commentOnPost(postId: String, comment: String) {
        viewModelScope.launch {
            val result = postsRepository.commentOnPost(postId, comment)
            when (result) {
                is Result.Success<*> -> {
                    _postsState.update {
                        it.copy(
                            posts = it.posts.map { post ->
                                if(post._id == postId) {
                                    post.copy(
                                        commentsCount = post.commentsCount + 1
                                    )
                                }else {
                                    post
                                }
                            }
                        )
                    }
                    getComments(postId)
                }
                is Result.Error<*> -> {
                    _uiState.update { HomeUiState.Error(error = result.message) }
                }
            }
        }
    }

    private fun likePost(postId: String) {
        viewModelScope.launch {
            val result = postsRepository.likePost(postId)
            when (result) {
                is Result.Success<*> -> {
                    _postsState.update {
                        it.copy(
                            posts = it.posts.map { post ->
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
                        )
                    }
                }

                is Result.Error<*> -> _uiState.update { HomeUiState.Error(error = result.message) }
            }
        }
    }


    private fun getComments(postId: String, getMoreComments: Boolean = false) {
        _commentsState.update {
            it.copy(
                isLoadingComments = true,
                commentsOnPost = if (!getMoreComments) emptyList() else it.commentsOnPost
            )
        }

        viewModelScope.launch {
            if (!getMoreComments) {
                pageForComments = 1
            }

            val result =
                postsRepository.getPostComments(postId, pageForComments++, limitForComments)
            when (result) {
                is Result.Success<*> -> {
                    _commentsState.update {
                        it.copy(
                            commentsOnPost = if (getMoreComments) it.commentsOnPost + result.data!! else result.data!!,
                            isLoadingComments = false
                        )
                    }
                }

                is Result.Error<*> -> {
                    _uiState.update { HomeUiState.Error(error = result.message) }
                    _commentsState.update { it.copy(isLoadingComments = false) }
                }
            }
        }
    }


    private fun getPosts(isLoadingMore: Boolean = false) {
        if(isLoadingMore){
            _postsState.update { it.copy(isLoadingMore = true) }
        }else {
            _uiState.update { HomeUiState.Loading }
        }
        _postsState.update { it.copy(isLoadingPosts = true) }
        viewModelScope.launch {
            val result = postsRepository.getPosts(pageForPosts++, limitForPosts)
            when (result) {
                is Result.Success<*> -> {
                    _postsState.update {
                        it.copy(
                            posts = it.posts + (result.data!!),
                            isLoadingPosts = false
                        )
                    }
                    _uiState.update { HomeUiState.Success }
                    _postsState.update { it.copy(isLoadingMore = false) }
                }

                is Result.Error<*> -> {
                    _postsState.update { it.copy(isLoadingPosts = false) }
                    _uiState.update { HomeUiState.Error(error = result.message) }
                }
            }
        }
    }

}

sealed class HomeUiEvents {
    object GetMorePost : HomeUiEvents()
    object Refresh : HomeUiEvents()
    data class GetComments(val postId: String) : HomeUiEvents()
    data class GetMoreComments(val postId: String) : HomeUiEvents()
    data class LikePost(val postId: String) : HomeUiEvents()

    data class CommentOnPost(val postId: String, val content: String): HomeUiEvents()
}

sealed class HomeUiState {
    object Loading : HomeUiState()
    object Success : HomeUiState()
    data class Error(val error: String? = null) : HomeUiState()
}

data class CommentsState(
    val commentsOnPost: List<Comment> = emptyList(),
    val isLoadingComments: Boolean = false,
)

data class PostsState(
    val posts: List<Post> = emptyList(),
    val isLoadingPosts: Boolean = false,
    val isLoadingMore: Boolean = false,
)