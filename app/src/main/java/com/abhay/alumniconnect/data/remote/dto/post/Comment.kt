package com.abhay.alumniconnect.data.remote.dto.post

data class Comment(
    val _id: String,
    val author: CommentAuthor,
    val comment: String,
    val createdAt: String
)