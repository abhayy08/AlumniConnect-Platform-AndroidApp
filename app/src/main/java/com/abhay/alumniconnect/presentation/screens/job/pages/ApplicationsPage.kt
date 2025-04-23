package com.abhay.alumniconnect.presentation.screens.job.pages

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.abhay.alumniconnect.data.remote.dto.job.Job
import com.abhay.alumniconnect.presentation.components.JobCard

@Composable
fun ApplicationsPage(
    modifier: Modifier = Modifier,
    jobs: List<Job> = emptyList(),
    onJobCardClick: (id: String, applied: Boolean) -> Unit = { _, _ -> }
) {
    Surface(
        color = MaterialTheme.colorScheme.background
    ) {
        LazyColumn(
            modifier = modifier.fillMaxSize()
        ) {
            items(jobs, key = { it._id }) { job ->
                JobCard(
                    job = job,
                    alreadyApplied = true,
                    onClick = { onJobCardClick(job._id, true) }
                )
            }
        }
    }
}