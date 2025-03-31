package com.abhay.alumniconnect.presentation.screens.profile.editprofilescreen

import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.abhay.alumniconnect.presentation.components.Chip
import com.abhay.alumniconnect.presentation.components.ChipsInputField
import com.abhay.alumniconnect.presentation.components.CustomOutlinedTextField
import com.example.compose.AlumniConnectTheme

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun EditProfileScreen(
    editProfileState: EditProfileState,
    uiState: EditProfileUiState,
    onEvent: (EditProfileActions) -> Unit,
    onBackClick: () -> Unit
) {

    val scrollState = rememberScrollState()
    val context = LocalContext.current

    LaunchedEffect(uiState) {
        when (uiState) {
            is EditProfileUiState.Error -> {
                Toast.makeText(context, uiState.message, Toast.LENGTH_SHORT).show()
            }

            else -> {}
        }
    }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(scrollState)
                    .padding(16.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(
                        onClick = onBackClick
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Rounded.ArrowBack,
                            contentDescription = "Navigate Back"
                        )
                    }
                    Text(
                        text = "Edit Profile",
                        style = MaterialTheme.typography.headlineMedium,
                    )
                }

                CustomOutlinedTextField(
                    value = editProfileState.bio,
                    onValueChange = { onEvent(EditProfileActions.UpdateBio(it)) },
                    label = "Bio",
                    modifier = Modifier.fillMaxWidth(),
                    maxLines = 5
                )

                CustomOutlinedTextField(
                    value = editProfileState.linkedInProfile,
                    onValueChange = { onEvent(EditProfileActions.UpdateLinkedInProfile(it)) },
                    label = "LinkedIn Profile",
                    modifier = Modifier.fillMaxWidth(),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Uri,
                        imeAction = ImeAction.Next
                    )
                )

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                ) {
                    Text(
                        text = "Achievements",
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.padding(bottom = 4.dp)
                    )

                    var achievementInput by remember { mutableStateOf("") }
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        CustomOutlinedTextField(
                            value = achievementInput,
                            onValueChange = { achievementInput = it },
                            label = "Add an achievement",
                            modifier = Modifier
                                .weight(1f)
                                .padding(end = 8.dp),
                            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                            keyboardActions = KeyboardActions(
                                onDone = {
                                    if (achievementInput.isNotBlank()) {
                                        onEvent(EditProfileActions.AddAchievement(achievementInput))
                                        achievementInput = ""
                                    }
                                }
                            )
                        )
                        IconButton(
                            onClick = {
                                if (achievementInput.isNotBlank()) {
                                    onEvent(EditProfileActions.AddAchievement(achievementInput))
                                    achievementInput = ""
                                }
                            }
                        ) {
                            Icon(
                                imageVector = Icons.Default.Add,
                                contentDescription = "Add Achievement"
                            )
                        }
                    }

                    FlowRow(modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp)) {
                        editProfileState.achievements.forEach { achievement ->
                            Chip(
                                label = achievement,
                                onDelete = {
                                    onEvent(
                                        EditProfileActions.RemoveAchievement(
                                            achievement
                                        )
                                    )
                                }
                            )
                        }
                    }
                }

                ChipsInputField(
                    label = "Skills",
                    initialValue = editProfileState.skills,
                    onValueChanged = { onEvent(EditProfileActions.UpdateSkills(it)) }
                )

                Spacer(modifier = Modifier.height(16.dp))

                ChipsInputField(
                    label = "Interests",
                    initialValue = editProfileState.interests,
                    onValueChanged = { onEvent(EditProfileActions.UpdateInterests(it)) }
                )

                Spacer(modifier = Modifier.height(24.dp))

                Button(
                    onClick = {
                        onEvent(EditProfileActions.SaveProfile(popBack = { onBackClick() }))
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 16.dp),
                    enabled = uiState != EditProfileUiState.Loading,
                    shape = MaterialTheme.shapes.medium
                ) {
                    Text("Save Changes")
                }
            }

            // Loading indicator
            if (uiState == EditProfileUiState.Loading) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }
        }
    }
}

@Preview
@Composable
private fun EditProfileScreenPreview() {
    AlumniConnectTheme {
        EditProfileScreen(
            editProfileState = EditProfileState(),
            uiState = EditProfileUiState.Success,
            onEvent = {},
            onBackClick = {}
        )
    }
}