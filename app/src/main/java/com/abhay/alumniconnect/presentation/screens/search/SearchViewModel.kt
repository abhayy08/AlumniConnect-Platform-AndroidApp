package com.abhay.alumniconnect.presentation.screens.search

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.abhay.alumniconnect.data.remote.dto.job.Job
import com.abhay.alumniconnect.domain.model.User
import com.abhay.alumniconnect.domain.repository.AlumniRemoteRepository
import com.abhay.alumniconnect.domain.repository.JobsRepository
import com.abhay.alumniconnect.utils.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val profileRepository: AlumniRemoteRepository,
    private val jobsRepository: JobsRepository
) : ViewModel() {

    private val _searchType = MutableStateFlow(SearchType.ALUMNI)
    val searchType: StateFlow<SearchType> = _searchType

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery

    private val _alumniResults = MutableStateFlow<List<User>>(emptyList())
    val alumniResults: StateFlow<List<User>> = _alumniResults

    private val _jobResults = MutableStateFlow<List<Job>>(emptyList())
    val jobResults: StateFlow<List<Job>> = _jobResults

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    private val _alumniFilters = MutableStateFlow(AlumniFilters())
    val alumniFilters: StateFlow<AlumniFilters> = _alumniFilters

    private val _jobFilters = MutableStateFlow(JobFilters())
    val jobFilters: StateFlow<JobFilters> = _jobFilters

    fun onEvent(event: SearchUiEvent) {
        when (event) {
            is SearchUiEvent.SetSearchType -> {
                _searchType.value = event.type
                clearResults()
            }

            is SearchUiEvent.SetSearchQuery -> _searchQuery.value = event.query

            is SearchUiEvent.UpdateAlumniFilters -> _alumniFilters.value = event.filters
            is SearchUiEvent.UpdateJobFilters -> _jobFilters.value = event.filters

            is SearchUiEvent.ClearFilters -> {
                _alumniFilters.value = AlumniFilters()
                _jobFilters.value = JobFilters()
            }

            is SearchUiEvent.PerformSearch -> performSearch()
        }
    }

    private fun clearResults() {
        _alumniResults.value = emptyList()
        _jobResults.value = emptyList()
    }

    private fun performSearch() {
        clearResults()
        _error.value = null
        _isLoading.value = true

        viewModelScope.launch {
            try {
                when (searchType.value) {
                    SearchType.ALUMNI -> searchAlumni()
                    SearchType.JOBS -> searchJobs()
                }
            } catch (e: Exception) {
                _error.value = "Error performing search: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }

    private suspend fun searchAlumni() {
        val filters = with(_alumniFilters.value) {
            mapOf(
                "name" to searchQuery.value,
                "graduationYear" to graduationYear?.toString(),
                "major" to major,
                "company" to company,
                "jobTitle" to jobTitle,
                "skills" to skills,
                "university" to university,
            ).filterValues { !it.isNullOrBlank() }
        }

        if (filters.isEmpty()) {
            _error.value = "Please provide at least one search criteria"
            return
        }

        val result = profileRepository.searchAlumni(filters)
        when (result) {
            is Result.Success<*> -> {
                _alumniResults.value = result.data!!
            }
            is Result.Error<*> -> {
                _error.value = result.message
            }
        }
    }

    private suspend fun searchJobs() {
        val filters = with(_jobFilters.value) {
            mutableMapOf<String, String>().apply {
                put("title", searchQuery.value)
                jobLocation?.let { put("location", it.toString().lowercase()) }
                minExperience?.let { put("minExperience", it.toString()) }
                graduationYear?.let { put("graduationYear", it.toString()) }
                jobType?.let { put("jobType", it.toString().lowercase()) }
                if (branch.isNotBlank()) put("branch", branch)
                degree?.let { put("degree", it.toString()) }
            }
        }

        if (filters.all { it.value.isBlank() }) {
            _error.value = "Please provide at least one search criteria"
            return
        }

        val result = jobsRepository.searchJobs(filters)
        when(result) {
            is Result.Success<*> -> {
                _jobResults.value = result.data!!
            }
            is Result.Error<*> -> {
                _error.value = result.message
            }
        }
    }
}

