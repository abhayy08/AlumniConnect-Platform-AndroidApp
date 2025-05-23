package com.abhay.alumniconnect.presentation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.Logout
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.Create
import androidx.compose.material.icons.rounded.Work
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.Modifier.Companion.then
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.IntOffset
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
import com.abhay.alumniconnect.utils.clearAndNavigate
import com.abhay.alumniconnect.utils.navigateWithStateAndPopToStart
import com.example.ui.theme.someFontFamily
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(navController: NavHostController = rememberNavController(), onLogout: () -> Unit) {
    val navBackStackEntry = navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry.value?.destination

    val screensToShowFABOn = remember { listOf(Route.MainRoute.Home, Route.MainRoute.Jobs) }
    val isFabVisible = screensToShowFABOn.firstOrNull { route ->
        currentDestination?.hierarchy?.any { it.hasRoute(route::class) } == true
    } != null

    val topLevelRoute = topLevelDestinations.firstOrNull { route ->
        currentDestination?.hierarchy?.any { it.hasRoute(route.route::class) } == true
    }

    val isTopLevel by remember(topLevelRoute) {
        derivedStateOf {
            topLevelRoute != null
        }
    }

    val currentTitle = topLevelRoute?.title ?: "Alumni Connect"

    val topBarOffsetY by animateDpAsState(
        targetValue = if (isTopLevel) 0.dp else (-100).dp,
        animationSpec = tween(300),
        label = "TopBarAnimation"
    )

    val bottomBarOffsetY by animateDpAsState(
        targetValue = if (isTopLevel) 0.dp else 100.dp,
        animationSpec = tween(300),
        label = "BottomBarAnimation"
    )

    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()

    val mainViewModel = hiltViewModel<MainViewModel>()

    Scaffold(
        bottomBar = {
            if (isTopLevel || bottomBarOffsetY < 100.dp) {
                Box(
                    modifier = Modifier.offset {
                        IntOffset(0, bottomBarOffsetY.roundToPx())
                    }
                ) {
                    BottomNavigationBar(
                        currentDestination = currentDestination,
                        onNavItemClick = { route ->

                            val isCurrentRoute = currentDestination?.hierarchy?.any {
                                it.hasRoute(route::class)
                            } == true

                            if (!isCurrentRoute) {
                                navController.navigateWithStateAndPopToStart(route)
                            }
                        },
                        isVisible = true
                    )
                }
            }
        },
        topBar = {
            if (isTopLevel || topBarOffsetY > (-100).dp) {
                Box(
                    modifier = Modifier.offset(y = topBarOffsetY)
                ) {
                    AlumniTopAppBar(
                        isVisible = true,
                        title = currentTitle,
                        onLogOutClick = {
                            mainViewModel.onLogout {
                                onLogout()
                            }
                        }
                    )
                }
            }
        },
        floatingActionButton = {
            AnimatedVisibility(
                visible = isFabVisible,
                enter = slideInVertically(initialOffsetY = { it }) + fadeIn(tween(150)),
                exit = slideOutVertically(targetOffsetY = { it }) + fadeOut(tween(150))
            ) {
                if (isFabVisible) {
                    AppFloatingActionButton(
                        navController = navController,
                    )
                }
            }
        },
        snackbarHost = {
            SnackbarHost(
                hostState = snackbarHostState, modifier = Modifier.imePadding()
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
            enterTransition = { EnterTransition.None },
            exitTransition = { ExitTransition.None },
            popEnterTransition = { EnterTransition.None },
            popExitTransition = { ExitTransition.None }
        ) {
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
        targetValue = if (expanded) 45f else 0f, label = ""
    )

    val shape by animateDpAsState(
        targetValue = if (expanded) 50.dp else 16.dp, label = ""
    )

    val labelBackground = MaterialTheme.colorScheme.surfaceVariant
    val labelTextColor = MaterialTheme.colorScheme.onSurfaceVariant

    Column(
        horizontalAlignment = Alignment.End
    ) {
        AnimatedVisibility(
            visible = expanded,
            enter = slideInVertically(initialOffsetY = { it }) + fadeIn(tween(150)),
            exit = slideOutVertically(targetOffsetY = { it }) + fadeOut(tween(150))
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(10.dp),
                horizontalAlignment = Alignment.End
            ) {
                FabWithLabel(
                    text = "Create Job",
                    icon = Icons.Rounded.Work,
                    onClick = {
                        expanded = false
                        navController.navigate(Route.MainRoute.CreateJob)
                    },
                    labelBg = labelBackground,
                    labelTextColor = labelTextColor
                )

                FabWithLabel(
                    text = "Create Post",
                    icon = Icons.Rounded.Create,
                    onClick = {
                        expanded = false
                        navController.navigate(Route.MainRoute.CreatePost)
                    },
                    labelBg = labelBackground,
                    labelTextColor = labelTextColor
                )
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        FloatingActionButton(
            onClick = { expanded = !expanded },
            shape = RoundedCornerShape(shape)
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
fun FabWithLabel(
    text: String,
    icon: ImageVector,
    onClick: () -> Unit,
    labelBg: Color,
    labelTextColor: Color
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Box(
            modifier = Modifier
                .background(labelBg, RoundedCornerShape(12.dp))
                .padding(horizontal = 12.dp, vertical = 6.dp)
        ) {
            Text(
                text = text,
                style = MaterialTheme.typography.bodySmall,
                color = labelTextColor
            )
        }

        SmallFloatingActionButton(
            onClick = onClick,
            containerColor = MaterialTheme.colorScheme.primary
        ) {
            Icon(icon, contentDescription = text)
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
    modifier: Modifier = Modifier, isVisible: Boolean = true, title: String,
    onLogOutClick: () -> Unit = {}
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
            },
            actions = {
                if(title == "Profile") {
                    IconButton(
                        onClick = onLogOutClick
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Rounded.Logout,
                            contentDescription = "Log out"
                        )
                    }
                }
            }
        )
    }
}