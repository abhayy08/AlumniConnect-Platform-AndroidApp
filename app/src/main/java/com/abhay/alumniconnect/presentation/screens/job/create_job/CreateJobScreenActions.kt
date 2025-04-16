package com.abhay.alumniconnect.presentation.screens.job.create_job

import com.abhay.alumniconnect.data.remote.dto.job.RequiredEducation

sealed class CreateJobScreenActions {
    data class onTitleChange(val title: String) : CreateJobScreenActions()
    data class onCompanyChange(val company: String) : CreateJobScreenActions()
    data class onDescriptionChange(val description: String) : CreateJobScreenActions()
    data class onLocationChange(val location: String) : CreateJobScreenActions()
    data class onJobTypeChange(val jobType: String) : CreateJobScreenActions()
    data class onExperienceLevelChange(val experienceLevel: String) : CreateJobScreenActions()
    data class onMinExperienceChange(val minExperience: String) : CreateJobScreenActions()
    data class onApplicationDeadlineChange(val applicationDeadline: String) : CreateJobScreenActions()
    data class onRequiredSkillsChange(val requiredSkills: List<String>) : CreateJobScreenActions()
    data class onRequiredEducationChange(val requiredEducation: List<RequiredEducation>) : CreateJobScreenActions()
    data class onGraduationYearChange(val graduationYear: String) : CreateJobScreenActions()
    data class onBenefitsOfferedChange(val benefitsOffered: List<String>) : CreateJobScreenActions()
    data class onCreateJob(val popBack: () -> Unit) : CreateJobScreenActions()
    object resetMessage : CreateJobScreenActions()
}