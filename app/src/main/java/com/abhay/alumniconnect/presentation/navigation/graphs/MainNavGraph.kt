package com.abhay.alumniconnect.presentation.navigation.graphs

import android.util.Log
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import androidx.navigation.toRoute
import com.abhay.alumniconnect.presentation.dummyUser
import com.abhay.alumniconnect.presentation.navigation.routes.Route
import com.abhay.alumniconnect.presentation.screens.MainViewModel
import com.abhay.alumniconnect.presentation.screens.job.JobViewModel
import com.abhay.alumniconnect.presentation.screens.job.JobsScreen
import com.abhay.alumniconnect.presentation.screens.job.application.JobApplicationScreen
import com.abhay.alumniconnect.presentation.screens.job.application.JobApplicationViewModel
import com.abhay.alumniconnect.presentation.screens.job.create_job.CreateJobScreen
import com.abhay.alumniconnect.presentation.screens.job.create_job.CreateJobViewModel
import com.abhay.alumniconnect.presentation.screens.job.job_detail_screen.JobDetailViewModel
import com.abhay.alumniconnect.presentation.screens.job.job_detail_screen.JobDetails
import com.abhay.alumniconnect.presentation.screens.main.HomeScreen
import com.abhay.alumniconnect.presentation.screens.main.HomeViewModel
import com.abhay.alumniconnect.presentation.screens.main.create_post.CreatePostScreen
import com.abhay.alumniconnect.presentation.screens.main.create_post.CreatePostViewModel
import com.abhay.alumniconnect.presentation.screens.profile.ConnectionsScreen
import com.abhay.alumniconnect.presentation.screens.profile.ProfileScreen
import com.abhay.alumniconnect.presentation.screens.profile.ProfileViewModel
import com.abhay.alumniconnect.presentation.screens.profile.add_edit_work_experience.AddEditExperienceScreen
import com.abhay.alumniconnect.presentation.screens.profile.add_edit_work_experience.AddEditExperienceViewModel
import com.abhay.alumniconnect.presentation.screens.profile.applicants.ApplicantsScreen
import com.abhay.alumniconnect.presentation.screens.profile.applicants.ApplicantsViewModel
import com.abhay.alumniconnect.presentation.screens.profile.edit_profile_screen.EditProfileScreen
import com.abhay.alumniconnect.presentation.screens.profile.edit_profile_screen.EditProfileViewModel
import com.abhay.alumniconnect.presentation.screens.profile.user_details.UserProfileViewModel
import com.abhay.alumniconnect.presentation.screens.search.SearchScreen
import com.abhay.alumniconnect.utils.AppUtils
import com.abhay.alumniconnect.utils.popUp

fun NavGraphBuilder.MainNavGraph(
    navController: NavHostController,
    onShowSnackbarMessage: (String) -> Unit = { _ -> },
    mainViewModel: MainViewModel
) {
    composable<Route.MainRoute.Home> {
        val viewModel = hiltViewModel<HomeViewModel>()
        val uiState = viewModel.uiState.collectAsState().value
        val postsState = viewModel.postsState.collectAsState().value
        val commentsState = viewModel.commentsState.collectAsState().value

        HomeScreen(
            uiState = uiState,
            postsState = postsState,
            commentsState = commentsState,
            onEvent = viewModel::onEvent,
            showSnackbar = onShowSnackbarMessage
        )
    }

    composable<Route.MainRoute.CreatePost> {
        val viewModel = hiltViewModel<CreatePostViewModel>()
        val currentUser = mainViewModel.currentUser.collectAsState().value
        val state = viewModel.createPostState.collectAsState().value
        CreatePostScreen(
            currentUser = currentUser,
            onNavigateBack = { navController.navigateUp() },
            postState = state,
            onPostContentChange = { viewModel.onContentChange(it) },
            onPostSubmit = {
                viewModel.createPost {
                    navController.navigateUp()
                }
            },
            resetError = viewModel::resetError,
            showSnackbar = onShowSnackbarMessage
        )
    }

    composable<Route.MainRoute.Search> {
        SearchScreen(onAlumniSelected = { userId ->
            navController.navigate(Route.MainRoute.UserProfile(userId = userId))
        }, onJobSelected = { jobId, alreadyApplied ->
            navController.navigate(
                Route.MainRoute.JobDetail(
                    alreadyApplied = alreadyApplied, jobId = jobId
                )
            )
        })
    }

    composable<Route.MainRoute.JobDetail> {
        val args = it.toRoute<Route.MainRoute.JobDetail>()
        val viewmodel = hiltViewModel<JobDetailViewModel>(it)
        val jobDetailsState = viewmodel.jobDetailsState.collectAsState().value

        LaunchedEffect(args.jobId) {
            if (args.jobId != null) {
                viewmodel.setJobId(args.jobId)
            } else {
                onShowSnackbarMessage("Unable to get Job Details")
                navController.popUp()
            }
        }

        JobDetails(
            jobState = jobDetailsState,
            alreadyApplied = args.alreadyApplied,
            onBackClick = { navController.popUp() },
            onApplyClick = {
                navController.navigate(Route.MainRoute.Jobs.Application(id = it))
            },
            resetError = { viewmodel.resetError() },
            showSnackbar = onShowSnackbarMessage
        )
    }

    composable<Route.MainRoute.Applicants> {
        val args = it.toRoute<Route.MainRoute.Applicants>()
        val viewModel = hiltViewModel<ApplicantsViewModel>()

        LaunchedEffect(args.jobId) {
            viewModel.getApplicantsOfJob(args.jobId)
        }

        val applications = viewModel.applicantsState.collectAsState().value
        val messagesState = viewModel.messageState.collectAsState()

        LaunchedEffect(messagesState.value) {
            if (messagesState.value != null) {
                onShowSnackbarMessage(messagesState.value!!)
                viewModel.resetMessageState()
            }
        }

        ApplicantsScreen(
            applications = applications,
            onApplicationStatusUpdate = { applicationId, status ->
                viewModel.updateApplicationState(args.jobId, applicationId, status)
            },
            onUserClick = { userId ->
                navController.navigate(Route.MainRoute.UserProfile(userId = userId))
            },
            onResumeClick = { link ->
                AppUtils.openLink(link)
            },
        )
    }

    composable<Route.MainRoute.Profile> {
        val viewModel = hiltViewModel<ProfileViewModel>()
        val profileUiState = viewModel.profileState.collectAsState().value
        val jobsState = viewModel.jobsState.collectAsState().value
        val uiState = viewModel.uiState.collectAsState().value

        ProfileScreen(
            profileState = profileUiState,
            jobsState = jobsState,
            uiState = uiState,
            onConnectionsClick = { navController.navigate(Route.MainRoute.Connections) },
            onProfileEditClick = { navController.navigate(Route.MainRoute.EditProfile) },
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
            },
            showSnackbar = onShowSnackbarMessage,
            onJobClick = { jobId ->
                navController.navigate(Route.MainRoute.Applicants(jobId = jobId))
            },
            onLinkClick = { link ->
                AppUtils.openLink(link)
            })
    }

    composable<Route.MainRoute.UserProfile> {
        val args = it.toRoute<Route.MainRoute.UserProfile>()
        val viewModel = hiltViewModel<UserProfileViewModel>()
        Log.d("UserProfile", "UserProfile:")

        LaunchedEffect(args.userId) {
            if (args.userId != null) {
                viewModel.loadUserProfile(args.userId)
            } else {
                onShowSnackbarMessage("Invalid User ID!")
                navController.popUp()
            }
        }

        val profileState = viewModel.profileState.collectAsState().value
        val jobsState = viewModel.jobsState.collectAsState().value
        val uiState = viewModel.uiState.collectAsState().value

        ProfileScreen(
            isCurrentUser = false,
            profileState = profileState,
            jobsState = jobsState,
            uiState = uiState,
            onProfileEditClick = {},
            onConnectionsClick = { },
            onJobClick = { jobId -> navController.navigate(Route.MainRoute.JobDetail(jobId = jobId)) })
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
            onBackClick = { navController.popUp() },
            showSnackbar = onShowSnackbarMessage
        )
    }

    composable<Route.MainRoute.CreateJob> {
        val viewModel = hiltViewModel<CreateJobViewModel>()
        val newJobState = viewModel.newJobState.collectAsState().value
        CreateJobScreen(
            newJobState = newJobState, onEvent = viewModel::onEvent, onBackClick = {
                navController.popUp()
            }, showSnackbar = onShowSnackbarMessage
        )
    }

    composable<Route.MainRoute.Jobs.Application> {
        val args = it.toRoute<Route.MainRoute.Jobs.Application>()
        val viewModel = hiltViewModel<JobApplicationViewModel>(it)
        val state = viewModel.applicationState.collectAsState().value

        LaunchedEffect(args.id) {
            if (args.id == null) {
                onShowSnackbarMessage("Unable to get Job Id, Try Again Later!")
                navController.popUp()
            } else {
                viewModel.init(args.id)
            }
        }

        JobApplicationScreen(
            state = state,
            onEvent = viewModel::onEvent,
            showSnackbar = onShowSnackbarMessage,
            onBackClick = {
                navController.popUp()
            },
            navigateAndPopUp = { route, popUp ->
                navController.popUp()
            })
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
                onApplyClick = { navController.navigate(Route.MainRoute.Jobs.Application(it)) },
                onJobCardClick = { id, appliedOrNot ->
                    navController.navigate(
                        Route.MainRoute.JobDetail(
                            jobId = id, alreadyApplied = appliedOrNot
                        )
                    )
                },
                onShowSnackbarMessage = showSnackbar
            )
        }


    }
}

