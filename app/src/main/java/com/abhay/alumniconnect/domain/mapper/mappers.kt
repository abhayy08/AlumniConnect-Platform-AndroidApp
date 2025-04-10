package com.abhay.alumniconnect.domain.mapper

import com.abhay.alumniconnect.data.remote.dto.user.UserDetails
import com.abhay.alumniconnect.domain.model.User

fun UserDetails.toUser(): User {
    return User(
        id = this._id,
        name = this.name,
        email = this.email,
        bio = this.bio ?: "",
        company = this.company ?: "",
        currentJob = this.currentJob ?: "",
        jobTitle = this.jobTitle ?: "",
        degree = this.degree,
        fieldOfStudy = this.fieldOfStudy ?: "",
        graduationYear = this.graduationYear,
        major = this.major,
        minor = this.minor ?: "",
        university = this.university,
        linkedInProfile = this.linkedInProfile ?: "",
        location = this.location ?: "",
        isVerifiedUser = this.isVerifiedUser,
        createdAt = this.createdAt,
        updatedAt = this.updatedAt,
        achievements = this.achievements,
        interests = this.interests,
        skills = this.skills,
        connections = this.connections,
        workExperience = this.workExperience,
        privacySettings = this.privacySettings
    )
}

fun User.toUserDetails(): UserDetails {
    return UserDetails(
        _id = this.id,
        achievements = this.achievements,
        bio = this.bio,
        company = this.company,
        connections = this.connections,
        createdAt = this.createdAt,
        currentJob = this.currentJob,
        jobTitle = this.jobTitle,
        degree = this.degree,
        email = this.email,
        fieldOfStudy = this.fieldOfStudy,
        graduationYear = this.graduationYear,
        interests = this.interests,
        isVerifiedUser = this.isVerifiedUser,
        linkedInProfile = this.linkedInProfile,
        location = this.location,
        major = this.major,
        minor = this.minor,
        name = this.name,
        privacySettings = this.privacySettings,
        skills = this.skills,
        university = this.university,
        updatedAt = this.updatedAt,
        workExperience = this.workExperience,
        __v = null
    )
}