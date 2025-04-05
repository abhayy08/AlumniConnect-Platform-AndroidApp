package com.abhay.alumniconnect.presentation.screens.job

sealed class JobScreenActions {

    data class onApplyClick(val jobId: String, val resumeLink: String, val coverLetter: String) : JobScreenActions()
    data class onNavigateBack(val onBackClick: () -> Unit): JobScreenActions()

}