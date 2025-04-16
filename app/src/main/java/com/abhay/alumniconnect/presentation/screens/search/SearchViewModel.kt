package com.abhay.alumniconnect.presentation.screens.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.abhay.alumniconnect.data.remote.dto.job.Job
import com.abhay.alumniconnect.domain.model.User
import com.abhay.alumniconnect.domain.repository.AlumniRemoteRepository
import com.abhay.alumniconnect.domain.repository.JobsRepository
import com.abhay.alumniconnect.utils.Result
import com.abhay.alumniconnect.utils.capitalize
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

    private val _nameFilter = MutableStateFlow("")
    val nameFilter: StateFlow<String> = _nameFilter

    private val _graduationYearFilter = MutableStateFlow<Int?>(null)
    val graduationYearFilter: StateFlow<Int?> = _graduationYearFilter

    private val _majorFilter = MutableStateFlow("")
    val majorFilter: StateFlow<String> = _majorFilter

    private val _companyFilter = MutableStateFlow("")
    val companyFilter: StateFlow<String> = _companyFilter

    private val _jobTitleFilter = MutableStateFlow("")
    val jobTitleFilter: StateFlow<String> = _jobTitleFilter

    private val _skillsFilter = MutableStateFlow("")
    val skillsFilter: StateFlow<String> = _skillsFilter

    private val _universityFilter = MutableStateFlow("")
    val universityFilter: StateFlow<String> = _universityFilter

    private val _locationFilter = MutableStateFlow("")
    val locationFilter: StateFlow<String> = _locationFilter

    private val _jobLocationFilter = MutableStateFlow<JobLocation?>(null)
    val jobLocationFilter: StateFlow<JobLocation?> = _jobLocationFilter

    private val _minExperienceFilter = MutableStateFlow<Int?>(null)
    val minExperienceFilter: StateFlow<Int?> = _minExperienceFilter

    private val _jobGraduationYearFilter = MutableStateFlow<Int?>(null)
    val jobGraduationYearFilter: StateFlow<Int?> = _jobGraduationYearFilter

    private val _jobTypeFilter = MutableStateFlow<JobType?>(null)
    val jobTypeFilter: StateFlow<JobType?> = _jobTypeFilter

    private val _branchFilter = MutableStateFlow("")
    val branchFilter: StateFlow<String> = _branchFilter

    private val _degreeFilter = MutableStateFlow<DegreeType?>(null)
    val degreeFilter: StateFlow<DegreeType?> = _degreeFilter

    fun onEvent(event: SearchEvent) {
        when (event) {
            is SearchEvent.SetSearchType -> {
                _searchType.value = event.type
                clearResults()
            }
            is SearchEvent.SetSearchQuery -> _searchQuery.value = event.query
            is SearchEvent.SetNameFilter -> _nameFilter.value = event.name
            is SearchEvent.SetGraduationYearFilter -> _graduationYearFilter.value = event.year
            is SearchEvent.SetMajorFilter -> _majorFilter.value = event.major
            is SearchEvent.SetCompanyFilter -> _companyFilter.value = event.company
            is SearchEvent.SetJobTitleFilter -> _jobTitleFilter.value = event.title
            is SearchEvent.SetSkillsFilter -> _skillsFilter.value = event.skills
            is SearchEvent.SetUniversityFilter -> _universityFilter.value = event.university
            is SearchEvent.SetLocationFilter -> _locationFilter.value = event.location
            is SearchEvent.SetJobLocationFilter -> _jobLocationFilter.value = event.location
            is SearchEvent.SetMinExperienceFilter -> _minExperienceFilter.value = event.years
            is SearchEvent.SetJobGraduationYearFilter -> _jobGraduationYearFilter.value = event.year
            is SearchEvent.SetJobTypeFilter -> _jobTypeFilter.value = event.type
            is SearchEvent.SetBranchFilter -> _branchFilter.value = event.branch
            is SearchEvent.SetDegreeFilter -> _degreeFilter.value = event.degree
            is SearchEvent.ClearFilters -> clearFilters()
            is SearchEvent.PerformSearch -> performSearch()
        }
    }

    private fun clearFilters() {
        _nameFilter.value = ""
        _graduationYearFilter.value = null
        _majorFilter.value = ""
        _companyFilter.value = ""
        _jobTitleFilter.value = ""
        _skillsFilter.value = ""
        _universityFilter.value = ""
        _locationFilter.value = ""
        _jobLocationFilter.value = null
        _minExperienceFilter.value = null
        _jobGraduationYearFilter.value = null
        _jobTypeFilter.value = null
        _branchFilter.value = ""
        _degreeFilter.value = null
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
        val filters = mapOf(
            "name" to nameFilter.value,
            "graduationYear" to graduationYearFilter.value?.toString(),
            "major" to majorFilter.value,
            "company" to companyFilter.value,
            "jobTitle" to jobTitleFilter.value,
            "skills" to skillsFilter.value,
            "university" to universityFilter.value,
            "location" to locationFilter.value
        ).filterValues { !it.isNullOrBlank() }

        if (filters.isEmpty() && searchQuery.value.isBlank()) {
            _error.value = "Please provide at least one search criteria"
            return
        }

        val result = profileRepository.searchAlumni(filters + mapOf("query" to searchQuery.value))

        when(result) {
            is Result.Success<*> -> _alumniResults.value = result.data!!
            is Result.Error<*> -> {}
        }

    }

    private suspend fun searchJobs() {
        val filters = mutableMapOf<String, String>()

        if (jobLocationFilter.value != null) {
            filters["location"] = jobLocationFilter.value.toString().lowercase()
        }

        if (minExperienceFilter.value != null) {
            filters["minExperience"] = minExperienceFilter.value.toString()
        }

        if (jobGraduationYearFilter.value != null) {
            filters["graduationYear"] = jobGraduationYearFilter.value.toString()
        }

        if (jobTypeFilter.value != null) {
            filters["jobType"] = jobTypeFilter.value.toString().lowercase().replace('_', '-')
        }

        if (branchFilter.value.isNotBlank()) {
            filters["branch"] = branchFilter.value
        }

        if (degreeFilter.value != null) {
            filters["degree"] = degreeFilter.value.toString()
        }

        if (filters.isEmpty() && searchQuery.value.isBlank()) {
            _error.value = "Please provide at least one search criteria"
            return
        }

        val result = jobsRepository.searchJobs(filters + mapOf("query" to searchQuery.value))
        when(result) {
            is Result.Success<*> -> _jobResults.value = result.data!!
            is Result.Error<*> -> {}
        }

    }
}

// Enums and data classes
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

sealed class SearchEvent {
    data class SetSearchType(val type: SearchType) : SearchEvent()
    data class SetSearchQuery(val query: String) : SearchEvent()
    data class SetNameFilter(val name: String) : SearchEvent()
    data class SetGraduationYearFilter(val year: Int?) : SearchEvent()
    data class SetMajorFilter(val major: String) : SearchEvent()
    data class SetCompanyFilter(val company: String) : SearchEvent()
    data class SetJobTitleFilter(val title: String) : SearchEvent()
    data class SetSkillsFilter(val skills: String) : SearchEvent()
    data class SetUniversityFilter(val university: String) : SearchEvent()
    data class SetLocationFilter(val location: String) : SearchEvent()
    data class SetJobLocationFilter(val location: JobLocation?) : SearchEvent()
    data class SetMinExperienceFilter(val years: Int?) : SearchEvent()
    data class SetJobGraduationYearFilter(val year: Int?) : SearchEvent()
    data class SetJobTypeFilter(val type: JobType?) : SearchEvent()
    data class SetBranchFilter(val branch: String) : SearchEvent()
    data class SetDegreeFilter(val degree: DegreeType?) : SearchEvent()
    object ClearFilters : SearchEvent()
    object PerformSearch : SearchEvent()
}
