package com.abhay.alumniconnect.data.remote.dto.post

data class Post(
    val __v: Int,
    val _id: String,
    val author: Author,
    val comments: List<Comment>?,
    val content: String,
    val createdAt: String,
    val likes: List<Like>?,
    val updatedAt: String,
    val likedByCurrentUser: Boolean = false,
    val commentsCount: Int ,
    val likesCount: Int
)