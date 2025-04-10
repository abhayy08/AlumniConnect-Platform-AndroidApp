package com.abhay.alumniconnect.presentation

import com.abhay.alumniconnect.data.remote.dto.Connection
import com.abhay.alumniconnect.data.remote.dto.job.Job
import com.abhay.alumniconnect.data.remote.dto.job.PostedBy
import com.abhay.alumniconnect.data.remote.dto.user.PrivacySettings
import com.abhay.alumniconnect.data.remote.dto.job.RequiredEducation
import com.abhay.alumniconnect.data.remote.dto.user.WorkExperience
import com.abhay.alumniconnect.domain.model.User

val dummyUser = User(
    id = "123456",
    name = "Jane Doe",
    email = "jane.doe@example.com",
    bio = "Software developer with a passion for creating innovative solutions. Experienced in mobile and web development.",
    company = "Tech Innovations",
    currentJob = "Mobile Developer",
    jobTitle = "Senior Developer",
    degree = "Bachelor of Science",
    fieldOfStudy = "Computer Science",
    graduationYear = 2022,
    major = "Computer Science",
    minor = "Mathematics",
    university = "University of Technology",
    linkedInProfile = "linkedin.com/in/jane-doe",
    location = "San Francisco, CA",
    isVerifiedUser = true,
    createdAt = "2023-01-15T14:23:45.678Z",
    updatedAt = "2023-04-28T09:12:34.567Z",
    achievements = listOf(
        "Winner of University Hackathon 2022",
        "Published paper on Machine Learning Applications",
        "Developed open-source library with 500+ stars"
    ),
    interests = listOf("Machine Learning", "Mobile Development", "IoT", "Cloud Computing"),
    skills = listOf("Kotlin", "Android", "Java", "Python", "Flutter", "React", "AWS"),
    connections = listOf(
        Connection("1", "John Smith", "Google", "Software Engineer"),
        Connection("2", "Alice Johnson", "Amazon", "Product Manager"),
        Connection("3", "Bob Williams", "Microsoft", "UX Designer")
    ),
    workExperience = listOf(
        WorkExperience(
            _id = "exp1",
            company = "Tech Innovations",
            position = "Senior Developer",
            startDate = "2022-01-01",
            endDate = null,
            description = "Leading the mobile development team and implementing new features"
        ),
        WorkExperience(
            _id = "exp2",
            company = "StartupX",
            position = "Junior Developer",
            startDate = "2020-03-15",
            endDate = "2021-12-31",
            description = "Developed and maintained the company's mobile application"
        )
    ),
    privacySettings = PrivacySettings(
        showEmail = true,
        showPhone = false,
        showLocation = true
    )
)


val dummyJobs = listOf(
    Job(
        _id = "1",
        title = "Android Developer",
        company = "Google",
        description = "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.",
        location = "remote",
        jobType = "full-time",
        experienceLevel = "mid",
        minExperience = 2,
        applicationDeadline = "2025-04-30",
        requiredSkills = listOf("Kotlin", "Jetpack Compose", "MVVM"),
        requiredEducation = listOf(
            RequiredEducation("Bachelors", "CSE"),
            RequiredEducation("Bachelors", "IT")
        ),
        graduationYear = 2022,
        benefitsOffered = listOf("Health Insurance", "Stock Options"),
        status = "open",
        __v = 1,
        createdAt = "",
        postedBy = PostedBy("1", "Abhay", null),
        updatedAt = "",
        applications = emptyList()
    ),

)