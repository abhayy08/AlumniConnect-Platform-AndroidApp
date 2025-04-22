package com.abhay.alumniconnect.presentation.navigation.routes

import kotlinx.serialization.Serializable

sealed class Route {

    // Auth Graph
    @Serializable
    object AuthRoute : Route() {

        @Serializable
        object LoginRegister
    }

    @Serializable
    object SplashScreen

    // Main Graph
    @Serializable
    object MainRoute: Route() {

        @Serializable
        object Home

        @Serializable
        object CreatePost

        @Serializable
        object Profile

        @Serializable
        data class UserProfile(val userId: String)

        @Serializable
        object EditProfile

        @Serializable
        data class Applicants(
            val jobId: String
        )

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
        data class JobDetail(
            val alreadyApplied: Boolean = false,
            val jobId: String? = null
        )

        @Serializable
        object CreateJob

        @Serializable
        object Jobs {

            @Serializable
            object JobsLists

            @Serializable
            data class Application(val id: String? = null)
        }

    }
}


