package com.abhay.alumniconnect.data.repository

import com.abhay.alumniconnect.data.remote.AlumniApi
import com.abhay.alumniconnect.data.remote.dto.post.Author
import com.abhay.alumniconnect.data.remote.dto.post.Post
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever
import retrofit2.Response
import com.abhay.alumniconnect.utils.Result
import com.abhay.alumniconnect.data.remote.dto.ApiResponse
import com.abhay.alumniconnect.data.remote.dto.CreatePostResponse
import com.abhay.alumniconnect.data.remote.dto.post.Comment
import org.mockito.kotlin.*


@ExperimentalCoroutinesApi
class PostRepositoryImplTest {

    private lateinit var repository: PostRepositoryImpl
    private val api: AlumniApi = mock()
    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        repository = PostRepositoryImpl(api)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    private fun samplePost() = Post(
        __v = 1,
        _id = "post123",
        author = Author("id1", "Company", "Dev", "Alice", "img.png"),
        comments = emptyList(),
        content = "Hello World",
        createdAt = "2023-01-01",
        likes = emptyList(),
        updatedAt = "2023-01-01",
        likedByCurrentUser = false,
        commentsCount = 0,
        likesCount = 0,
        imageUrl = ""
    )

    @Test
    fun `getPosts returns success`() = runTest(testDispatcher) {
        val posts = listOf(samplePost())
        whenever(api.getPosts(1, 10)).thenReturn(Response.success(posts))

        val result = repository.getPosts(1, 10)
        testDispatcher.scheduler.advanceUntilIdle()

        assertTrue(result is Result.Success)
        assertEquals(posts, (result as Result.Success).data)
    }

    @Test
    fun `getPosts returns error when API fails`() = runTest(testDispatcher) {
        whenever(api.getPosts(1, 10)).thenReturn(Response.error(500, "Server error".toResponseBody()))
        val result = repository.getPosts(1, 10)
        assertTrue(result is Result.Error)
    }

    @Test
    fun `getPostsById returns success`() = runTest(testDispatcher) {
        val posts = listOf(samplePost())
        whenever(api.getPostsByUserId("user123", 1, 10)).thenReturn(Response.success(posts))
        val result = repository.getPostsById("user123", 1, 10)
        testDispatcher.scheduler.advanceUntilIdle()
        assertTrue(result is Result.Success)
    }

    @Test
    fun `createPost without image returns success`() = runTest(testDispatcher) {
        val response = CreatePostResponse(message = "Created")
        whenever(api.createPost(any())).thenReturn(Response.success(response))
        val result = repository.createPost("Post content", null)
        testDispatcher.scheduler.advanceUntilIdle()
        assertTrue(result is Result.Success)
    }

    @Test
    fun `likePost returns success when API call is successful`() = runTest(testDispatcher) {
        val response = ApiResponse(message = "Post liked")
        whenever(api.likePost("post123")).thenReturn(Response.success(response))
        val result = repository.likePost("post123")
        assertTrue(result is Result.Success)
    }

    @Test
    fun `commentOnPost returns error when API fails`() = runTest(testDispatcher) {
        whenever(api.commentOnPost(eq("post123"), any())).thenReturn(Response.error(400, "Bad Request".toResponseBody()))
        val result = repository.commentOnPost("post123", "Nice post!")
        assertTrue(result is Result.Error)
    }

    @Test
    fun `getPostComments returns success`() = runTest(testDispatcher) {
        val comments = listOf<Comment>()
        whenever(api.getCommentsOnPost("post123", 1, 10)).thenReturn(Response.success(comments))
        val result = repository.getPostComments("post123", 1, 10)
        testDispatcher.scheduler.advanceUntilIdle()
        assertTrue(result is Result.Success)
    }

    @Test
    fun `deleteComment returns success`() = runTest(testDispatcher) {
        val response = ApiResponse(message = "Deleted")
        whenever(api.deleteComment("post123", "comment456")).thenReturn(Response.success(response))
        val result = repository.deleteComment("post123", "comment456")
        testDispatcher.scheduler.advanceUntilIdle()
        assertTrue(result is Result.Success)
    }

    @Test
    fun `deletePost returns success`() = runTest(testDispatcher) {
        val response = ApiResponse(message = "Deleted")
        whenever(api.deletePost("post123")).thenReturn(Response.success(response))
        val result = repository.deletePost("post123")
        testDispatcher.scheduler.advanceUntilIdle()
        assertTrue(result is Result.Success)
    }
}

