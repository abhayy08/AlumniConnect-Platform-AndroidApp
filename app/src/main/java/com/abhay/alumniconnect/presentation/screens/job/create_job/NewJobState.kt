package com.abhay.alumniconnect.presentation.screens.job.create_job

import com.abhay.alumniconnect.data.remote.dto.Job
import com.abhay.alumniconnect.data.remote.dto.RequiredEducation

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
        return Job(
            applicationDeadline = this.applicationDeadline,
            benefitsOffered = this.benefitsOffered,
            company = this.company,
            createdAt = null,
            description = this.description,
            experienceLevel = this.experienceLevel,
            graduationYear = this.graduationYear.toInt(),
            jobType = this.jobType,
            location = this.location,
            minExperience = this.minExperience.toInt(),
            postedBy = null,
            requiredEducation = this.requiredEducation,
            applications = emptyList(),
            requiredSkills = this.requiredSkills,
            status = "open",
            title = this.title,
            updatedAt = null,
            __v = 0 ,
            _id = ""
        )
    }
}