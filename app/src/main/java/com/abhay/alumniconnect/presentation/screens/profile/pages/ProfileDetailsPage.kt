package com.abhay.alumniconnect.presentation.screens.profile.pages

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.abhay.alumniconnect.data.remote.dto.WorkExperience
import com.abhay.alumniconnect.domain.model.User
import com.abhay.alumniconnect.presentation.screens.profile.AccountCreationInfo
import com.abhay.alumniconnect.presentation.screens.profile.AchievementsSection
import com.abhay.alumniconnect.presentation.screens.profile.BioSection
import com.abhay.alumniconnect.presentation.screens.profile.JobAndLinkedInSection
import com.abhay.alumniconnect.presentation.screens.profile.SkillsAndInterestsSection
import com.abhay.alumniconnect.presentation.screens.profile.WorkExperienceSection


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
