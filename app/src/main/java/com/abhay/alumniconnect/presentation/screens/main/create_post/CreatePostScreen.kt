package com.abhay.alumniconnect.presentation.screens.main.create_post

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Image
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.abhay.alumniconnect.domain.model.User
import com.abhay.alumniconnect.presentation.components.LinkifiedText
import com.abhay.alumniconnect.presentation.dummyUser
import com.example.compose.AlumniConnectTheme
import java.util.regex.Pattern

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreatePostScreen(
    currentUser: User?,
    postState: CreatePostState,
    onPostContentChange: (String) -> Unit = {},
    onPostSubmit: () -> Unit = {},
    onNavigateBack: () -> Unit = {},
    onAddImage: () -> Unit = {},
    onAddLink: () -> Unit = {},
    resetError: () -> Unit = {},
    showSnackbar: (String) -> Unit = {}
) {
    val focusRequester = remember { FocusRequester() }
    val scrollState = rememberScrollState()

    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
    }

    LaunchedEffect(postState.error) {
        if(postState.error != null) {
            showSnackbar(postState.error)
            resetError()
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Create Post") }, navigationIcon = {
                IconButton(onClick = onNavigateBack) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack, contentDescription = "Back"
                    )
                }
            }, actions = {
                Button(
                    onClick = onPostSubmit,
                    enabled = postState.content.isNotBlank(),
                    modifier = Modifier.padding(end = 16.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Send,
                        contentDescription = "Post",
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Post")
                }
            })
        }) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .verticalScroll(scrollState)
        ) {
            // User info
            currentUser?.let { user ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // User avatar
                    Box(
                        modifier = Modifier
                            .size(50.dp)
                            .clip(CircleShape)
                            .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.2f)),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = user.name.first().toString(),
                            style = MaterialTheme.typography.titleLarge,
                            color = MaterialTheme.colorScheme.primary
                        )
                    }

                    Spacer(modifier = Modifier.width(12.dp))

                    Column {
                        Text(
                            text = user.name,
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.SemiBold
                        )

                        Text(
                            text = "Posting to Alumni Connect",
                            style = MaterialTheme.typography.bodyMedium,
                            color = Color.Gray
                        )
                    }
                }
            }

            HorizontalDivider()

            // Rich text editor area
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                // Basic text field for input
                BasicTextField(
                    value = postState.content,
                    onValueChange = onPostContentChange,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                        .focusRequester(focusRequester),
                    textStyle = TextStyle(
                        fontSize = 16.sp,
                        color = MaterialTheme.colorScheme.onSurface,
                        textAlign = TextAlign.Start
                    ),
                    decorationBox = { innerTextField ->
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                        ) {
                            if (postState.content.isEmpty()) {
                                Text(
                                    text = "What's on your mind?",
                                    color = Color.Gray,
                                    fontSize = 16.sp
                                )
                            }
                            innerTextField()
                        }
                    }
                )


                // Preview of the post with formatted links
                if (postState.content.isNotEmpty()) {
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = "Preview:",
                        style = MaterialTheme.typography.labelMedium,
                        color = Color.Gray
                    )
                    Spacer(modifier = Modifier.height(8.dp))

                    Surface(
                        modifier = Modifier
                            .fillMaxWidth()
                            .border(
                                width = 1.dp,
                                color = MaterialTheme.colorScheme.outline.copy(alpha = 0.5f),
                                shape = RoundedCornerShape(8.dp)
                            )
                            .clip(RoundedCornerShape(8.dp)),
                        color = MaterialTheme.colorScheme.surface
                    ) {
                        LinkifiedText(
                            text = postState.content, modifier = Modifier.padding(12.dp),
                            style = TextStyle(
                                fontSize = 16.sp, color = MaterialTheme.colorScheme.onSurface
                            ),
                        )
                    }
                }
            }

            HorizontalDivider()

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Text(
                    text = "Add to your post:",
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.weight(1f)
                )

                // Image attachment button
                IconButton(
                    onClick = onAddImage,
                    modifier = Modifier
                        .size(40.dp)
                        .clip(CircleShape)
                        .background(MaterialTheme.colorScheme.secondaryContainer)
                ) {
                    Icon(
                        imageVector = Icons.Default.Image,
                        contentDescription = "Add Image",
                        tint = MaterialTheme.colorScheme.onSecondaryContainer
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun CreatePostScreenPreview() {
    val previewUser = dummyUser
    val textFieldValue = remember {
        mutableStateOf(
            TextFieldValue(
                "I've just published a new article about Jetpack Compose! " + "Check it out at www.medium.com/android-dev/jetpack-compose-tutorial " + "and let me know what you think. Also, there's a GitHub repo at " + "https://github.com/johndoe/compose-samples with sample code."
            )
        )
    }

    AlumniConnectTheme {
        CreatePostScreen(
            currentUser = previewUser,
            postState = CreatePostState(content = textFieldValue.value.text),
            onPostContentChange = { })
    }
}

@Preview(showBackground = true)
@Composable
private fun CreatePostScreenEmptyPreview() {
    AlumniConnectTheme {
        CreatePostScreen(
            currentUser = dummyUser, postState = CreatePostState()
        )
    }
}