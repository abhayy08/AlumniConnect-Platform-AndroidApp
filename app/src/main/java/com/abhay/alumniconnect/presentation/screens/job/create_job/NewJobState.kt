package com.abhay.alumniconnect.presentation.screens.job.create_job

import com.abhay.alumniconnect.data.remote.dto.job.Job
import com.abhay.alumniconnect.data.remote.dto.job.RequiredEducation

data class NewJobState(
    val title: String = "",
    val company: String = "",
    val description: String = "",
    val location: String = "in-office",
    val jobType: String = "full-time",
    val experienceLevel: String = "entry",
    val minExperience: String = "",
    val applicationDeadline: String = "",
    val requiredSkills: List<String> = emptyList<String>(),
    val requiredEducation: List<RequiredEducation> = emptyList(),
    val graduationYear: String = "",
    val benefitsOffered: List<String> = emptyList<String>(),
    val message: String? = null
) {

    fun toJob(): Job {
        val minExp = minExperience.toIntOrNull() ?: 0
        val gradYear = graduationYear.toIntOrNull() ?: 0

        return Job(
            applicationDeadline = applicationDeadline,
            benefitsOffered = benefitsOffered,
            company = company,
            createdAt = null,
            description = description,
            experienceLevel = experienceLevel,
            graduationYear = gradYear,
            jobType = jobType,
            location = location,
            minExperience = minExp,
            postedBy = null,
            requiredEducation = requiredEducation,
            applications = emptyList(),
            requiredSkills = requiredSkills,
            status = "open",
            title = title,
            updatedAt = null,
            __v = 0,
            _id = ""
        )
    }
}