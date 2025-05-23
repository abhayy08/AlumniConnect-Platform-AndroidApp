package com.abhay.alumniconnect.domain.repository

import com.abhay.alumniconnect.data.remote.dto.post.Comment
import com.abhay.alumniconnect.data.remote.dto.post.Post
import com.abhay.alumniconnect.utils.Result
import java.io.File

interface PostsRepository {

    suspend fun getPosts(page: Int, limit: Int): Result<List<Post>>

    suspend fun getPostsById(userId: String, page: Int = 1, limit: Int = 15): Result<List<Post>>

    suspend fun createPost(content: String, imageFile: File?): Result<String>

    suspend fun likePost(postId: String): Result<Unit>

    suspend fun commentOnPost(postId: String, content: String): Result<Unit>

    suspend fun getPostComments(postId: String, page: Int, limit: Int): Result<List<Comment>>

    suspend fun deleteComment(postId: String, commentId: String): Result<Unit>

    suspend fun deletePost(postId: String): Result<Unit>

}