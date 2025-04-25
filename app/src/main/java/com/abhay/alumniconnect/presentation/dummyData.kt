package com.abhay.alumniconnect.presentation

import com.abhay.alumniconnect.data.remote.dto.Connection
import com.abhay.alumniconnect.data.remote.dto.job.Job
import com.abhay.alumniconnect.data.remote.dto.job.PostedBy
import com.abhay.alumniconnect.data.remote.dto.user.PrivacySettings
import com.abhay.alumniconnect.data.remote.dto.job.RequiredEducation
import com.abhay.alumniconnect.data.remote.dto.post.Author
import com.abhay.alumniconnect.data.remote.dto.post.Comment
import com.abhay.alumniconnect.data.remote.dto.post.CommentAuthor
import com.abhay.alumniconnect.data.remote.dto.post.Like
import com.abhay.alumniconnect.data.remote.dto.post.Post
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
        Connection("1", "John Smith", "Google", "Software Engineer", ""),
        Connection("2", "Alice Johnson", "Amazon", "Product Manager", ""),
        Connection("3", "Bob Williams", "Microsoft", "UX Designer", "")
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
    ),
    isConnected = false,
    connectionCount = 0
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

val dummyPosts = listOf(
    Post(
        __v = 0,
        _id = "post1",
        author = Author(
            _id = "user1",
            company = "TechCorp",
            jobTitle = "Software Engineer",
            name = "Alice Johnson"
        ),
        comments = listOf(
            Comment(
                _id = "comment1",
                author = CommentAuthor(
                    _id = "user2",
                    name = "Bob Smith"
                ),
                comment = "Great post!",
                createdAt = "2025-04-09T10:00:00Z"
            ),
            Comment(
                _id = "comment2",
                author = CommentAuthor(
                    _id = "user3",
                    name = "Charlie Nguyen"
                ),
                comment = "Thanks for sharing!",
                createdAt = "2025-04-09T10:30:00Z"
            ),
            Comment(
                _id = "c1omment2",
                author = CommentAuthor(
                    _id = "user3",
                    name = "Charlie Nguyen"
                ),
                comment = "Thanks for sharing!",
                createdAt = "2025-04-09T10:30:00Z"
            ),
            Comment(
                _id = "comme2nt2",
                author = CommentAuthor(
                    _id = "user3",
                    name = "Charlie Nguyen"
                ),
                comment = "Thanks for sharing!",
                createdAt = "2025-04-09T10:30:00Z"
            ),
            Comment(
                _id = "commen3t2",
                author = CommentAuthor(
                    _id = "user3",
                    name = "Charlie Nguyen"
                ),
                comment = "Thanks for sharing!",
                createdAt = "2025-04-09T10:30:00Z"
            ),
            Comment(
                _id = "comme4nt2",
                author = CommentAuthor(
                    _id = "user3",
                    name = "Charlie Nguyen"
                ),
                comment = "Thanks for sharing!",
                createdAt = "2025-04-09T10:30:00Z"
            ),
            Comment(
                _id = "comm5ent2",
                author = CommentAuthor(
                    _id = "user3",
                    name = "Charlie Nguyen"
                ),
                comment = "Thanks for sharing!",
                createdAt = "2025-04-09T10:30:00Z"
            ),
            Comment(
                _id = "com6ment2",
                author = CommentAuthor(
                    _id = "user3",
                    name = "Charlie Nguyen"
                ),
                comment = "Thanks for sharing!",
                createdAt = "2025-04-09T10:30:00Z"
            ),
            Comment(
                _id = "co7mment2",
                author = CommentAuthor(
                    _id = "user3",
                    name = "Charlie Nguyen"
                ),
                comment = "Thanks for sharing!",
                createdAt = "2025-04-09T10:30:00Z"
            ),
            Comment(
                _id = "comm8ent2",
                author = CommentAuthor(
                    _id = "user3",
                    name = "Charlie Nguyen"
                ),
                comment = "Thanks for sharing!",
                createdAt = "2025-04-09T10:30:00Z"
            ),
            Comment(
                _id = "co9mment2",
                author = CommentAuthor(
                    _id = "user3",
                    name = "Charlie Nguyen"
                ),
                comment = "Thanks for sharing!",
                createdAt = "2025-04-09T10:30:00Z"
            ),
            Comment(
                _id = "comfment2",
                author = CommentAuthor(
                    _id = "user3",
                    name = "Charlie Nguyen"
                ),
                comment = "Thanks for sharing!",
                createdAt = "2025-04-09T10:30:00Z"
            ),
            Comment(
                _id = "comment2",
                author = CommentAuthor(
                    _id = "user3",
                    name = "Charlie Nguyen"
                ),
                comment = "Thanks for sharing!",
                createdAt = "2025-04-09T10:30:00Z"
            ),
            Comment(
                _id = "commdent2",
                author = CommentAuthor(
                    _id = "user3",
                    name = "Charlie Nguyen"
                ),
                comment = "Thanks for sharing!",
                createdAt = "2025-04-09T10:30:00Z"
            )
        ),
        content = "Today I  learned about MongoDB relationships in Mongoose.",
        createdAt = "2025-04-09T09:45:00Z",
        updatedAt = "2025-04-09T11:00:00Z",
        likes = listOf(
            Like(_id = "user2", name = "Bob Smith"),
            Like(_id = "user4", name = "Dana Lee")
        ),
        likedByCurrentUser = true,
        commentsCount = 10,
        likesCount = 5
    ),
    Post(
        __v = 0,
        _id = "post2",
        author = Author(
            _id = "user5",
            company = "DesignHub",
            jobTitle = "UX Designer",
            name = "Emily Chen"
        ),
        comments = emptyList(),
        content = "Design is not just what it looks like and feels like. Design is how it works. - Steve Jobs",
        createdAt = "2025-04-08T14:20:00Z",
        updatedAt = "2025-04-08T14:20:00Z",
        likes = listOf(
            Like(_id = "user1", name = "Alice Johnson")
        ),
        likedByCurrentUser = false,
        commentsCount = 0,
        likesCount = 1
    )
)
