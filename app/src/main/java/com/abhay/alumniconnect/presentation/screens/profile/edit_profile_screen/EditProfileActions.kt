package com.abhay.alumniconnect.presentation.screens.profile.edit_profile_screen

sealed class EditProfileActions {
    data class SaveProfile(val popBack: () -> Unit) : EditProfileActions()
    data class UpdateBio(val bio: String) : EditProfileActions()
    data class UpdateLinkedInProfile(val linkedInProfile: String) : EditProfileActions()
    data class UpdateSkills(val skills: List<String>) : EditProfileActions()
    data class UpdateInterests(val interests: List<String>) : EditProfileActions()
    data class AddAchievement(val achievement: String) : EditProfileActions()
    data class RemoveAchievement(val achievement: String) : EditProfileActions()
}