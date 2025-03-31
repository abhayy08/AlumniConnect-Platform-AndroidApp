package com.abhay.alumniconnect.presentation.navigation.graphs

import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.abhay.alumniconnect.data.remote.dto.Connection
import com.abhay.alumniconnect.data.remote.dto.PrivacySettings
import com.abhay.alumniconnect.data.remote.dto.WorkExperience
import com.abhay.alumniconnect.domain.model.User
import com.abhay.alumniconnect.presentation.navigation.routes.Route
import com.abhay.alumniconnect.presentation.screens.event.EventsScreen
import com.abhay.alumniconnect.presentation.screens.job.JobsScreen
import com.abhay.alumniconnect.presentation.screens.main.HomeScreen
import com.abhay.alumniconnect.presentation.screens.profile.ConnectionsScreen
import com.abhay.alumniconnect.presentation.screens.profile.ProfileScreen
import com.abhay.alumniconnect.presentation.screens.profile.ProfileScreenViewModel
import com.abhay.alumniconnect.presentation.screens.profile.add_edit_work_experience.AddEditExperienceScreen
import com.abhay.alumniconnect.presentation.screens.profile.add_edit_work_experience.AddEditExperienceViewModel
import com.abhay.alumniconnect.presentation.screens.profile.editprofilescreen.EditProfileScreen
import com.abhay.alumniconnect.presentation.screens.profile.editprofilescreen.EditProfileViewModel
import com.abhay.alumniconnect.presentation.screens.search.SearchScreen
import com.abhay.alumniconnect.utils.popUp

fun NavGraphBuilder.MainNavGraph(
    navController: NavHostController
) {
    composable<Route.MainRoute.Home> {
        HomeScreen()
    }

    composable<Route.MainRoute.Search> {
        SearchScreen()
    }

    composable<Route.MainRoute.Jobs> {
        JobsScreen()
    }

    composable<Route.MainRoute.Events> {
        EventsScreen()
    }

    composable<Route.MainRoute.Profile> {
        val viewModel = hiltViewModel<ProfileScreenViewModel>()
        val profileUiState = viewModel.profileUiState.collectAsState().value

        ProfileScreen(
            profileUiState = profileUiState,
            onConnectionsClick = { navController.navigate(Route.MainRoute.Connections) },
            onEditClick = { navController.navigate(Route.MainRoute.EditProfile) },
            onAddExperienceClick = {
                navController.navigate(Route.MainRoute.AddEditExperience())
            },
            onExperienceEditClick = { experience ->
                navController.navigate(
                    Route.MainRoute.AddEditExperience(
                        experienceId = experience._id,
                        company = experience.company,
                        description = experience.description,
                        endDate = experience.endDate,
                        position = experience.position,
                        startDate = experience.startDate
                    )
                )
            }
        )
    }

    composable<Route.MainRoute.Connections> {
        ConnectionsScreen(
            connections = dummyUser.connections,
            onBackClick = { navController.navigateUp() }
        )
    }

    composable<Route.MainRoute.EditProfile> {
        val viewModel = hiltViewModel<EditProfileViewModel>()
        val editProfileState = viewModel.editProfileState.collectAsState().value
        val uiState = viewModel.uiState.collectAsState().value

        EditProfileScreen(
            editProfileState = editProfileState,
            uiState = uiState,
            onEvent = viewModel::onEvent,
            onBackClick = { navController.popUp() }
        )
    }

    composable<Route.MainRoute.AddEditExperience> {
        val args = it.toRoute<Route.MainRoute.AddEditExperience>()
        val viewModel = hiltViewModel<AddEditExperienceViewModel>()
        viewModel.init(
            experienceId = args.experienceId,
            company = args.company,
            description = args.description,
            endDate = args.endDate,
            position = args.position,
            startDate = args.startDate
        )
        val state = viewModel.addEditExperienceState.collectAsState().value
        val uiState = viewModel.uiState.collectAsState().value

        AddEditExperienceScreen(
            state = state,
            uiState = uiState,
            onEvent = viewModel::onEvent,
            onBackClick = { navController.popUp() }
        )

    }

}


// For preview purposes
val dummyUser = User(
    id = "123456",
    name = "Jane Doe",
    email = "jane.doe@example.com",
    bio = "Software developer with a passion for creating innovative solutions. Experienced in mobile and web development.",
    company = "Tech Innovations",
    currentJob = "Mobile Developer",
    jobTitle = "Senior Developer",
    degree = "Bachelor of Science",
    fieldOfStudy = "Computer Science",
    graduationYear = 2022,
    major = "Computer Science",
    minor = "Mathematics",
    university = "University of Technology",
    linkedInProfile = "linkedin.com/in/jane-doe",
    location = "San Francisco, CA",
    isVerifiedUser = true,
    createdAt = "2023-01-15T14:23:45.678Z",
    updatedAt = "2023-04-28T09:12:34.567Z",
    achievements = listOf(
        "Winner of University Hackathon 2022",
        "Published paper on Machine Learning Applications",
        "Developed open-source library with 500+ stars"
    ),
    interests = listOf("Machine Learning", "Mobile Development", "IoT", "Cloud Computing"),
    skills = listOf("Kotlin", "Android", "Java", "Python", "Flutter", "React", "AWS"),
    connections = listOf(
        Connection("1", "John Smith", "Google", "Software Engineer"),
        Connection("2", "Alice Johnson", "Amazon", "Product Manager"),
        Connection("3", "Bob Williams", "Microsoft", "UX Designer")
    ),
    workExperience = listOf(
        WorkExperience(
            _id = "exp1",
            company = "Tech Innovations",
            position = "Senior Developer",
            startDate = "2022-01-01",
            endDate = null,
            description = "Leading the mobile development team and implementing new features"
        ),
        WorkExperience(
            _id = "exp2",
            company = "StartupX",
            position = "Junior Developer",
            startDate = "2020-03-15",
            endDate = "2021-12-31",
            description = "Developed and maintained the company's mobile application"
        )
    ),
    privacySettings = PrivacySettings(
        showEmail = true,
        showPhone = false,
        showLocation = true
    )
)