package com.abhay.alumniconnect.presentation.screens.job.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.abhay.alumniconnect.data.remote.dto.job.Job
import com.abhay.alumniconnect.presentation.components.CustomChip
import com.abhay.alumniconnect.presentation.components.InfoLabel
import com.abhay.alumniconnect.presentation.dummyJobs
import com.abhay.alumniconnect.utils.capitalize
import com.abhay.alumniconnect.utils.formatDateForDisplay
import com.example.compose.AlumniConnectTheme

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun JobCard(
    job: Job,
    onApplyClick: () -> Unit = {},
    alreadyApplied: Boolean = false,
    showApplyButton: Boolean = true,
    onClick: () -> Unit = {}
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp, vertical = 6.dp)
            .clickable(onClick = onClick),
        shape = MaterialTheme.shapes.small,
        elevation = CardDefaults.cardElevation(2.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceContainer.copy(alpha = 1f)
        ),
        border = BorderStroke(width = 1.dp, color = MaterialTheme.colorScheme.primaryContainer)
    ) {
        Column(
            modifier = Modifier.padding(12.dp)
        ) {
            // title + company + logo
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = job.title,
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Spacer(modifier = Modifier.height(2.dp))
                    Text(
                        text = job.company,
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                    )
                }

                // Company logo
                Surface(
                    modifier = Modifier.size(40.dp),
                    shape = MaterialTheme.shapes.small,
                    color = Color.Black,
                    border = BorderStroke(
                        1.dp,
                        MaterialTheme.colorScheme.primary.copy(alpha = 0.7f)
                    ),
                    shadowElevation = 6.dp
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Text(
                            text = job.company.first().uppercase(),
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.tertiary
                        )
                    }
                }
            }

            HorizontalDivider(
                modifier = Modifier.padding(vertical = 8.dp),
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.08f)
            )

            // Info Section
            Row(
                modifier = Modifier.fillMaxWidth(),
            ) {
                InfoLabel(
                    label = "Location",
                    value = job.location.capitalize(),
                    modifier = Modifier.weight(1f)
                )
                InfoLabel(
                    label = "Type",
                    value = job.jobType.toString().split('-').joinToString(" ") { it.capitalize() },
                    modifier = Modifier.weight(1f)
                )
            }

            Spacer(modifier = Modifier.height(6.dp))

            // Experience
            InfoLabel(
                label = "Experience",
                value = "${job.minExperience}+ Yrs"
            )

            Spacer(modifier = Modifier.height(6.dp))

            // Skills
            FlowRow(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                job.requiredSkills.take(3).forEach { skill ->
                    CustomChip(
                        value = skill,
                        color = MaterialTheme.colorScheme.primaryContainer
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Description
            Text(
                text = job.description.take(80) + if (job.description.length > 80) "..." else "",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.8f),
                modifier = Modifier.padding(bottom = 8.dp)
            )

            // date and apply button
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.AccessTime,
                        contentDescription = null,
                        modifier = Modifier.size(14.dp),
                        tint = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = "Posted on ${formatDateForDisplay(job.createdAt.toString())}",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)
                    )
                }

                if (showApplyButton) {
                    Button(
                        onClick = onApplyClick,
                        shape = MaterialTheme.shapes.small,
                        enabled = !alreadyApplied
                    ) {
                        Text(
                            text = if (alreadyApplied) "Applied" else "Apply Now"
                        )
                    }
                }
            }
        }
    }
}

@Preview
@Composable
private fun JobCardPreview() {
    AlumniConnectTheme {
        JobCard(
            job = dummyJobs[0]
        )
    }
}