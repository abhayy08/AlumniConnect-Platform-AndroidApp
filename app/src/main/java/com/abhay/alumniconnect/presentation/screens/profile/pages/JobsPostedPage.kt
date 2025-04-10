package com.abhay.alumniconnect.presentation.screens.profile.pages

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.abhay.alumniconnect.data.remote.dto.job.Job
import com.abhay.alumniconnect.presentation.screens.job.components.JobCard


@Composable
fun JobsPostedPage(
    modifier: Modifier = Modifier,
    jobs: List<Job> = emptyList(),
) {
    if(jobs.isNotEmpty()) {
        LazyColumn(
            modifier = modifier.fillMaxSize()
        ) {
            items(jobs) {
                JobCard(
                    title = it.title,
                    company = it.company,
                    location = it.location,
                    jobType = it.jobType,
                    experienceLevel = it.experienceLevel,
                    requiredSkills = it.requiredSkills,
                    applicationDeadline = it.applicationDeadline,
                    onApplyClick = {},
                    alreadyApplied = false,
                    status = it.status,
                    showApplyButton = false
                )
            }
        }
    }else {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp), contentAlignment = Alignment.Center
        ) {
            Text(
                text = "No posts yet. Jobs shared by you will appear here.",
                style = MaterialTheme.typography.bodyLarge,
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
            )
        }
    }
}

