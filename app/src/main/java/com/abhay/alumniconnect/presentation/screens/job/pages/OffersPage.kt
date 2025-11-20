package com.abhay.alumniconnect.presentation.screens.job.pages

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.abhay.alumniconnect.data.remote.dto.job.Job
import com.abhay.alumniconnect.presentation.components.JobCard
import com.example.ui.theme.someFontFamily

@Composable
fun OffersPage(
    modifier: Modifier = Modifier,
    jobs: List<Job> = emptyList(),
    onJobCardClick: (id: String, applied: Boolean) -> Unit = { _, _ -> }
) {
    Surface(
        color = MaterialTheme.colorScheme.background
    ) {

        if(jobs.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 80.dp),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ){
                    Text(
                        text = "Don’t give up!",
                        fontSize = 25.sp,
                        style = MaterialTheme.typography.bodyMedium,
                        fontFamily = someFontFamily,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary,
                        textAlign = TextAlign.Center,
                        letterSpacing = 2.sp
                    )
                    Text(
                        text = "(ง•̀o•́)ง ",
                        fontSize = 25.sp,
                        style = MaterialTheme.typography.bodyMedium,
                        fontFamily = someFontFamily,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary,
                        textAlign = TextAlign.Center,
                        letterSpacing = 2.sp
                    )
                }
            }
        }else {
            LazyColumn(
                modifier = modifier.fillMaxSize()
            ) {
                items(jobs, key = { it._id }) { job ->
                    JobCard(
                        job = job,
                        alreadyApplied = true,
                        onClick = { onJobCardClick(job._id, job.alreadyApplied == true) }
                    )
                }
            }
        }
    }
}