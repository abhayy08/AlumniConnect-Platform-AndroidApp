package com.abhay.alumniconnect.presentation.navigation.graphs

import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import androidx.navigation.toRoute
import com.abhay.alumniconnect.presentation.dummyUser
import com.abhay.alumniconnect.presentation.navigation.routes.Route
import com.abhay.alumniconnect.presentation.screens.event.EventsScreen
import com.abhay.alumniconnect.presentation.screens.job.JobViewModel
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
    navController: NavHostController, onShowSnackbarMessage: (String) -> Unit = { _ -> }
) {
    composable<Route.MainRoute.Home> {
        HomeScreen()
    }

    composable<Route.MainRoute.Search> {
        SearchScreen()
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
            })
    }

    composable<Route.MainRoute.Connections> {
        ConnectionsScreen(
            connections = dummyUser.connections, onBackClick = { navController.navigateUp() })
    }

    composable<Route.MainRoute.EditProfile> {
        val viewModel = hiltViewModel<EditProfileViewModel>()
        val editProfileState = viewModel.editProfileState.collectAsState().value
        val uiState = viewModel.uiState.collectAsState().value

        EditProfileScreen(
            editProfileState = editProfileState,
            uiState = uiState,
            onEvent = viewModel::onEvent,
            onBackClick = { navController.popUp() })
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
            onBackClick = { navController.popUp() })

    }

    jobNavGraph(
        showSnackbar = onShowSnackbarMessage, navController = navController
    )

}

fun NavGraphBuilder.jobNavGraph(
    showSnackbar: (String) -> Unit = {}, navController: NavHostController
) {
    navigation<Route.MainRoute.Jobs>(
        startDestination = Route.MainRoute.Jobs.JobsLists
    ) {

        composable<Route.MainRoute.Jobs.JobsLists> {
            val viewModel = it.sharedViewModel<JobViewModel>(navController)

            val jobScreenState = viewModel.jobScreenState.collectAsState().value
            val jobUiState = viewModel.jobUiState.collectAsState().value
            JobsScreen(
                jobScreenState = jobScreenState,
                uiState = jobUiState,
                onApplyClick = {},
                onJobCardClick = { id ->

                },
                onShowSnackbarMessage = showSnackbar
            )
        }

        composable<Route.MainRoute.Jobs.JobDetails> {

        }
    }
}

