package com.abhay.alumniconnect.presentation.screens.profile.pages

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.abhay.alumniconnect.data.remote.dto.post.Comment
import com.abhay.alumniconnect.data.remote.dto.post.Post
import com.abhay.alumniconnect.domain.model.User
import com.abhay.alumniconnect.presentation.components.CommentItem
import com.abhay.alumniconnect.presentation.components.PostItem
import com.abhay.alumniconnect.presentation.screens.home.CommentInputBox
import com.abhay.alumniconnect.presentation.screens.home.CommentsSection
import com.abhay.alumniconnect.presentation.screens.home.CommentsState
import com.abhay.alumniconnect.presentation.screens.home.HomeUiEvents
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserPostsPage(
    currentUser: User? = null,
    posts: List<Post>,
    onUserClick: (String) -> Unit,
    onLikeClick: (String) -> Unit,
    onGetCommentsClick: (String) -> Unit,
    commentsState: List<Comment>,
    onPostCommentClick: (String, String) -> Unit
) {

    var selectedPostId by rememberSaveable { mutableStateOf<String?>(null) }
    var openBottomSheet by rememberSaveable { mutableStateOf(false) }
    val scope = rememberCoroutineScope()
    val bottomSheetState = rememberModalBottomSheetState(skipPartiallyExpanded = false)

    var commentTextFieldValue by remember { mutableStateOf("") }

    if (posts.isNotEmpty()) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(4.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            items(items = posts, key = { it._id }) { post ->
                PostItem(
                    modifier = Modifier.padding(6.dp),
                    post = post,
                    onUserClick = {
                        onUserClick(post.author._id)
                    },
                    onLikeClick = { onLikeClick(post._id) },
                    onCommentClick = {
                        selectedPostId = post._id
                        openBottomSheet = true
                        onGetCommentsClick(post._id)
                        scope.launch { bottomSheetState.show() }
                    },
                )
            }
        }

        if (openBottomSheet && selectedPostId != null) {
            ModalBottomSheet(
                onDismissRequest = { openBottomSheet = false },
                sheetState = bottomSheetState,
                shape = MaterialTheme.shapes.medium
            ) {
                CommentsSectionInProfile(
                    commentsState = commentsState,
                    commentTextFieldValue = commentTextFieldValue,
                    onCommentTextChange = { commentTextFieldValue = it },
                    onPostComment = {
                        onPostCommentClick(selectedPostId!!, commentTextFieldValue)
                        commentTextFieldValue = ""
                    },
                    currentUser = currentUser
                )
            }
        }
    } else {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp), contentAlignment = Alignment.Center
        ) {
            Text(
                text = "No posts yet. Updates and content shared by you will appear here.",
                style = MaterialTheme.typography.bodyLarge,
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
            )
        }
    }
}

@Composable
fun CommentsSectionInProfile(
    commentsState: List<Comment>,
    commentTextFieldValue: String,
    onCommentTextChange: (String) -> Unit,
    onPostComment: () -> Unit,
    currentUser: User?
) {
    Box(modifier = Modifier.fillMaxSize()) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 72.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            if (commentsState.isNotEmpty()) {
                items(commentsState, key = { it._id }) { comment ->
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

        // Comment Input Box
        CommentInputBox(
            commentTextFieldValue = commentTextFieldValue,
            onCommentTextChange = onCommentTextChange,
            onPostComment = onPostComment,
            currentUser = currentUser
        )
    }
}