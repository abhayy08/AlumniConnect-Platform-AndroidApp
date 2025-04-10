package com.abhay.alumniconnect.presentation.screens.main

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.abhay.alumniconnect.presentation.components.PostItem
import com.abhay.alumniconnect.presentation.dummyPosts
import com.example.compose.AlumniConnectTheme
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    uiState: HomeUiState,
    postsState: PostsState,
    commentsState: CommentsState,
    onEvent: (HomeUiEvents) -> Unit,
    showSnackbar: (String) -> Unit
) {

    LaunchedEffect(uiState) {
        when (uiState) {
            is HomeUiState.Error -> {
                if (uiState.error != null) {
                    showSnackbar(uiState.error)
                }
            }

            else -> {}
        }
    }

    var openBottomSheet by rememberSaveable { mutableStateOf(false) }
    val scope = rememberCoroutineScope()
    val bottomSheetState = rememberModalBottomSheetState(skipPartiallyExpanded = false)

    var selectedPostId by rememberSaveable { mutableStateOf<String?>(null) }


    if (uiState != HomeUiState.Loading) {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(4.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                items(items = postsState.posts, key = { it._id }) { post ->
                    PostItem(
                        modifier = Modifier.padding(6.dp),
                        post = post,
                        onUserClick = { },
                        onLikeClick = { onEvent(HomeUiEvents.LikePost(post._id)) },
                        onCommentClick = {
                            selectedPostId = post._id
                            openBottomSheet = true
                            onEvent(HomeUiEvents.GetComments(post._id))
                            scope.launch { bottomSheetState.show() }
                        }

                    )
                }

                item {
                    if (postsState.isLoadingPosts) {
                        CircularProgressIndicator()
                    }
                }
            }
        }
    } else {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
    }

    if (openBottomSheet && selectedPostId != null) {
        ModalBottomSheet(
            onDismissRequest = { openBottomSheet = false },
            sheetState = bottomSheetState,
        ) {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                if(commentsState.isLoadingComments){
                    item {
                        CircularProgressIndicator()
                    }
                }else {
                    items(commentsState.commentsOnPost, key = { it._id }) { comment ->
                        Text(comment.comment)
                    }
                }
            }
        }
    }

}

@Preview(
    showBackground = true,
)
@Composable
private fun HomeScreenPreview() {
    AlumniConnectTheme {
        HomeScreen(
            uiState = HomeUiState.Success,
            postsState = PostsState(posts = dummyPosts),
            commentsState = CommentsState(),
            onEvent = {},
            showSnackbar = {}
        )
    }
}