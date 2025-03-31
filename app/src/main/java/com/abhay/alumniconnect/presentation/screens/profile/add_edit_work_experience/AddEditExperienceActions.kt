package com.abhay.alumniconnect.presentation.screens.profile.add_edit_work_experience

sealed class AddEditExperienceActions {

    data class onPositionChange(val position: String) : AddEditExperienceActions()
    data class onCompanyChange(val company: String) : AddEditExperienceActions()
    data class onStartDateChange(val startDate: String) : AddEditExperienceActions()
    data class onEndDateChange(val endDate: String) : AddEditExperienceActions()
    data class onDescriptionChange(val description: String) : AddEditExperienceActions()

    data class onSaveExperience(val popBack: () -> Unit) : AddEditExperienceActions()
    data class onDeleteExperience(val popBack: () -> Unit) : AddEditExperienceActions()
    data class onCancel(val popBack: () -> Unit) : AddEditExperienceActions()

}