package com.abhay.alumniconnect.data.repository

import com.abhay.alumniconnect.data.remote.AlumniApi
import com.abhay.alumniconnect.data.remote.dto.post.Comment
import com.abhay.alumniconnect.data.remote.dto.post.Post
import com.abhay.alumniconnect.domain.repository.PostsRepository
import com.abhay.alumniconnect.utils.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.lang.Exception
import javax.inject.Inject

class PostRepositoryImpl @Inject constructor(
    private val api: AlumniApi
): PostsRepository {
    override suspend fun getPosts(page: Int, limit: Int): Result<List<Post>> =
        withContext(Dispatchers.IO) {
            try {
                val response = api.getPosts(page, limit)
                if(!response.isSuccessful) {
                    return@withContext Result.Error(
                        message = extractErrorMessage(response, ERROR_TAG)
                    )
                }
                response.body()?.let {
                    return@withContext Result.Success(it)
                }
                Result.Error(message = "An unknown error has occurred")
            }catch(e: Exception) {
                Result.Error(e.message ?: "An unknown error has occurred")
            }
        }

    override suspend fun createPost(content: String): Result<String> =
        withContext(Dispatchers.IO) {
            try {
                val requestBody = mapOf(
                    "content" to content
                )
                val response = api.createPost(requestBody)
                if(!response.isSuccessful) {
                    return@withContext Result.Error(
                        message = extractErrorMessage(response, ERROR_TAG)
                    )
                }

                response.body()?.let {
                    return@withContext Result.Success(it.message)
                }
                Result.Error(message = "An unknown error has occurred")
            }catch(e: Exception) {
                Result.Error(e.message ?: "An unknown error has occurred")
            }
        }

    override suspend fun likePost(postId: String): Result<Unit> =
        withContext(Dispatchers.IO) {
            try {
                val response = api.likePost(postId)
                if(!response.isSuccessful) {
                    return@withContext Result.Error(
                        message = extractErrorMessage(response, ERROR_TAG)
                    )
                }
                response.body()?.let {
                    return@withContext Result.Success(Unit)
                }
                Result.Error(message = "An unknown error has occurred")
            }catch(e: Exception) {
                Result.Error(e.message ?: "An unknown error has occurred")
            }
        }

    override suspend fun commentOnPost(postId: String): Result<Unit> =
        withContext(Dispatchers.IO) {
            try {
                val response = api.commentOnPost(postId)
                if(!response.isSuccessful) {
                    return@withContext Result.Error(
                        message = extractErrorMessage(response, ERROR_TAG)
                    )
                }
                response.body()?.let {
                    return@withContext Result.Success(Unit)
                }
                Result.Error(message = "An unknown error has occurred")
            }catch(e: Exception) {
                Result.Error(e.message ?: "An unknown error has occurred")
            }
        }

    override suspend fun getPostComments(
        postId: String,
        page: Int,
        limit: Int
    ): Result<List<Comment>> =
        withContext(Dispatchers.IO) {
            try {
                val response = api.getCommentsOnPost(postId, page, limit)
                if(!response.isSuccessful) {
                    return@withContext Result.Error(
                        message = extractErrorMessage(response, ERROR_TAG)
                    )
                }
                response.body()?.let {
                    return@withContext Result.Success(it)
                }
                Result.Error(message = "An unknown error has occurred")
            }catch(e: Exception) {
                Result.Error(e.message ?: "An unknown error has occurred")
            }
        }

    override suspend fun deleteComment(
        postId: String,
        commentId: String
    ): Result<Unit> =
        withContext(Dispatchers.IO) {
            try {
                val response = api.deleteComment(postId, commentId)
                if(!response.isSuccessful) {
                    return@withContext Result.Error(
                        message = extractErrorMessage(response, ERROR_TAG)
                    )
                }
                response.body()?.let {
                    return@withContext Result.Success(Unit)
                }
                Result.Error(message = "An unknown error has occurred")
            }catch(e: Exception) {
                Result.Error(e.message ?: "An unknown error has occurred")
            }
        }

    override suspend fun deletePost(postId: String): Result<Unit> =
        withContext(Dispatchers.IO) {
            try {
                val response = api.deletePost(postId)
                if(!response.isSuccessful) {
                    return@withContext Result.Error(
                        message = extractErrorMessage(response, ERROR_TAG)
                    )
                }
                response.body()?.let {
                    return@withContext Result.Success(Unit)
                }
                Result.Error(message = "An unknown error has occurred")
            }catch(e: Exception) {
                Result.Error(e.message ?: "An unknown error has occurred")
            }
        }

    companion object{
        const val ERROR_TAG = "PostRepositoryImpl"
    }

}