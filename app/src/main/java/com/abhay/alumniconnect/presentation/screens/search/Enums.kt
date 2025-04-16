package com.abhay.alumniconnect.presentation.screens.search

import com.abhay.alumniconnect.utils.capitalize

enum class SearchType {
    ALUMNI, JOBS
}

enum class JobLocation {
    REMOTE, IN_OFFICE, HYBRID;

    override fun toString(): String {
        return name.lowercase().replace('_', '-')
    }
}

enum class JobType {
    FULL_TIME, PART_TIME, CONTRACT, INTERNSHIP;

    override fun toString(): String {
        return name.lowercase().replace('_', '-')
    }
}

enum class DegreeType {
    BACHELORS, MASTERS, PHD;

    override fun toString(): String {
        return name.capitalize()
    }
}