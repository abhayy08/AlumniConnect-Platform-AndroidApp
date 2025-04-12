package com.abhay.alumniconnect.presentation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.Modifier.Companion.then
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.abhay.alumniconnect.R
import com.abhay.alumniconnect.presentation.navigation.graphs.MainNavGraph
import com.abhay.alumniconnect.presentation.navigation.routes.Route
import com.abhay.alumniconnect.utils.navigateWithStateAndPopToStart
import com.example.ui.theme.someFontFamily
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(navController: NavHostController = rememberNavController()) {
    val navBackStackEntry = navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry.value?.destination
    var currentTitle = "Alumni Connect"
    val isTopLevel = topLevelDestinations.any { route ->
        currentDestination?.hierarchy?.any {
            if (it.hasRoute(route.route::class)) {
                currentTitle = route.title
                true
            } else {
                false
            }
        } == true
    }

    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()

    Scaffold(
        bottomBar = {
            AnimatedVisibility(
                visible = isTopLevel,
                enter = slideInVertically(animationSpec = tween(200)) { it },
                exit = slideOutVertically(animationSpec = tween(200)) { it }
            ) {
                BottomNavigationBar(
                    currentDestination = currentDestination, onNavItemClick = { route ->
                        navController.navigateWithStateAndPopToStart(route)
                    },
                    isVisible = isTopLevel
                )
            }
        },
        topBar = {
            AnimatedVisibility(
                visible = isTopLevel,
                enter = slideInVertically(animationSpec = tween(200)) { -it },
                exit = slideOutVertically(animationSpec = tween(200)) { -it }
            ) {
                AlumniTopAppBar(
                    isVisible = isTopLevel,
                    title = currentTitle
                )
            }

        },
        floatingActionButton = {
            FloatingActionButton(
                navController = navController,
                modifier = Modifier.height(70.dp)
            )
        },
        snackbarHost = {
            SnackbarHost(
                hostState = snackbarHostState,
                modifier = Modifier.imePadding()
            )
        }
    ) { paddingValues ->
        NavHost(
            modifier = then(
                if (isTopLevel) Modifier.padding(paddingValues)
                else Modifier
            ),
            navController = navController,
            startDestination = Route.MainRoute.Home,
            enterTransition = {
                slideInHorizontally(
                    initialOffsetX = { it }, animationSpec = tween(500)
                )
            },
            exitTransition = {
                slideOutHorizontally(
                    targetOffsetX = { -it }, animationSpec = tween(500)
                )
            },
            popEnterTransition = {
                slideInHorizontally(
                    initialOffsetX = { -it }, animationSpec = tween(500)
                )
            },
            popExitTransition = {
                slideOutHorizontally(
                    targetOffsetX = { it }, animationSpec = tween(500)
                )
            }
        ) {
            MainNavGraph(
                navController,
                onShowSnackbarMessage = { message ->
                    coroutineScope.launch {
                        snackbarHostState.showSnackbar(message)
                    }
                }
            )
        }
    }
}

@Composable
fun FloatingActionButton(
    modifier: Modifier = Modifier,
    navController: NavHostController
) {
    FloatingActionButton(
        onClick = {
            navController.navigate(Route.MainRoute.CreateJob)
        },
        shape = MaterialTheme.shapes.small
    ) {
        Icon(
            imageVector = Icons.Rounded.Add,
            contentDescription = "Add"
        )
    }
}


@Composable
fun BottomNavigationBar(
    modifier: Modifier = Modifier,
    onNavItemClick: (Any) -> Unit,
    currentDestination: NavDestination?,
    isVisible: Boolean = true
) {

    if (isVisible) {
        NavigationBar(
            modifier = modifier.height(90.dp),
            containerColor = MaterialTheme.colorScheme.surface,
            tonalElevation = 30.dp
        ) {
            topLevelDestinations.forEach { item ->
                val isSelected =
                    currentDestination?.hierarchy?.any { it.hasRoute(item.route::class) } == true
                NavigationBarItem(
                    selected = isSelected,
                    icon = {
                        Icon(
                            imageVector = item.icon,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.primary
                        )
                    },
                    label = { Text(item.title) },
                    onClick = { onNavItemClick(item.route) },
                    alwaysShowLabel = false,
                )
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AlumniTopAppBar(
    modifier: Modifier = Modifier,
    isVisible: Boolean = true,
    title: String
) {

    if (isVisible) {
        TopAppBar(
            title = {
                Text(
                    text = title,
                    fontFamily = someFontFamily,
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold
                )
            },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = MaterialTheme.colorScheme.surface
            ),
            navigationIcon = {
                Icon(
                    painter = painterResource(id = R.drawable.ic_launcher_foreground),
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.requiredSize(70.dp)
                )
            })
    }
}