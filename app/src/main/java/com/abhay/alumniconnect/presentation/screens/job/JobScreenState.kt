package com.abhay.alumniconnect.presentation.screens.job

import com.abhay.alumniconnect.data.remote.dto.Job

data class JobScreenState(
    val jobs: List<Job> = emptyList(),
    val jobsAppliedTo: List<Job> = emptyList(),
    val jobsOffered: List<Job> = emptyList()
)