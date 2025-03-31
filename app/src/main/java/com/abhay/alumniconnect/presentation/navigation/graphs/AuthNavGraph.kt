package com.abhay.alumniconnect.presentation.navigation.graphs

import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.abhay.alumniconnect.presentation.navigation.routes.Route
import com.abhay.alumniconnect.presentation.screens.auth.AuthViewModel
import com.abhay.alumniconnect.presentation.screens.auth.LoginSignupScreen
import com.abhay.alumniconnect.utils.clearAndNavigate
import com.abhay.alumniconnect.utils.navigateAndPopUp

fun NavGraphBuilder.AuthNavGraph(
    navController: NavHostController
) {
    navigation<Route.AuthRoute>(
        startDestination = Route.AuthRoute.LoginRegister
    ) {
        composable<Route.AuthRoute.LoginRegister> {
            val viewModel: AuthViewModel = hiltViewModel()
            val state = viewModel.authUiState.collectAsState().value
            val isUserLoggedIn = viewModel.isLoggedIn.value

            LoginSignupScreen(
                uiState = state,
                onEvent = viewModel::onEvent,
                isUserLoggedIn = isUserLoggedIn,
                openAndPopUp = { navigateTo, popUp ->
                    navController.navigateAndPopUp(navigateTo, popUp)
                }
            )
        }
    }
}