package com.abhay.alumniconnect.presentation.screens.job.application

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.abhay.alumniconnect.presentation.components.CustomOutlinedTextField
import com.example.compose.AlumniConnectTheme
import kotlinx.coroutines.delay

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun JobApplicationScreen(
    state: JobApplicationState,
    onEvent: (JobApplicationEvents) -> Unit = { },
    showSnackbar: (String) -> Unit = {},
    onBackClick: () -> Unit,
    navigateAndPopUp: (Any, Any) -> Unit = {route, popUp -> }
) {

    LaunchedEffect(state.message) {
        if (state.message != null) {
            showSnackbar(state.message)
            onEvent(JobApplicationEvents.ResetErrorMessage)
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp),
    ) {
        TopAppBar(modifier = Modifier.align(Alignment.TopCenter), title = {
            Text(
                text = "Job Application", style = MaterialTheme.typography.titleLarge
            )
        }, navigationIcon = {
            IconButton(onClick = onBackClick) {
                Icon(
                    Icons.AutoMirrored.Rounded.ArrowBack, contentDescription = "Back"
                )
            }
        })

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 110.dp)
        ) {
            CustomOutlinedTextField(
                value = state.resumeLink,
                onValueChange = { onEvent(JobApplicationEvents.UpdateResumeLink(it)) },
                label = "Resume Link",
                isRequired = true,
                isError = state.resumeError
            )
            CustomOutlinedTextField(
                value = state.coverLetter,
                onValueChange = { onEvent(JobApplicationEvents.UpdateCoverLetter(it)) },
                label = "Cover Letter (Optional)"
            )
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .imePadding()
        ) {

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
                    modifier = Modifier.padding(8.dp),
                    text = "Note: Make sure that your resume is up to date with your skills and " + "other details along with your " +
                            "user profile here, so that the job poster can review your application properly",
                    style = MaterialTheme.typography.bodySmall
                )
            }

            Button(
                modifier = Modifier.fillMaxWidth(),
                shape = MaterialTheme.shapes.small,
                onClick = { onEvent(JobApplicationEvents.ApplyForJob(navigateAndPopUp = navigateAndPopUp)) },
                enabled = state.resumeLink.isNotEmpty()
            ) {
                Text(
                    text = "Submit Application"
                )
            }
        }
    }
}

@Preview(
    showBackground = true
)
@Composable
private fun ApplicationScreenPreview() {
    AlumniConnectTheme {
        JobApplicationScreen(JobApplicationState(), {}, {}, {})
    }
}
