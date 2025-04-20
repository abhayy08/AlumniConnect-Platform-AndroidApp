package com.abhay.alumniconnect.presentation.screens.profile.applicants

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ApplicantsScreen(
    applications: List<Application>,
    onApplicationStatusUpdate: (String, String) -> Unit = { _, _ -> },
    onUserClick: (String) -> Unit,
    onBackClick: () -> Unit = {}
) {
    val statusOptions = listOf("pending", "reviewed", "interviewed", "rejected", "accepted")
    var showStatusDialog by remember { mutableStateOf(false) }
    var selectedApplication by remember { mutableStateOf<Application?>(null) }
    var currentStatus by remember { mutableStateOf("pending") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Applicants") },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.AutoMirrored.Rounded.ArrowBack, contentDescription = "Close")
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
        ) {

            if (applications.isEmpty()) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
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
                        ApplicantCard(
                            applicant = application,
                            onStatusUpdateClick = {
                                selectedApplication = application
                                currentStatus = application.status
                                showStatusDialog = true
                            },
                            onUserClick = { onUserClick(application._userId) }
                        )
                    }
                }
            }

            if (showStatusDialog && selectedApplication != null) {
                AlertDialog(
                    shape = MaterialTheme.shapes.small,
                    onDismissRequest = { showStatusDialog = false },
                    title = {
                        Text(text = "Update Application Status")
                    },
                    text = {
                        Column {
                            Text(
                                text = "Select a new status for ${selectedApplication?.name}",
                                style = MaterialTheme.typography.bodyMedium,
                                modifier = Modifier.padding(bottom = 16.dp)
                            )

                            statusOptions.forEach { status ->
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .clickable { currentStatus = status }
                                        .padding(vertical = 8.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    RadioButton(
                                        selected = status == currentStatus,
                                        onClick = { currentStatus = status }
                                    )
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Text(
                                        text = status.replaceFirstChar { it.uppercaseChar() },
                                        style = MaterialTheme.typography.bodyMedium
                                    )
                                }
                            }
                        }
                    },
                    confirmButton = {
                        Button(
                            onClick = {
                                selectedApplication?.let { applicant ->
                                    onApplicationStatusUpdate(
                                        applicant._applicationId,
                                        currentStatus
                                    )
                                }
                                showStatusDialog = false
                            }
                        ) {
                            Text("Update")
                        }
                    },
                    dismissButton = {
                        TextButton(
                            onClick = { showStatusDialog = false }
                        ) {
                            Text("Cancel")
                        }
                    }
                )
            }
        }
    }
}


@Composable
private fun ApplicantCard(
    applicant: Application,
    onStatusUpdateClick: () -> Unit,
    onUserClick: () -> Unit
) {
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
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Profile icon/avatar
            Box(
                modifier = Modifier
                    .size(50.dp)
                    .background(
                        color = MaterialTheme.colorScheme.primaryContainer,
                        shape = MaterialTheme.shapes.small
                    ),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = applicant.name.first().toString(),
                    style = MaterialTheme.typography.headlineMedium,
                    color = MaterialTheme.colorScheme.onPrimaryContainer
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            // Applicant info
            Column(
                modifier = Modifier.weight(1f)
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

            // Action buttons
            IconButton(onClick = onStatusUpdateClick) {
                Icon(
                    imageVector = Icons.Default.Edit,
                    contentDescription = "Update Status",
                    tint = MaterialTheme.colorScheme.primary
                )
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
            _userId = "1", appliedAt = "recteque", resumeLink = "penatibus", status = "novum"

        ),
        Application(
            _applicationId = "1",
            name = "John Doe",
            email = "john.doe@example.com",
            _userId = "1", appliedAt = "recteque", resumeLink = "penatibus", status = "novum"

        ),
        Application(
            _applicationId = "1",
            name = "John Doe",
            email = "john.doe@example.com",
            _userId = "1", appliedAt = "recteque", resumeLink = "penatibus", status = "novum"

        ),
        Application(
            _applicationId = "1",
            name = "John Doe",
            email = "john.doe@example.com",
            _userId = "1", appliedAt = "recteque", resumeLink = "penatibus", status = "novum"

        ),
    )

    MaterialTheme {
        ApplicantsScreen(
            applications = sampleApplicants,
            onApplicationStatusUpdate = { _, _ -> },
            onUserClick = {}
        )
    }
}
