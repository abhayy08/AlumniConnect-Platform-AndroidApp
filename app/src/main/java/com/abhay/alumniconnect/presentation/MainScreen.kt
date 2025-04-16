package com.abhay.alumniconnect.presentation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.Create
import androidx.compose.material.icons.rounded.Work
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SmallFloatingActionButton
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.Modifier.Companion.then
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
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
import com.abhay.alumniconnect.presentation.screens.MainViewModel
import com.abhay.alumniconnect.utils.navigateWithStateAndPopToStart
import com.example.ui.theme.someFontFamily
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(navController: NavHostController = rememberNavController()) {
    val navBackStackEntry = navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry.value?.destination
    val topLevelRoute = topLevelDestinations.firstOrNull { route ->
        currentDestination?.hierarchy?.any { it.hasRoute(route.route::class) } == true
    }
    val isTopLevel = topLevelRoute != null
    val currentTitle = topLevelRoute?.title ?: "Alumni Connect"


    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()

    val mainViewModel = hiltViewModel<MainViewModel>()

    Scaffold(bottomBar = {
        AnimatedVisibility(
            visible = isTopLevel,
            enter = slideInVertically(animationSpec = tween(200)) { it },
            exit = slideOutVertically(animationSpec = tween(200)) { it }) {
            BottomNavigationBar(
                currentDestination = currentDestination, onNavItemClick = { route ->
                    navController.navigateWithStateAndPopToStart(route)
                }, isVisible = isTopLevel
            )
        }
    }, topBar = {
        AnimatedVisibility(
            visible = isTopLevel,
            enter = slideInVertically(animationSpec = tween(200)) { -it },
            exit = slideOutVertically(animationSpec = tween(200)) { -it }) {
            AlumniTopAppBar(
                isVisible = isTopLevel, title = currentTitle
            )
        }

    }, floatingActionButton = {
        if(isTopLevel) {
            AppFloatingActionButton(
                navController = navController,
            )
        }
    }, snackbarHost = {
        SnackbarHost(
            hostState = snackbarHostState, modifier = Modifier.imePadding()
        )
    }) { paddingValues ->
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
            }) {
            MainNavGraph(
                mainViewModel = mainViewModel,
                navController = navController,
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
fun AppFloatingActionButton(
    navController: NavHostController
) {
    var expanded by remember { mutableStateOf(false) }

    val rotation by animateFloatAsState(
        targetValue = if (expanded) 45f else 0f, label = "FabRotation"
    )

    val shape by animateDpAsState(
        targetValue = (if (expanded) 50.dp else 4.dp), label = "FabShape"
    )

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        AnimatedVisibility(
            visible = expanded,
            enter = slideInVertically(initialOffsetY = { it }) + fadeIn(animationSpec = tween(200)),
            exit = slideOutVertically(targetOffsetY = { it }) + fadeOut(animationSpec = tween(200))
        ) {
            Column {
                SmallFloatingActionButton(
                    onClick = {
                        expanded = false
                        navController.navigate(Route.MainRoute.CreateJob)
                    }, containerColor = MaterialTheme.colorScheme.primary
                ) {
                    Icon(Icons.Rounded.Work, contentDescription = "Create Job")
                }

                SmallFloatingActionButton(
                    onClick = {
                        expanded = false
                        navController.navigate(Route.MainRoute.CreatePost)
                    }, containerColor = MaterialTheme.colorScheme.primary
                ) {
                    Icon(Icons.Rounded.Create, contentDescription = "Create Post")
                }
            }
        }
        Spacer(Modifier.height(6.dp))
        FloatingActionButton(
            onClick = { expanded = !expanded }, shape = RoundedCornerShape(shape)
        ) {
            Icon(
                imageVector = Icons.Rounded.Add,
                contentDescription = if (expanded) "Close" else "Expand",
                modifier = Modifier.rotate(rotation)
            )
        }
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
    modifier: Modifier = Modifier, isVisible: Boolean = true, title: String
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
            }, colors = TopAppBarDefaults.topAppBarColors(
                containerColor = MaterialTheme.colorScheme.surface
            ), navigationIcon = {
                Icon(
                    painter = painterResource(id = R.drawable.ic_launcher_foreground),
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.requiredSize(70.dp)
                )
            })
    }
}