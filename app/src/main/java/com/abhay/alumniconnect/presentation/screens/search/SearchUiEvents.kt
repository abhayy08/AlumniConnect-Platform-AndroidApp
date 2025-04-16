package com.abhay.alumniconnect.presentation.screens.search

sealed class SearchUiEvent {
    data class SetSearchType(val type: SearchType) : SearchUiEvent()
    data class SetSearchQuery(val query: String) : SearchUiEvent()
    data class UpdateAlumniFilters(val filters: AlumniFilters) : SearchUiEvent()
    data class UpdateJobFilters(val filters: JobFilters) : SearchUiEvent()
    object ClearFilters : SearchUiEvent()
    object PerformSearch : SearchUiEvent()
}