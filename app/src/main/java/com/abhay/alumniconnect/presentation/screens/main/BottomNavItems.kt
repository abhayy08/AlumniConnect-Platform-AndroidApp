package com.abhay.alumniconnect.presentation.screens.main

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Event
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material.icons.rounded.Person
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material.icons.rounded.Work
import androidx.compose.ui.graphics.vector.ImageVector
import com.abhay.alumniconnect.presentation.navigation.routes.Route

data class BottomNavItem(
    val route: Any,
    val title: String,
    val icon: ImageVector
)

val topLevelDestinations = listOf(
    BottomNavItem(
        route = Route.MainRoute.Home,
        title = "Home",
        icon = Icons.Rounded.Home
    ),
    BottomNavItem(
        route = Route.MainRoute.Search,
        title = "Search",
        icon = Icons.Rounded.Search
    ),
    BottomNavItem(
        route = Route.MainRoute.Jobs.JobsLists,
        title = "Jobs",
        icon = Icons.Rounded.Work
    ),
    BottomNavItem(
        route = Route.MainRoute.Events,
        title = "Events",
        icon = Icons.Rounded.Event
    ),
    BottomNavItem(
        route = Route.MainRoute.Profile,
        title = "Profile",
        icon = Icons.Rounded.Person
    )
)
