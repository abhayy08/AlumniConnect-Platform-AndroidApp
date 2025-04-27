package com.abhay.alumniconnect.data.repository

import android.util.Log
import com.abhay.alumniconnect.data.remote.AlumniApi
import com.abhay.alumniconnect.data.remote.dto.post.Comment
import com.abhay.alumniconnect.data.remote.dto.post.Post
import com.abhay.alumniconnect.domain.repository.PostsRepository
import com.abhay.alumniconnect.utils.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
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

    override suspend fun createPost(content: String, imageFile: File?): Result<String> =
        withContext(Dispatchers.IO) {
            try {
                if(imageFile == null) {
                    val requestBody = mapOf(
                        "content" to content
                    )
                    val response = api.createPost(requestBody)
                    if (!response.isSuccessful) {
                        return@withContext Result.Error(
                            message = extractErrorMessage(response, ERROR_TAG)
                        )
                    }

                    response.body()?.let {
                        return@withContext Result.Success(it.message)
                    }
                    return@withContext Result.Error(message = "An unknown error has occurred")
                }else{

                    val contentPart = MultipartBody.Part.createFormData("content", content)

                    val mimeType = when {
                        imageFile.name.endsWith(".jpg", ignoreCase = true) -> "image/jpeg"
                        imageFile.name.endsWith(".jpeg", ignoreCase = true) -> "image/jpeg"
                        imageFile.name.endsWith(".png", ignoreCase = true) -> "image/png"
                        imageFile.name.endsWith(".gif", ignoreCase = true) -> "image/gif"
                        else -> "image/*"
                    }

                    val requestFile = imageFile.asRequestBody(mimeType.toMediaTypeOrNull())
                    val imagePart = MultipartBody.Part.createFormData("image", imageFile.name, requestFile)

                    val response = api.createPostWithImage(contentPart, imagePart)
                    if (!response.isSuccessful) {
                        return@withContext Result.Error(
                            message = extractErrorMessage(response, ERROR_TAG)
                        )
                    }

                    response.body()?.let {
                        return@withContext Result.Success(it.message)
                    }
                    return@withContext Result.Error(message = "An unknown error has occurred")
                }
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

    override suspend fun commentOnPost(postId: String, content: String): Result<Unit> =
        withContext(Dispatchers.IO) {
            try {
                val requestBody = mapOf("comment" to content)
                val response = api.commentOnPost(postId, requestBody)
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