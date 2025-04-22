package com.abhay.alumniconnect.presentation.screens.profile.applicants

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material.icons.rounded.KeyboardArrowDown
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.abhay.alumniconnect.data.remote.dto.job.Application
import com.abhay.alumniconnect.utils.capitalize
import com.example.compose.AlumniConnectTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ApplicantsScreen(
    applications: List<Application>,
    onApplicationStatusUpdate: (String, String) -> Unit = { _, _ -> },
    onUserClick: (String) -> Unit,
    onBackClick: () -> Unit = {}
) {
    Scaffold(
        topBar = {
            TopAppBar(title = { Text(text = "Applicants") }, navigationIcon = {
                IconButton(onClick = onBackClick) {
                    Icon(Icons.AutoMirrored.Rounded.ArrowBack, contentDescription = "Close")
                }
            })
        }) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            if (applications.isEmpty()) {
                Box(
                    modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "No applicants found",
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(applications) { application ->
                        ApplicantCard(applicant = application, onStatusUpdate = { newStatus ->
                            onApplicationStatusUpdate(application._applicationId, newStatus)
                        }, onUserClick = { onUserClick(application._userId) })
                    }
                }
            }
        }
    }
}

@Composable
private fun ApplicantCard(
    applicant: Application, onStatusUpdate: (String) -> Unit, onUserClick: () -> Unit
) {
    val statusOptions = listOf("pending", "reviewed", "interviewed", "rejected", "accepted")
    var expanded by remember { mutableStateOf(false) }
    var currentStatus by remember { mutableStateOf(applicant.status) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onUserClick() },
        shape = MaterialTheme.shapes.small,
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Profile icon/avatar
            Box(
                modifier = Modifier
                    .size(50.dp)
                    .background(
                        color = MaterialTheme.colorScheme.primaryContainer,
                        shape = MaterialTheme.shapes.small
                    ), contentAlignment = Alignment.Center
            ) {
                Text(
                    text = applicant.name.first().toString(),
                    style = MaterialTheme.typography.headlineMedium,
                    color = MaterialTheme.colorScheme.onPrimaryContainer
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            Row(
                modifier = Modifier.weight(1f),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Name and email
                Column(
                    modifier = Modifier.weight(1f, fill = false)
                ) {
                    Text(
                        text = applicant.name,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = applicant.email,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }

                Box(
                    modifier = Modifier
                        .padding(start = 8.dp)
                        .background(
                            MaterialTheme.colorScheme.secondaryContainer, MaterialTheme.shapes.small
                        )
                        .border(
                            1.dp, MaterialTheme.colorScheme.primary, MaterialTheme.shapes.small
                        )
                ) {
                    Row(modifier = Modifier
                        .clickable { expanded = true }
                        .padding(horizontal = 8.dp, vertical = 6.dp),
                        verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = currentStatus.capitalize(),
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.primary
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Icon(
                            imageVector = Icons.Rounded.KeyboardArrowDown,
                            contentDescription = "Change Status",
                            tint = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.size(16.dp)
                        )
                    }

                    DropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false },
                        modifier = Modifier.width(IntrinsicSize.Min),
                        shape = MaterialTheme.shapes.small
                    ) {
                        statusOptions.forEach { status ->
                            DropdownMenuItem(text = {
                                Text(status.replaceFirstChar { it.uppercaseChar() })
                            }, onClick = {
                                currentStatus = status
                                onStatusUpdate(status)
                                expanded = false
                            })
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ApplicantsScreenPreview() {
    // Sample applicants data
    val sampleApplicants = listOf(
        Application(
            _applicationId = "1",
            name = "John Doe",
            email = "john.doe@example.com",
            _userId = "1",
            appliedAt = "recteque",
            resumeLink = "penatibus",
            status = "pending"
        ),
        Application(
            _applicationId = "2",
            name = "Jane Smith",
            email = "jane.smith@example.com",
            _userId = "2",
            appliedAt = "recteque",
            resumeLink = "penatibus",
            status = "reviewed"
        ),
        Application(
            _applicationId = "3",
            name = "Alex Johnson",
            email = "alex.johnson@example.com",
            _userId = "3",
            appliedAt = "recteque",
            resumeLink = "penatibus",
            status = "interviewed"
        ),
        Application(
            _applicationId = "4",
            name = "Sarah Williams",
            email = "sarah.williams@example.com",
            _userId = "4",
            appliedAt = "recteque",
            resumeLink = "penatibus",
            status = "accepted"
        ),
    )

    AlumniConnectTheme {
        ApplicantsScreen(
            applications = sampleApplicants,
            onApplicationStatusUpdate = { _, _ -> },
            onUserClick = {})
    }
}