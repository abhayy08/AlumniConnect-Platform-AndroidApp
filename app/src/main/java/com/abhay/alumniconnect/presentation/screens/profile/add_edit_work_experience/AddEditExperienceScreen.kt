package com.abhay.alumniconnect.presentation.screens.profile.add_edit_work_experience

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.abhay.alumniconnect.presentation.components.CustomOutlinedTextField
import com.abhay.alumniconnect.presentation.components.DatePickerTextField

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddEditExperienceScreen(
    state: AddEditExperienceState = AddEditExperienceState(),
    uiState: AddEditExperienceUiState = AddEditExperienceUiState.Loading,
    onEvent: (AddEditExperienceActions) -> Unit,
    onBackClick: () -> Unit = {},
    showSnackbar: (String) -> Unit
) {

    LaunchedEffect(uiState) {
        when (uiState) {
            is AddEditExperienceUiState.Error -> {
                if(uiState.message.isNotEmpty()) {
                    showSnackbar(uiState.message)
                }
            }

            else -> {}
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = if (state.isEditMode) "Edit Experience" else "Add Experience",
                        style = MaterialTheme.typography.headlineSmall,
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Rounded.ArrowBack,
                            contentDescription = "Navigate Back"
                        )
                    }
                },
                actions = {
                    if (state.isEditMode) {
                        IconButton(onClick = { onEvent(AddEditExperienceActions.onDeleteExperience(onBackClick)) }) {
                            Icon(
                                imageVector = Icons.Rounded.Delete,
                                contentDescription = "Delete Experience"
                            )
                        }
                    }
                }
            )
        },
        content = { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(16.dp)
            ) {
                CustomOutlinedTextField(
                    value = state.position,
                    onValueChange = { onEvent(AddEditExperienceActions.onPositionChange(it)) },
                    label = "Position",
                    modifier = Modifier.fillMaxWidth()
                )

                CustomOutlinedTextField(
                    value = state.company,
                    onValueChange = { onEvent(AddEditExperienceActions.onCompanyChange(it)) },
                    label = "Company",
                    modifier = Modifier.fillMaxWidth()
                )

                DatePickerTextField(
                    label = "Start Date", value = state.startDate, onDateSelected = { selectedDate ->
                        onEvent(AddEditExperienceActions.onStartDateChange(selectedDate))
                    }, modifier = Modifier.fillMaxWidth()
                )

                DatePickerTextField(
                    label = "End Date", value = state.endDate, onDateSelected = { selectedDate ->
                        onEvent(AddEditExperienceActions.onEndDateChange(selectedDate))
                    }, modifier = Modifier.fillMaxWidth()
                )

                CustomOutlinedTextField(
                    value = state.description,
                    onValueChange = { onEvent(AddEditExperienceActions.onDescriptionChange(it)) },
                    label = "Description",
                    modifier = Modifier.fillMaxWidth(),
                    maxLines = 4
                )

                Spacer(Modifier.height(8.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Button(
                        onClick = {
                            onEvent(AddEditExperienceActions.onCancel(onBackClick))
                        },
                        shape = MaterialTheme.shapes.medium,
                        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error)
                    ) {
                        Text("Cancel")
                    }

                    Button(
                        onClick = {
                            onEvent(AddEditExperienceActions.onSaveExperience(onBackClick))
                        }, shape = MaterialTheme.shapes.medium
                    ) {
                        Text("Save Changes")
                    }
                }
            }

            // Loading indicator
            if (uiState == AddEditExperienceUiState.Loading) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ){
                    CircularProgressIndicator()
                }
            }
        }
    )
}

@Preview(
    showBackground = true
)
@Composable
private fun AddEditWorkExperiencePreview() {
    AddEditExperienceScreen(
        onEvent = {},
        showSnackbar = {}
    )
}
