package com.abhay.alumniconnect.presentation.screens.job

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.abhay.alumniconnect.data.remote.dto.Job

@Composable
fun OpportunitesPage(
    modifier: Modifier = Modifier,
    jobs: List<Job>,
    onApplyClick: (id: String, resumeLink: String) -> Unit = { _, _ -> }
) {
    LazyColumn(
        modifier = modifier
    ){
        items(jobs) {
            JobCard(
                title = it.title,
                company = it.company,
                location = it.location,
                jobType = it.jobType,
                experienceLevel = it.experienceLevel,
                requiredSkills = it.requiredSkills,
                applicationDeadline = it.applicationDeadline,
                onClick = { onApplyClick(it._id, "this is the link") }
            )
        }
    }
}

@Preview
@Composable
private fun OpportunitesPagePreview() {
    OpportunitesPage(
        jobs = dummyJobs
    )
}
