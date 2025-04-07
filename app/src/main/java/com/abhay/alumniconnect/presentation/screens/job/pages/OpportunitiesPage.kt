package com.abhay.alumniconnect.presentation.screens.job.pages

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.abhay.alumniconnect.data.remote.dto.Job
import com.abhay.alumniconnect.presentation.dummyJobs
import com.abhay.alumniconnect.presentation.screens.job.components.JobCard

@Composable
fun OpportunitiesPage(
    modifier: Modifier = Modifier,
    jobs: List<Job>,
    onApplyClick: (String) -> Unit = { },
    onJobCardClick: (id: String, applied: Boolean) -> Unit = { _, _ -> }
) {
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
    ){
        items(jobs, key = {it._id}) {
            JobCard(
                modifier = Modifier.clickable { onJobCardClick(it._id, false) },
                title = it.title,
                company = it.company,
                location = it.location,
                jobType = it.jobType,
                experienceLevel = it.experienceLevel,
                requiredSkills = it.requiredSkills,
                applicationDeadline = it.applicationDeadline,
                onApplyClick = { onApplyClick(it._id) },
            )
        }
    }
}
