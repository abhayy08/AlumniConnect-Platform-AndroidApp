package com.abhay.alumniconnect.presentation.screens.main

import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.abhay.alumniconnect.presentation.components.CommentItem
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

    var commentTextFieldValue by remember { mutableStateOf("") }

    var openBottomSheet by rememberSaveable { mutableStateOf(false) }
    val scope = rememberCoroutineScope()
    val bottomSheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

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
            shape = MaterialTheme.shapes.medium
        ) {

            Box(modifier = Modifier.fillMaxSize()) {

                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(bottom = 72.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    if (commentsState.isLoadingComments) {
                        item {
                            CircularProgressIndicator(modifier = Modifier.padding(16.dp))
                        }
                    } else {
                        if (commentsState.commentsOnPost.isNotEmpty()) {
                            items(commentsState.commentsOnPost, key = { it._id }) { comment ->
                                CommentItem(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(16.dp),
                                    comment = comment
                                )
                                HorizontalDivider(modifier = Modifier.fillMaxWidth(0.9f))
                            }
                        } else {
                            item {
                                Box(
                                    modifier = Modifier.fillMaxSize(),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text("No comments yet")
                                }
                            }
                        }
                    }
                }

                // Comment Input Box
                Row(
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .fillMaxWidth()
                        .padding(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(40.dp)
                            .clip(CircleShape)
                            .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.2f)),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = postsState.posts[0].author.name.first().toString(),
                            style = MaterialTheme.typography.titleMedium,
                            color = MaterialTheme.colorScheme.primary
                        )
                    }

                    BasicTextField(
                        value = commentTextFieldValue,
                        onValueChange = { commentTextFieldValue = it },
                        modifier = Modifier
                            .weight(1f)
                            .padding(start = 8.dp),
                        textStyle = MaterialTheme.typography.bodyMedium.copy(color = MaterialTheme.colorScheme.onSurface),
                        maxLines = 1,
                        decorationBox = {
                            Box {
                                if (commentTextFieldValue.isEmpty()) {
                                    Text(
                                        "Add a comment....",
                                        style = MaterialTheme.typography.bodyMedium.copy(
                                            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                                        )
                                    )
                                }
                                it()
                            }
                        },
                        cursorBrush = SolidColor(MaterialTheme.colorScheme.primary)
                    )

                    TextButton(
                        onClick = {
                            onEvent(HomeUiEvents.CommentOnPost(selectedPostId!!,commentTextFieldValue))
                            commentTextFieldValue = ""
                        },
                        modifier = Modifier.padding(start = 8.dp)
                    ) {
                        Text("Post")
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
            commentsState = CommentsState(
                commentsOnPost = dummyPosts[0].comments!!
            ),
            onEvent = {},
            showSnackbar = {}
        )
    }
}