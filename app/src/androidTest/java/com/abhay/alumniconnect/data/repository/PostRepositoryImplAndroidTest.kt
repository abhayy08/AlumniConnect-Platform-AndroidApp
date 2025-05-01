package com.abhay.alumniconnect.data.repository

import com.abhay.alumniconnect.data.remote.AlumniApi
import com.abhay.alumniconnect.utils.Result
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject

@ExperimentalCoroutinesApi
@HiltAndroidTest
class PostRepositoryImplAndroidTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Inject
    lateinit var api: AlumniApi

    private lateinit var repository: PostRepositoryImpl
    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setUp() {
        hiltRule.inject()
        Dispatchers.setMain(testDispatcher)
        repository = PostRepositoryImpl(api)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun getPostsReturnsSuccess() = runTest(testDispatcher) {
        val result = repository.getPosts(1, 10)
        testDispatcher.scheduler.advanceUntilIdle()
        assertTrue(result is Result.Success)
    }

    @Test
    fun getPostsByIdReturnsSuccess() = runTest(testDispatcher) {
        val result = repository.getPostsById("testUserId", 1, 10)
        testDispatcher.scheduler.advanceUntilIdle()
        assertTrue(result is Result.Success)
    }

    @Test
    fun createPostReturnsSuccess() = runTest(testDispatcher) {
        val result = repository.createPost("Test content", null)
        testDispatcher.scheduler.advanceUntilIdle()
        assertTrue(result is Result.Success)
    }

    @Test
    fun likePostReturnsSuccess() = runTest(testDispatcher) {
        val result = repository.likePost("testPostId")
        testDispatcher.scheduler.advanceUntilIdle()
        assertTrue(result is Result.Success)
    }

    @Test
    fun commentOnPostReturnsSuccess() = runTest(testDispatcher) {
        val result = repository.commentOnPost("testPostId", "Test comment")
        testDispatcher.scheduler.advanceUntilIdle()
        assertTrue(result is Result.Success)
    }

    @Test
    fun getPostCommentsReturnsSuccess() = runTest(testDispatcher) {
        val result = repository.getPostComments("testPostId", 1, 10)
        testDispatcher.scheduler.advanceUntilIdle()
        assertTrue(result is Result.Success)
    }

    @Test
    fun deleteCommentReturnsSuccess() = runTest(testDispatcher) {
        val result = repository.deleteComment("testPostId", "testCommentId")
        testDispatcher.scheduler.advanceUntilIdle()
        assertTrue(result is Result.Success)
    }

    @Test
    fun deletePostReturnsSuccess() = runTest(testDispatcher) {
        val result = repository.deletePost("testPostId")
        testDispatcher.scheduler.advanceUntilIdle()
        assertTrue(result is Result.Success)
    }
}