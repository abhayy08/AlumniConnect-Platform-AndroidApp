package com.abhay.alumniconnect.presentation.screens.job.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.abhay.alumniconnect.presentation.components.CustomChip
import com.abhay.alumniconnect.presentation.components.InfoLabel
import com.abhay.alumniconnect.utils.capitalize
import com.abhay.alumniconnect.utils.formatDateForDisplay
import com.example.compose.AlumniConnectTheme

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun JobCard(
    modifier: Modifier = Modifier,
    title: String,
    company: String,
    location: String,
    jobType: String,
    experienceLevel: String,
    requiredSkills: List<String>,
    applicationDeadline: String,
    onApplyClick: () -> Unit = {},
    alreadyApplied: Boolean = false,
    status: String = "",
    showApplyButton: Boolean = true,
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(10.dp),
        shape = MaterialTheme.shapes.small,
        elevation = CardDefaults.cardElevation(2.dp),
        border = BorderStroke(
            width = 1.dp, color = MaterialTheme.colorScheme.primaryContainer
        ),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.background
        )
    ) {
        Column(modifier = Modifier.padding(12.dp)) {

            Column {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(text = title, style = MaterialTheme.typography.titleMedium)
                    if(alreadyApplied) {
                        CustomChip(
                            value = status.capitalize(),
                            color = MaterialTheme.colorScheme.secondaryContainer
                        )
                    }
                }
                Text(text = company, style = MaterialTheme.typography.labelLarge, color = Color.Gray)
            }

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(80.dp)
            ) {
                InfoLabel(label = "Location", value = location.replaceFirstChar { it.titlecase() })

                InfoLabel(label = "Job Type", value = jobType.replaceFirstChar { it.titlecase() })
            }

            Spacer(modifier = Modifier.height(8.dp))

            InfoLabel(label = "Experience", value = experienceLevel.replaceFirstChar { it.titlecase() })

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                FlowRow {
                    requiredSkills.take(3).forEach { skill ->
                        CustomChip(
                            modifier = Modifier.scale(0.95f),
                            value = skill,
                            color = MaterialTheme.colorScheme.secondaryContainer
                        )
                    }
                }
            }
            Spacer(Modifier.height(6.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    Text(
                        text = "Registration open till",
                        style = MaterialTheme.typography.labelSmall,
                    )
                    Spacer(Modifier.height(2.dp))
                    Text(
                        text = formatDateForDisplay(applicationDeadline),
                        style = MaterialTheme.typography.labelSmall,
                        fontWeight = FontWeight.Bold
                    )
                }
                if(showApplyButton) {
                    Button(
                        modifier = Modifier.scale(0.9f),
                        onClick = onApplyClick,
                        shape = MaterialTheme.shapes.small,
                        enabled = !alreadyApplied
                    ) {
                        if(alreadyApplied) {
                            Text("Applied")
                        }else {
                            Text("Apply Now")
                        }
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
            title = "Android Developer",
            company = "Google",
            location = "remote",
            jobType = "full-time",
            experienceLevel = "mid",
            requiredSkills = listOf("Kotlin", "Jetpack Compose", "MVVM"),
            applicationDeadline = "2025-04-30",
            onApplyClick = {},
        )
    }
}
