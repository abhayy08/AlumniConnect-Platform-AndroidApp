package com.abhay.alumniconnect.presentation.screens.profile.add_edit_work_experience

import com.abhay.alumniconnect.data.remote.dto.user.WorkExperience

data class AddEditExperienceState(
    val id: String? = null,
    val company: String = "",
    val description: String = "",
    val endDate: String = "",
    val position: String = "",
    val startDate: String = "",
    val isEditMode: Boolean = false
) {
    fun toExperience(): WorkExperience {
        return WorkExperience(
            _id = this.id,
            company = this.company,
            description = this.description,
            endDate = this.endDate,
            position = this.position,
            startDate = this.startDate
        )

    }
}