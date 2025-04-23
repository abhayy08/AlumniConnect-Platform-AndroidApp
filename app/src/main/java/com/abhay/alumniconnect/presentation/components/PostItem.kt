package com.abhay.alumniconnect.presentation.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.LocalIndication
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.Comment
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.abhay.alumniconnect.data.remote.dto.post.Post
import com.abhay.alumniconnect.presentation.dummyPosts
import com.abhay.alumniconnect.utils.formatDateForDisplay
import com.example.ui.theme.AppShapes

@Composable
fun PostItem(
    modifier: Modifier = Modifier,
    post: Post,
    onUserClick: (String) -> Unit,
    onLikeClick: () -> Unit,
    onCommentClick: () -> Unit,
) {
    var isExpanded by remember { mutableStateOf(false) }
    val maxLines = if (isExpanded) Int.MAX_VALUE else 3

    Card(
        shape = AppShapes.medium,
        modifier = modifier.fillMaxWidth(),
        elevation = CardDefaults.elevatedCardElevation(defaultElevation = 4.dp),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.primaryContainer),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.background)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {

            // User Info
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.clickable { onUserClick(post.author._id) }) {
                    Box(
                        modifier = Modifier
                            .size(40.dp)
                            .clip(MaterialTheme.shapes.small)
                            .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.2f)),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = post.author.name.first().toString(),
                            style = MaterialTheme.typography.titleMedium,
                            color = MaterialTheme.colorScheme.primary
                        )
                    }

                    Spacer(modifier = Modifier.width(12.dp))

                    Column {
                        Text(
                            text = post.author.name,
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.SemiBold
                        )
                        Text(
                            text = formatDateForDisplay(post.createdAt),
                            style = MaterialTheme.typography.bodySmall,
                            color = Color.Gray
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // post content
            LinkifiedText(
                text = post.content,
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.padding(vertical = 4.dp),
                maxLines = maxLines
            )

            if (post.content.length > 200) {
                TextButton(
                    onClick = { isExpanded = !isExpanded },
                    contentPadding = PaddingValues(0.dp),
                ) {
                    Text(
                        text = if (isExpanded) "Show less" else "Read more",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Like and comment count
            Text(
                text = "${post.likesCount} likes • ${post.commentsCount} comments",
                style = MaterialTheme.typography.bodySmall,
                color = Color.Gray
            )

            HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))

            // Like / Comment Buttons
            Row(
                modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                // Like Button
                Row(
                    verticalAlignment = Alignment.CenterVertically, modifier = Modifier
                        .clickable(
                            interactionSource = remember { MutableInteractionSource() },
                            indication = LocalIndication.current,
                            onClick = onLikeClick
                        )
                        .padding(vertical = 8.dp)
                ) {
                    Icon(
                        imageVector = if (post.likedByCurrentUser) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                        contentDescription = "Like",
                        tint = if (post.likedByCurrentUser) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface,
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = "Like",
                        style = MaterialTheme.typography.bodyMedium,
                        color = if (post.likedByCurrentUser) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface
                    )
                }

                // Comment Button
                Row(
                    verticalAlignment = Alignment.CenterVertically, modifier = Modifier
                        .clickable(
                            interactionSource = remember { MutableInteractionSource() },
                            indication = LocalIndication.current,
                            onClick = onCommentClick
                        )
                        .padding(vertical = 8.dp)
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Rounded.Comment,
                        contentDescription = "Comment",
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = "Comment", style = MaterialTheme.typography.bodyMedium
                    )
                }
            }
        }
    }
}


@Preview
@Composable
private fun PostItemPreview() {
    PostItem(
        post = dummyPosts[0],
        onLikeClick = {},
        onCommentClick = {},
        onUserClick = {},
    )

}
