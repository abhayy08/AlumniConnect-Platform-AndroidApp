package com.abhay.alumniconnect.presentation.navigation.routes

import com.abhay.alumniconnect.data.remote.dto.Connection
import com.abhay.alumniconnect.data.remote.dto.UserDetails
import kotlinx.serialization.Serializable

sealed class Route {

    // Auth Graph
    @Serializable
    object AuthRoute : Route() {

        @Serializable
        object LoginRegister
    }

    // Main Graph
    @Serializable
    object MainRoute: Route() {

        @Serializable
        object Home

        @Serializable
        object Profile

        @Serializable
        object EditProfile

        @Serializable
        data class AddEditExperience(
            val experienceId: String? = null,
            val company: String? = null,
            val description: String? = null,
            val endDate: String? = null,
            val position: String? = null,
            val startDate: String? = null
        )


        @Serializable
        object Connections

        @Serializable
        object Search

        @Serializable
        object Jobs

        @Serializable
        object Events
    }
}


