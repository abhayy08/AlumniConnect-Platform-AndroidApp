package com.abhay.alumniconnect.presentation.screens.job.create_job

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.abhay.alumniconnect.data.remote.dto.job.RequiredEducation
import com.abhay.alumniconnect.presentation.components.ChipsInputField
import com.abhay.alumniconnect.presentation.components.CustomChipWithDeleteOption
import com.abhay.alumniconnect.presentation.components.DatePickerTextField
import com.abhay.alumniconnect.utils.capitalize
import com.example.compose.AlumniConnectTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateJobScreen(
    newJobState: NewJobState,
    onEvent: (CreateJobScreenActions) -> Unit,
    onBackClick: () -> Unit = {},
    showSnackbar: (String) -> Unit = {}
) {

    LaunchedEffect(newJobState.message) {
        if(newJobState.message != null){
            showSnackbar(newJobState.message)
            onEvent(CreateJobScreenActions.resetError)
        }
    }

    val scrollState = rememberScrollState()

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        TopAppBar(
            modifier = Modifier
                .zIndex(1f)
                .align(Alignment.TopCenter),
            title = { Text("Post New Job") },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = MaterialTheme.colorScheme.primaryContainer,
                titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer
            ),
            navigationIcon = {
                IconButton(onClick = onBackClick) {
                    Icon(Icons.AutoMirrored.Rounded.ArrowBack, contentDescription = "Close")
                }
            }
        )
        Column(
            modifier = Modifier
                .padding(top = TopAppBarDefaults.MediumAppBarExpandedHeight)
                .padding(8.dp)
                .fillMaxSize()
                .imePadding()
                .verticalScroll(scrollState),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Title
            OutlinedTextField(
                value = newJobState.title ,
                onValueChange = { onEvent(CreateJobScreenActions.onTitleChange(it)) },
                label = { Text("Job Title *") },
                modifier = Modifier.fillMaxWidth(),
                shape = MaterialTheme.shapes.small,
                isError = newJobState.title.isEmpty()
            )

            // Company
            OutlinedTextField(
                value = newJobState.company,
                onValueChange = { onEvent(CreateJobScreenActions.onCompanyChange(it)) },
                label = { Text("Company *") },
                modifier = Modifier.fillMaxWidth(),
                shape = MaterialTheme.shapes.small,
                isError = newJobState.company.isEmpty()
            )

            // Description
            OutlinedTextField(
                value = newJobState.description,
                onValueChange = { onEvent(CreateJobScreenActions.onDescriptionChange(it)) },
                label = { Text("Description *") },
                modifier = Modifier
                    .fillMaxWidth()
                    .defaultMinSize(minHeight = 120.dp),
                shape = MaterialTheme.shapes.small,
                isError = newJobState.description.isEmpty()
            )

            // Location
            val locationOptions = listOf("remote", "in-office", "hybrid")
            Text("Location", style = MaterialTheme.typography.bodyLarge)
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                locationOptions.forEach { location ->
                    FilterChip(
                        selected = newJobState.location == location,
                        onClick = {
                            onEvent(CreateJobScreenActions.onLocationChange(location))
                        },
                        label = { Text(location.capitalize()) },
                        shape = MaterialTheme.shapes.small
                    )
                }
            }

            // Job Type
            val jobTypeOptions = listOf("full-time", "part-time", "contract", "internship")
            Text("Job Type", style = MaterialTheme.typography.bodyLarge)
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                jobTypeOptions.forEach { jobType ->
                    FilterChip(
                        selected = newJobState.jobType == jobType,
                        onClick = { onEvent(CreateJobScreenActions.onJobTypeChange(jobType)) },
                        label = { Text(jobType.capitalize()) },
                        shape = MaterialTheme.shapes.small
                    )
                }
            }

            // Experience Level
            val experienceLevelOptions = listOf("entry", "mid", "senior")
            Text("Experience Level", style = MaterialTheme.typography.bodyLarge)
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                experienceLevelOptions.forEach { level ->
                    FilterChip(
                        selected = newJobState.experienceLevel == level,
                        onClick = { onEvent(CreateJobScreenActions.onExperienceLevelChange(level)) },
                        label = { Text(level.capitalize()) },
                        shape = MaterialTheme.shapes.small
                    )
                }
            }

            // Min Experience
            OutlinedTextField(
                value = newJobState.minExperience,
                onValueChange = { onEvent(CreateJobScreenActions.onMinExperienceChange(it)) },
                label = { Text("Minimum Experience (years) *") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.fillMaxWidth(),
                shape = MaterialTheme.shapes.small,
                isError = newJobState.minExperience.toString().isEmpty()
            )

            // Application Deadline
            DatePickerTextField(
                label = "Application Deadline *",
                value = newJobState.applicationDeadline,
                onDateSelected = { date ->
                    onEvent(CreateJobScreenActions.onApplicationDeadlineChange(date))
                },
                isError = newJobState.applicationDeadline.isEmpty()
            )

            // Required Skills
            ChipsInputField(
                label = "Skills *",
                initialValue = newJobState.requiredSkills,
                onValueChanged = { onEvent(CreateJobScreenActions.onRequiredSkillsChange(it)) },
                isError = newJobState.requiredSkills.isEmpty()
            )

            // Required Education
            EducationSection(
                education = newJobState.requiredEducation,
                onEducationChanged = { onEvent(CreateJobScreenActions.onRequiredEducationChange(it)) })

            // Graduation Year
            OutlinedTextField(
                value = newJobState.graduationYear,
                onValueChange = { onEvent(CreateJobScreenActions.onGraduationYearChange(it)) },
                label = { Text("Graduation Year *") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.fillMaxWidth(),
                shape = MaterialTheme.shapes.small,
                isError = newJobState.graduationYear.toString().isEmpty()
            )

            // Benefits Offered
            BenefitsSection(
                benefits = newJobState.benefitsOffered,
                onBenefitsChanged = { onEvent(CreateJobScreenActions.onBenefitsOfferedChange(it)) })

            // Submit Button
            Button(
                onClick = { onEvent(CreateJobScreenActions.onCreateJob(onBackClick)) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp),
                shape = MaterialTheme.shapes.small
            ) {
                Text("Post Job")
            }
        }
    }

}

@Composable
fun EducationSection(
    education: List<RequiredEducation>, onEducationChanged: (List<RequiredEducation>) -> Unit
) {
    var degree by remember { mutableStateOf("") }
    var branch by remember { mutableStateOf("") }

    Column(modifier = Modifier.fillMaxWidth()) {
        Text("Required Education *", style = MaterialTheme.typography.bodyLarge)

        OutlinedTextField(
            value = degree,
            onValueChange = { degree = it },
            label = { Text("Degree (e.g., Bachelors, Masters)") },
            modifier = Modifier.fillMaxWidth(),
            shape = MaterialTheme.shapes.small
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = branch,
            onValueChange = { branch = it },
            label = { Text("Branch (e.g., CSE, ECE)") },
            modifier = Modifier.fillMaxWidth(),
            shape = MaterialTheme.shapes.small
        )

        Spacer(modifier = Modifier.height(8.dp))

        Button(
            onClick = {
                if (degree.isNotBlank() && branch.isNotBlank()) {
                    onEducationChanged(education + RequiredEducation(degree, branch))
                    degree = ""
                    branch = ""
                }
            }, modifier = Modifier.align(Alignment.End), shape = MaterialTheme.shapes.small
        ) {
            Text("Add Education")
        }

        Spacer(modifier = Modifier.height(8.dp))

        education.forEachIndexed { index, edu ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp),
                shape = MaterialTheme.shapes.small
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(edu.degree, style = MaterialTheme.typography.bodyLarge)
                        Text(edu.branch, style = MaterialTheme.typography.bodyMedium)
                    }
                    IconButton(onClick = {
                        onEducationChanged(education.filterIndexed { i, _ -> i != index })
                    }) {
                        Icon(
                            imageVector = Icons.Default.Delete,
                            contentDescription = "Remove Education"
                        )
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun BenefitsSection(
    benefits: List<String>, onBenefitsChanged: (List<String>) -> Unit
) {
    var newBenefit by remember { mutableStateOf("") }

    Column(modifier = Modifier.fillMaxWidth()) {
        Text("Benefits Offered *", style = MaterialTheme.typography.bodyLarge)
        Row(
            modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically
        ) {
            OutlinedTextField(
                value = newBenefit,
                onValueChange = { newBenefit = it },
                label = { Text("Add Benefit") },
                modifier = Modifier.weight(1f),
                shape = MaterialTheme.shapes.small
            )
            Spacer(modifier = Modifier.width(8.dp))
            IconButton(onClick = {
                if (newBenefit.isNotBlank()) {
                    onBenefitsChanged(benefits + newBenefit)
                    newBenefit = ""
                }
            }) {
                Icon(
                    imageVector = Icons.Default.Add, contentDescription = "Add Benefit"
                )
            }
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp),
        ) {
            benefits.forEachIndexed { index, benefit ->
                CustomChipWithDeleteOption(
                    label = benefit,
                    onDelete = { onBenefitsChanged(benefits.filterIndexed { i, _ -> i != index }) }
                )
            }
        }
    }
}

@Preview(
    showBackground = true
)
@Composable
private fun CreateJobPreview() {
    AlumniConnectTheme {
        CreateJobScreen(
            newJobState =
                NewJobState(
                    applicationDeadline = "2025-04-08T00:00:00.000Z",
                    benefitsOffered = listOf(),
                    company = "solet",
                    description = "litora",
                    experienceLevel = "entry",
                    graduationYear = "2021",
                    jobType = "full-time",
                    location = "in-office",
                    minExperience = "2",
                    requiredEducation = listOf(RequiredEducation("B.Tech", "CSE")),
                    requiredSkills = listOf("Kotlin", "Android", "Jetpack Compose"),
                    title = "eros",
                ), onEvent = {}, showSnackbar = {}
        )
    }
}