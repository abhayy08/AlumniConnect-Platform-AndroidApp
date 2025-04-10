package com.abhay.alumniconnect.presentation.screens.profile

import androidx.compose.foundation.ExperimentalFoundationApi
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.Edit
import androidx.compose.material.icons.rounded.GridView
import androidx.compose.material.icons.rounded.Person
import androidx.compose.material.icons.rounded.Work
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.abhay.alumniconnect.data.remote.dto.Job
import com.abhay.alumniconnect.data.remote.dto.WorkExperience
import com.abhay.alumniconnect.domain.model.User
import com.abhay.alumniconnect.presentation.components.CustomChip
import com.abhay.alumniconnect.presentation.dummyJobs
import com.abhay.alumniconnect.presentation.dummyUser
import com.abhay.alumniconnect.presentation.screens.job.components.JobCard
import com.example.compose.AlumniConnectTheme
import com.example.ui.theme.AppShapes
import kotlinx.coroutines.launch
import java.time.Year

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ProfileScreen(
    profileState: ProfileState,
    jobsState: List<Job>,
    uiState: ProfileUiState,
    onProfileEditClick: () -> Unit,
    onConnectionsClick: () -> Unit,
    onAddExperienceClick: () -> Unit = {},
    onExperienceEditClick: (WorkExperience) -> Unit = {},
    showSnackbar: (String) -> Unit = {},
) {

    LaunchedEffect(uiState) {
        when (uiState) {
            is ProfileUiState.Error -> {
                showSnackbar(uiState.message)
            }

            is ProfileUiState.Success -> {
                if (uiState.message != null) showSnackbar(uiState.message)
            }

            else -> {}
        }
    }

    val pagerState = rememberPagerState(pageCount = { 3 })
    val coroutineScope = rememberCoroutineScope()

    if (uiState != ProfileUiState.Loading) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
        ) {
            profileState.user?.let { user ->
                // Profile header is always visible
                Column(modifier = Modifier.padding(16.dp)) {
                    ProfileHeader(user, onConnectionsClick, onProfileEditClick)
                }

                // Tab row
                TabRow(
                    selectedTabIndex = pagerState.currentPage,
                ) {
                    ProfileTab(
                        icon = Icons.Rounded.Person,
                        title = "Profile",
                        selected = pagerState.currentPage == 0,
                        onClick = { coroutineScope.launch { pagerState.animateScrollToPage(0) } })
                    ProfileTab(
                        icon = Icons.Rounded.Work,
                        title = "Jobs",
                        selected = pagerState.currentPage == 1,
                        onClick = { coroutineScope.launch { pagerState.animateScrollToPage(1) } })
                    ProfileTab(
                        icon = Icons.Rounded.GridView,
                        title = "Posts",
                        selected = pagerState.currentPage == 2,
                        onClick = { coroutineScope.launch { pagerState.animateScrollToPage(2) } })
                }

                // Horizontal pager content
                HorizontalPager(
                    state = pagerState, modifier = Modifier.weight(1f)
                ) { page ->
                    when (page) {
                        0 -> ProfileDetailsPage(
                            user = user,
                            onAddExperienceClick = onAddExperienceClick,
                            onExperienceEditClick = onExperienceEditClick
                        )

                        1 -> JobsPostedPage(jobs = jobsState)
                        2 -> UserPostsPage()
                    }
                }
            }
        }
    } else {
        Box(
            modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
    }
}

@Composable
fun ProfileTab(
    icon: ImageVector, title: String, selected: Boolean, onClick: () -> Unit
) {
    Tab(
        selected = selected,
        onClick = onClick,
        text = {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Icon(
                    imageVector = icon, contentDescription = title, modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(text = title, style = MaterialTheme.typography.bodyMedium)
            }
        },
        selectedContentColor = MaterialTheme.colorScheme.primary,
        unselectedContentColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
    )
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun ProfileDetailsPage(
    user: User, onAddExperienceClick: () -> Unit, onExperienceEditClick: (WorkExperience) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.Start
    ) {
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
            WorkExperienceSection(
                user.workExperience,
                onExperienceEditClick = { onExperienceEditClick(it) },
                onAddExperienceClick = onAddExperienceClick
            )
            Spacer(modifier = Modifier.height(16.dp))
        }

        AccountCreationInfo(user.createdAt)
    }
}

@Composable
fun JobsPostedPage(
    modifier: Modifier = Modifier,
    jobs: List<Job> = emptyList(),
) {
    if(jobs.isNotEmpty()) {
        LazyColumn(
            modifier = modifier.fillMaxSize()
        ) {
            items(jobs) {
                JobCard(
                    title = it.title,
                    company = it.company,
                    location = it.location,
                    jobType = it.jobType,
                    experienceLevel = it.experienceLevel,
                    requiredSkills = it.requiredSkills,
                    applicationDeadline = it.applicationDeadline,
                    onApplyClick = {},
                    alreadyApplied = false,
                    status = it.status,
                )
            }
        }
    }else {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp), contentAlignment = Alignment.Center
        ) {
            Text(
                text = "No posts yet. Jobs shared by you will appear here.",
                style = MaterialTheme.typography.bodyLarge,
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
            )
        }
    }
}

@Composable
fun UserPostsPage() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp), contentAlignment = Alignment.Center
    ) {
        Text(
            text = "No posts yet. Updates and content shared by you will appear here.",
            style = MaterialTheme.typography.bodyLarge,
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
        )
    }
}

@Composable
fun ProfileHeader(user: User, onConnectionsClick: () -> Unit, onProfileEditClick: () -> Unit) {
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
            Button(
                onClick = onProfileEditClick,
                shape = MaterialTheme.shapes.small,
                modifier = Modifier
                    .padding(4.dp)
                    .height(35.dp)
                    .align(Alignment.End)
            ) {
                Text("Edit Profile")
            }
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
                skills.forEach { skill -> CustomChip(value = skill) }
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
                interests.forEach { interest -> CustomChip(value = interest) }
            }
        }
    }
}

@Composable
fun WorkExperienceSection(
    workExperience: List<WorkExperience>,
    onExperienceEditClick: (WorkExperience) -> Unit,
    onAddExperienceClick: () -> Unit
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
                modifier = Modifier.padding(vertical = 8.dp)
            )
            IconButton(
                onClick = onAddExperienceClick,
            ) {
                Icon(
                    Icons.Rounded.Add, contentDescription = "Add Experience"
                )
            }
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
fun AccountCreationInfo(createdAt: String) {
    Text(
        text = "Joined on ${createdAt.substring(0, 10)}",
        style = MaterialTheme.typography.bodySmall,
        color = Color.Gray
    )
}

@Composable
fun WorkExperienceItem(
    work: WorkExperience, onExperienceEditClick: () -> Unit
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
                        text = work.company,
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.Gray
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

@OptIn(ExperimentalFoundationApi::class)
@Preview(showBackground = true)
@Composable
private fun ProfileScreenPreview() {
    AlumniConnectTheme {
        ProfileScreen(
            profileState = ProfileState(user = dummyUser),
            uiState = ProfileUiState.Success(),
            onProfileEditClick = {},
            onConnectionsClick = {},
            showSnackbar = { },
            jobsState = dummyJobs
        )
    }
}