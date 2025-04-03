package com.abhay.alumniconnect.data.remote.dto

data class Job(
    val __v: Int,
    val _id: String,
    val applicationDeadline: String,
    val benefitsOffered: List<String>,
    val company: String,
    val createdAt: String,
    val description: String,
    val experienceLevel: String,
    val graduationYear: Int,
    val jobType: String,
    val location: String,
    val minExperience: Int,
    val postedBy: Any,
    val requiredEducation: RequiredEducation,
    val requiredSkills: List<String>,
    val status: String,
    val title: String,
    val updatedAt: String
)