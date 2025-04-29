package com.abhay.alumniconnect.presentation.screens.job.job_detail_screen

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.abhay.alumniconnect.data.remote.dto.job.Job
import com.abhay.alumniconnect.data.remote.dto.job.PostedBy
import com.abhay.alumniconnect.presentation.components.CustomChip
import com.abhay.alumniconnect.presentation.components.InfoLabel
import com.abhay.alumniconnect.presentation.components.InfoLabelWithChip
import com.abhay.alumniconnect.presentation.components.ListWithBullets
import com.abhay.alumniconnect.utils.capitalize
import com.abhay.alumniconnect.utils.formatDateForDisplay

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun JobDetails(
    jobState: JobDetailsState,
    onApplyClick: (String) -> Unit = {},
    onBackClick: () -> Unit = {},
    alreadyApplied: Boolean,
    resetError: () -> Unit,
    showSnackbar: (String) -> Unit,
    onUserClick: (String) -> Unit,
    isCurrentUser: Boolean,
    onDeleteJob: () -> Unit
) {
    BackHandler { onBackClick() }

    LaunchedEffect(jobState.error) {
        if (jobState.error != null) {
            showSnackbar(jobState.error)
            resetError()
        }
    }

    jobState.job?.let { job ->
        Scaffold(
            topBar = {
                JobDetailsTopBar(
                    isCurrentUser = isCurrentUser,
                    job = job, isInDeadline = jobState.isInDeadline, onBackClick = onBackClick,
                    onDeleteJob = onDeleteJob
                )
            }, bottomBar = {
                JobDetailsBottomBar(
                    isInDeadline = jobState.isInDeadline,
                    deadline = job.applicationDeadline,
                    onApplyClick = { onApplyClick(job._id) },
                    alreadyApplied = alreadyApplied,
                    appliedAt = if (alreadyApplied) formatDateForDisplay(job.applications.first().appliedAt.toString()) else null
                )
            }, containerColor = MaterialTheme.colorScheme.background
        ) { innerPadding ->

            Column(
                modifier = Modifier
                    .padding(innerPadding)
                    .padding(horizontal = 20.dp, vertical = 12.dp)
                    .verticalScroll(rememberScrollState())
            ) {
                val spacing = 12.dp

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    InfoLabelWithChip(
                        label = "Job Type",
                        value = job.jobType.capitalize(),
                        labelStyle = MaterialTheme.typography.titleMedium,
                        labelColor = MaterialTheme.colorScheme.onBackground,
                        modifier = Modifier.weight(1f)
                    )
                    InfoLabelWithChip(
                        label = "Location",
                        value = job.location.capitalize(),
                        labelStyle = MaterialTheme.typography.titleMedium,
                        labelColor = MaterialTheme.colorScheme.onBackground,
                        modifier = Modifier.weight(1f)
                    )
                }

                Spacer(Modifier.height(6.dp))

                InfoLabelWithChip(
                    label = "Experience",
                    value = job.experienceLevel.capitalize(),
                    labelStyle = MaterialTheme.typography.titleMedium,
                    labelColor = MaterialTheme.colorScheme.onBackground
                )
                Spacer(Modifier.height(spacing))

                InfoLabelWithChip(
                    label = "Batch / Graduation Year",
                    value = job.graduationYear.toString(),
                    labelStyle = MaterialTheme.typography.titleMedium,
                    labelColor = MaterialTheme.colorScheme.onBackground
                )
                Spacer(Modifier.height(spacing))

                InfoLabel(
                    label = "Required Skills",
                    labelStyle = MaterialTheme.typography.titleMedium,
                    labelColor = MaterialTheme.colorScheme.onBackground
                )
                FlowRow { job.requiredSkills.forEach { CustomChip(value = it) } }
                Spacer(Modifier.height(spacing))

                InfoLabel(
                    label = "Required Education",
                    labelStyle = MaterialTheme.typography.titleMedium,
                    labelColor = MaterialTheme.colorScheme.onBackground
                )
                FlowRow {
                    job.requiredEducation.forEach {
                        CustomChip(value = "${it.degree} - ${it.branch}")
                    }
                }
                Spacer(Modifier.height(spacing))

                InfoLabel(
                    label = "Description",
                    labelStyle = MaterialTheme.typography.titleMedium,
                    labelColor = MaterialTheme.colorScheme.onBackground
                )
                JobDescriptionCard(job.description)
                Spacer(Modifier.height(spacing))

                InfoLabel(
                    label = "Benefits Offered",
                    labelStyle = MaterialTheme.typography.titleMedium,
                    labelColor = MaterialTheme.colorScheme.onBackground
                )
                ListWithBullets(
                    value = job.benefitsOffered, modifier = Modifier.fillMaxWidth()
                )

                Spacer(Modifier.height(spacing))

                InfoLabel(
                    label = "Posted By",
                    labelStyle = MaterialTheme.typography.titleMedium,
                    labelColor = MaterialTheme.colorScheme.onBackground
                )
                Spacer(Modifier.height(spacing))
                job.postedBy?.let {
                    PostedByCard(
                        postedBy = job.postedBy,
                    ){
                        onUserClick(job.postedBy._id)
                    }
                }
            }
        }
    }
}

@Composable
fun PostedByCard(
    postedBy: PostedBy,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {}
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .clickable { onClick() },
        shape = MaterialTheme.shapes.small
    ) {
        Row(
            modifier = Modifier.padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Surface(
                modifier = Modifier.size(50.dp),
                shape = MaterialTheme.shapes.small,
                color = MaterialTheme.colorScheme.primary.copy(alpha = 0.2f)
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Text(
                        text = postedBy.name.first().toString(),
                        style = MaterialTheme.typography.headlineMedium,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            }
            Spacer(Modifier.width(12.dp))
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = postedBy.name.capitalize(),
                    style = MaterialTheme.typography.titleMedium
                )
                Text(
                    text = postedBy.email.toString(),
                    style = MaterialTheme.typography.bodyMedium,
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun JobDetailsTopBar(
    job: Job,
    isInDeadline: Boolean,
    onBackClick: () -> Unit,
    isCurrentUser: Boolean,
    onDeleteJob: () -> Unit
) {

    var showDialogBox = remember { false }

    TopAppBar(modifier = Modifier.zIndex(1f), title = {
        Column {
            Text(job.title, style = MaterialTheme.typography.titleLarge)
            Text(job.company, style = MaterialTheme.typography.labelLarge, color = Color.Gray)
        }
    }, navigationIcon = {
        IconButton(onClick = onBackClick) {
            Icon(Icons.AutoMirrored.Rounded.ArrowBack, contentDescription = "Back")
        }
    }, actions = {
        CustomChip(
            value = if (isInDeadline) "Open" else "Closed",
            color = if (isInDeadline) Color.Green.copy(0.3f) else Color.Red.copy(0.3f),
            modifier = Modifier.padding(horizontal = 8.dp)
        )
        if (isCurrentUser) {
            IconButton(onClick = {
                showDialogBox = !showDialogBox
            }) {
                Icon(
                    imageVector = Icons.Rounded.Delete,
                    contentDescription = "Delete Job"
                )
            }
        }
    })

    if(showDialogBox) {
        AlertDialog(
            shape = MaterialTheme.shapes.small,
            onDismissRequest = { showDialogBox = false },
            confirmButton = {
                TextButton(onClick = onDeleteJob) {
                    Text("Delete")
                }
            },
            text = {
                Text("Are you sure you want to delete this job?")
            },
            dismissButton = {
                TextButton(onClick = { showDialogBox = false }) {
                    Text("Cancel")
                }
            }
        )
    }
}

@Composable
private fun JobDetailsBottomBar(
    isInDeadline: Boolean,
    deadline: String,
    onApplyClick: () -> Unit,
    alreadyApplied: Boolean,
    appliedAt: String? = null
) {
    Column(
        modifier = Modifier.padding(8.dp), horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (isInDeadline) {
            Text(
                text = "Registration Ends on ${formatDateForDisplay(deadline)}",
                style = MaterialTheme.typography.labelMedium,
                color = Color.Gray
            )
        }
        Button(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            shape = MaterialTheme.shapes.small,
            onClick = onApplyClick,
            enabled = isInDeadline && !alreadyApplied
        ) {
            Text(
                if (!isInDeadline) {
                    "Registration Ended on ${formatDateForDisplay(deadline)}"
                } else if (alreadyApplied) {
                ("Applied on $appliedAt")
                } else {
                    "Apply Now"
                }
            )
        }
    }
}

@Composable
private fun JobDescriptionCard(description: String) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp),
        shape = MaterialTheme.shapes.small,
        elevation = CardDefaults.cardElevation(1.dp),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.primaryContainer),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.background)
    ) {
        Text(
            text = description,
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            textAlign = TextAlign.Justify
        )
    }
}

