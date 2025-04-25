package com.abhay.alumniconnect.presentation.navigation.graphs


import android.util.Log
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.abhay.alumniconnect.presentation.MainScreen
import com.abhay.alumniconnect.presentation.navigation.routes.Route
import com.abhay.alumniconnect.presentation.navigation.utils.NavigationTransitions
import com.abhay.alumniconnect.presentation.screens.splash_screen.SplashScreen
import com.abhay.alumniconnect.utils.navigateAndPopUp

@Composable
fun RootNavGraph(
    navController: NavHostController
) {
    NavHost(
        navController = navController,
        startDestination = Route.SplashScreen,
        enterTransition = { EnterTransition.None },
        exitTransition = { ExitTransition.None },
        popEnterTransition = { EnterTransition.None },
        popExitTransition = { ExitTransition.None }
    ) {

        composable<Route.SplashScreen>(
            enterTransition = NavigationTransitions.enterTransition,
            exitTransition = NavigationTransitions.exitTransition,
            popEnterTransition = NavigationTransitions.popEnterTransition,
            popExitTransition = NavigationTransitions.popExitTransition
        ) {
            SplashScreen { isLoggedIn ->
                val destination = if (isLoggedIn) Route.MainRoute else Route.AuthRoute
                navController.navigateAndPopUp(destination, Route.SplashScreen)
            }
        }

        AuthNavGraph(navController)
        composable<Route.MainRoute> {
            MainScreen()
        }
    }
}

@Composable
inline fun <reified T : ViewModel> NavBackStackEntry.sharedViewModel(navController: NavController): T {
    val navGraphRoute = destination.parent?.route ?: return hiltViewModel()
    Log.d("sharedViewModel", "sharedViewModel: $navGraphRoute")
    val parentEntry = remember(this) {
        navController.getBackStackEntry(navGraphRoute)
    }
    return hiltViewModel(parentEntry)
}