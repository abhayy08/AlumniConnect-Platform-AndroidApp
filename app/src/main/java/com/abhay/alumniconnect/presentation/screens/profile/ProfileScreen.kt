package com.abhay.alumniconnect.presentation.screens.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Edit
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.abhay.alumniconnect.data.remote.dto.WorkExperience
import com.abhay.alumniconnect.domain.model.User
import com.abhay.alumniconnect.presentation.components.CustomChip
import com.abhay.alumniconnect.presentation.navigation.graphs.dummyUser
import com.example.compose.AlumniConnectTheme
import com.example.ui.theme.AppShapes
import java.time.Year

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun ProfileScreen(
    profileUiState: ProfileUiState,
    onEditClick: () -> Unit,
    onConnectionsClick: () -> Unit,
    onAddExperienceClick: () -> Unit = {},
    onExperienceEditClick: (WorkExperience) -> Unit = {}
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.Start
    ) {
        profileUiState.user?.let { user ->
            ProfileHeader(user, onConnectionsClick)
            Spacer(modifier = Modifier.height(12.dp))

            BioSection(user.bio)
            Spacer(modifier = Modifier.height(12.dp))

            JobAndLinkedInSection(user)
            Spacer(modifier = Modifier.height(12.dp))

            if (user.achievements.isNotEmpty()) {
                AchievementsSection(user.achievements)
                Spacer(modifier = Modifier.height(12.dp))
            }

            if (user.skills.isNotEmpty() || user.interests.isNotEmpty()) {
                SkillsAndInterestsSection(user.skills, user.interests)
                Spacer(modifier = Modifier.height(12.dp))
            }

            if (user.workExperience.isNotEmpty()) {
                WorkExperienceSection(user.workExperience, onExperienceEditClick = { onExperienceEditClick(it) } )
                Spacer(modifier = Modifier.height(16.dp))
            }

            EditProfileAndAddExperienceButton(onEditClick, onAddExperienceClick)
            Spacer(modifier = Modifier.height(16.dp))

            AccountCreationInfo(user.createdAt)
        }
    }
}

@Composable
fun ProfileHeader(user: User, onConnectionsClick: () -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start
    ) {
        Box(
            modifier = Modifier
                .size(100.dp)
                .clip(MaterialTheme.shapes.large)
                .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.2f)),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = user.name.first().toString(),
                style = MaterialTheme.typography.headlineMedium,
                color = MaterialTheme.colorScheme.primary
            )
        }

        Spacer(modifier = Modifier.width(16.dp))

        Column(modifier = Modifier.weight(1f)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = user.name,
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = if (user.graduationYear <= Year.now().value) "✅ Graduated" else "⏳ Studying",
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.Gray
                )
            }

            Text(
                text = "${user.university}, Class of ${user.graduationYear}",
                style = MaterialTheme.typography.bodySmall,
                color = Color.Gray
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = "${user.connections.size} Connections",
                style = MaterialTheme.typography.bodySmall,
                fontWeight = FontWeight.Medium,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.clickable { onConnectionsClick() })
        }
    }
}

@Composable
fun BioSection(bio: String) {
    Text(
        text = bio, style = MaterialTheme.typography.bodyMedium, textAlign = TextAlign.Start
    )
}

@Composable
fun JobAndLinkedInSection(user: User) {
    Column {
        if (user.currentJob.isNotEmpty()) {
            Text(
                text = "💼 ${user.jobTitle} at ${if (user.company.isNotEmpty()) user.company else "N/A"}",
                style = MaterialTheme.typography.bodyLarge
            )
        }

        if (user.linkedInProfile.isNotEmpty()) {
            Text(
                text = "🔗 LinkedIn Profile",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.clickable { /* Open LinkedIn */ })
        }
    }
}

@Composable
fun AchievementsSection(achievements: List<String>) {
    Column {
        Text(
            text = "🏆 Achievements",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.align(Alignment.Start)
        )
        Spacer(Modifier.height(6.dp))
        achievements.forEach { achievement ->
            Text(
                text = "• $achievement", style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun SkillsAndInterestsSection(skills: List<String>, interests: List<String>) {
    Column {
        if (skills.isNotEmpty()) {
            Text(
                text = "💡 Skills",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.align(Alignment.Start)
            )
            Spacer(Modifier.height(6.dp))
            FlowRow {
                skills.forEach { skill -> CustomChip(label = skill) }
            }
        }

        if (interests.isNotEmpty()) {
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "🎯 Interests",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.align(Alignment.Start)
            )
            Spacer(Modifier.height(6.dp))
            FlowRow {
                interests.forEach { interest -> CustomChip(label = interest) }
            }
        }
    }
}

@Composable
fun WorkExperienceSection(
    workExperience: List<WorkExperience>,
    onExperienceEditClick: (WorkExperience) -> Unit
) {
    Column {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "Work Experience",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier
                    .padding(vertical = 8.dp)
            )

        }
        workExperience.forEach { work ->
            WorkExperienceItem(work, onExperienceEditClick = { onExperienceEditClick(work) })
            HorizontalDivider(
                modifier = Modifier
                    .fillMaxWidth(0.89f)
                    .align(Alignment.CenterHorizontally)
            )
        }
    }
}

@Composable
fun EditProfileAndAddExperienceButton(
    onEditClick: () -> Unit, onEditExperienceClick: () -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth()
    ) {
        Button(
            onClick = onEditClick,
            shape = MaterialTheme.shapes.medium,
            modifier = Modifier.weight(1f)
        ) {
            Text("Edit Profile")
        }
        Spacer(Modifier.width(14.dp))
        Button(
            onClick = onEditExperienceClick,
            shape = MaterialTheme.shapes.medium,
            modifier = Modifier.weight(1f)
        ) {
            Text("Add Experience")
        }
    }
}

@Composable
fun AccountCreationInfo(createdAt: String) {
    Text(
        text = "Joined on ${createdAt.substring(0, 10)}",
        style = MaterialTheme.typography.bodySmall,
        color = Color.Gray
    )
}

@Composable
fun WorkExperienceItem(
    work: WorkExperience,
    onExperienceEditClick: () -> Unit
) {
    Card(
        shape = AppShapes.small,
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    Text(
                        text = work.position,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = work.company, style = MaterialTheme.typography.bodyMedium, color = Color.Gray
                    )
                }

                IconButton(
                    onClick = onExperienceEditClick
                ) {
                    Icon(Icons.Rounded.Edit, contentDescription = "Edit Experience")
                }
            }
            work.description?.let {
                Text(
                    text = it,
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(top = 4.dp)
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ProfileScreenPreview() {
    AlumniConnectTheme {
        ProfileScreen(
            profileUiState = ProfileUiState(user = dummyUser),
            onEditClick = {},
            onConnectionsClick = {})
    }
}