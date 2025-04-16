package com.abhay.alumniconnect.presentation.screens.search

import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.FilterAlt
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.School
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.WorkOutline
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.abhay.alumniconnect.data.remote.dto.job.Job
import com.abhay.alumniconnect.domain.model.User
import com.abhay.alumniconnect.presentation.screens.job.components.JobCard
import com.abhay.alumniconnect.utils.capitalize
import com.abhay.alumniconnect.utils.formatDateForDisplay
import java.util.Calendar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(
    viewModel: SearchViewModel = hiltViewModel(),
    onAlumniSelected: (userId: String) -> Unit,
    onJobSelected: (jobId: String) -> Unit
) {
    val searchType by viewModel.searchType.collectAsState()
    val searchQuery by viewModel.searchQuery.collectAsState()
    val alumniResults by viewModel.alumniResults.collectAsState()
    val jobResults by viewModel.jobResults.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val error by viewModel.error.collectAsState()

    val nameFilter by viewModel.nameFilter.collectAsState()
    val graduationYearFilter by viewModel.graduationYearFilter.collectAsState()
    val majorFilter by viewModel.majorFilter.collectAsState()
    val companyFilter by viewModel.companyFilter.collectAsState()
    val jobTitleFilter by viewModel.jobTitleFilter.collectAsState()
    val skillsFilter by viewModel.skillsFilter.collectAsState()
    val universityFilter by viewModel.universityFilter.collectAsState()
    val locationFilter by viewModel.locationFilter.collectAsState()

    val jobLocationFilter by viewModel.jobLocationFilter.collectAsState()
    val minExperienceFilter by viewModel.minExperienceFilter.collectAsState()
    val jobGraduationYearFilter by viewModel.jobGraduationYearFilter.collectAsState()
    val jobTypeFilter by viewModel.jobTypeFilter.collectAsState()
    val branchFilter by viewModel.branchFilter.collectAsState()
    val degreeFilter by viewModel.degreeFilter.collectAsState()

    var showFilters by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 4.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            SearchTypeButton(
                text = "Alumni",
                isSelected = searchType == SearchType.ALUMNI,
                onClick = { viewModel.onEvent(SearchEvent.SetSearchType(SearchType.ALUMNI)) }
            )
            SearchTypeButton(
                text = "Jobs",
                isSelected = searchType == SearchType.JOBS,
                onClick = { viewModel.onEvent(SearchEvent.SetSearchType(SearchType.JOBS)) }
            )
        }

        SearchBar(
            query = searchQuery,
            onQueryChange = { viewModel.onEvent(SearchEvent.SetSearchQuery(it)) },
            onSearch = { viewModel.onEvent(SearchEvent.PerformSearch) },
            toggleFilters = { showFilters = !showFilters },
            showFilters = showFilters
        )

        AnimatedVisibility(visible = showFilters) {
            FilterSection(
                searchType = searchType,
                nameFilter = nameFilter,
                graduationYearFilter = graduationYearFilter,
                majorFilter = majorFilter,
                companyFilter = companyFilter,
                jobTitleFilter = jobTitleFilter,
                skillsFilter = skillsFilter,
                universityFilter = universityFilter,
                locationFilter = locationFilter,
                jobLocationFilter = jobLocationFilter,
                minExperienceFilter = minExperienceFilter,
                jobGraduationYearFilter = jobGraduationYearFilter,
                jobTypeFilter = jobTypeFilter,
                branchFilter = branchFilter,
                degreeFilter = degreeFilter,
                onNameFilterChange = { viewModel.onEvent(SearchEvent.SetNameFilter(it)) },
                onGraduationYearFilterChange = {
                    viewModel.onEvent(
                        SearchEvent.SetGraduationYearFilter(
                            it
                        )
                    )
                },
                onMajorFilterChange = { viewModel.onEvent(SearchEvent.SetMajorFilter(it)) },
                onCompanyFilterChange = { viewModel.onEvent(SearchEvent.SetCompanyFilter(it)) },
                onJobTitleFilterChange = { viewModel.onEvent(SearchEvent.SetJobTitleFilter(it)) },
                onSkillsFilterChange = { viewModel.onEvent(SearchEvent.SetSkillsFilter(it)) },
                onUniversityFilterChange = { viewModel.onEvent(SearchEvent.SetUniversityFilter(it)) },
                onLocationFilterChange = { viewModel.onEvent(SearchEvent.SetLocationFilter(it)) },
                onJobLocationFilterChange = { viewModel.onEvent(SearchEvent.SetJobLocationFilter(it)) },
                onMinExperienceFilterChange = {
                    viewModel.onEvent(
                        SearchEvent.SetMinExperienceFilter(
                            it
                        )
                    )
                },
                onJobGraduationYearFilterChange = {
                    viewModel.onEvent(
                        SearchEvent.SetJobGraduationYearFilter(
                            it
                        )
                    )
                },
                onJobTypeFilterChange = { viewModel.onEvent(SearchEvent.SetJobTypeFilter(it)) },
                onBranchFilterChange = { viewModel.onEvent(SearchEvent.SetBranchFilter(it)) },
                onDegreeFilterChange = { viewModel.onEvent(SearchEvent.SetDegreeFilter(it)) },
                onClearFilters = { viewModel.onEvent(SearchEvent.ClearFilters) }
            )
        }

        AnimatedVisibility(visible = error != null) {
            error?.let {
                Text(
                    text = it,
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.labelMedium,
                    modifier = Modifier.padding(vertical = 8.dp)
                )
            }
        }

        AnimatedVisibility(visible = isLoading) {
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(modifier = Modifier.padding(16.dp))
            }
        }

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 8.dp)
        ) {
            when (searchType) {
                SearchType.ALUMNI -> {
                    if (alumniResults.isEmpty() && !isLoading) {
                        item { EmptyResultsMessage(searchType = searchType) }
                    } else {
                        items(alumniResults) { alumni ->
                            AlumniCard(alumni = alumni) {
                                onAlumniSelected(alumni.id)
                            }
                        }
                    }
                }

                SearchType.JOBS -> {
                    if (jobResults.isEmpty() && !isLoading) {
                        item { EmptyResultsMessage(searchType = searchType) }
                    } else {
                        items(jobResults, key = {it._id}) { job ->
                            JobCard(
                                job = job,
                                onApplyClick = {},
                                alreadyApplied = false,
                                showApplyButton = false,
                                onClick = {
                                    onJobSelected(job._id)
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}


@Composable
fun SearchTypeButton(
    text: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        shape = MaterialTheme.shapes.small,
        colors = ButtonDefaults.buttonColors(
            containerColor = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surface,
            contentColor = if (isSelected) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSurface
        ),
        modifier = Modifier
            .padding(4.dp)
            .widthIn(min = 120.dp)
            .height(40.dp),
        elevation = ButtonDefaults.elevatedButtonElevation(
            defaultElevation = if (isSelected) 4.dp else 0.dp,
            pressedElevation = 8.dp
        )
    ) {
        Text(text = text)
    }
}

@Composable
fun SearchBar(
    query: String,
    onQueryChange: (String) -> Unit,
    onSearch: () -> Unit,
    toggleFilters: () -> Unit,
    showFilters: Boolean
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        shape = MaterialTheme.shapes.small,
    ) {
        TextField(
            value = query,
            onValueChange = onQueryChange,
            placeholder = { Text("Search...") },
            singleLine = true,
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "Search"
                )
            },
            trailingIcon = {
                IconButton(onClick = { toggleFilters() }) {
                    Icon(
                        imageVector = if (showFilters) Icons.Default.FilterList else Icons.Default.FilterAlt,
                        contentDescription = "Filter"
                    )
                }
            },
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Search
            ),
            keyboardActions = KeyboardActions(
                onSearch = { onSearch() }
            ),
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color.Transparent,
                unfocusedContainerColor = Color.Transparent,
            ),
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Composable
fun FilterSection(
    searchType: SearchType,
    nameFilter: String,
    graduationYearFilter: Int?,
    majorFilter: String,
    companyFilter: String,
    jobTitleFilter: String,
    skillsFilter: String,
    universityFilter: String,
    locationFilter: String,
    jobLocationFilter: JobLocation?,
    minExperienceFilter: Int?,
    jobGraduationYearFilter: Int?,
    jobTypeFilter: JobType?,
    branchFilter: String,
    degreeFilter: DegreeType?,
    onNameFilterChange: (String) -> Unit,
    onGraduationYearFilterChange: (Int?) -> Unit,
    onMajorFilterChange: (String) -> Unit,
    onCompanyFilterChange: (String) -> Unit,
    onJobTitleFilterChange: (String) -> Unit,
    onSkillsFilterChange: (String) -> Unit,
    onUniversityFilterChange: (String) -> Unit,
    onLocationFilterChange: (String) -> Unit,
    onJobLocationFilterChange: (JobLocation?) -> Unit,
    onMinExperienceFilterChange: (Int?) -> Unit,
    onJobGraduationYearFilterChange: (Int?) -> Unit,
    onJobTypeFilterChange: (JobType?) -> Unit,
    onBranchFilterChange: (String) -> Unit,
    onDegreeFilterChange: (DegreeType?) -> Unit,
    onClearFilters: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Filters",
                style = MaterialTheme.typography.labelSmall
            )
            TextButton(onClick = onClearFilters) {
                Text("Clear All")
            }
        }

        when (searchType) {
            SearchType.ALUMNI -> {
                AlumniFilters(
                    nameFilter = nameFilter,
                    graduationYearFilter = graduationYearFilter,
                    majorFilter = majorFilter,
                    companyFilter = companyFilter,
                    jobTitleFilter = jobTitleFilter,
                    skillsFilter = skillsFilter,
                    universityFilter = universityFilter,
                    locationFilter = locationFilter,
                    onNameFilterChange = onNameFilterChange,
                    onGraduationYearFilterChange = onGraduationYearFilterChange,
                    onMajorFilterChange = onMajorFilterChange,
                    onCompanyFilterChange = onCompanyFilterChange,
                    onJobTitleFilterChange = onJobTitleFilterChange,
                    onSkillsFilterChange = onSkillsFilterChange,
                    onUniversityFilterChange = onUniversityFilterChange,
                    onLocationFilterChange = onLocationFilterChange
                )
            }

            SearchType.JOBS -> {
                JobFilters(
                    jobLocationFilter = jobLocationFilter,
                    minExperienceFilter = minExperienceFilter,
                    jobGraduationYearFilter = jobGraduationYearFilter,
                    jobTypeFilter = jobTypeFilter,
                    branchFilter = branchFilter,
                    degreeFilter = degreeFilter,
                    onJobLocationFilterChange = onJobLocationFilterChange,
                    onMinExperienceFilterChange = onMinExperienceFilterChange,
                    onJobGraduationYearFilterChange = onJobGraduationYearFilterChange,
                    onJobTypeFilterChange = onJobTypeFilterChange,
                    onBranchFilterChange = onBranchFilterChange,
                    onDegreeFilterChange = onDegreeFilterChange
                )
            }
        }
    }
}

@Composable
fun AlumniFilters(
    nameFilter: String,
    graduationYearFilter: Int?,
    majorFilter: String,
    companyFilter: String,
    jobTitleFilter: String,
    skillsFilter: String,
    universityFilter: String,
    locationFilter: String,
    onNameFilterChange: (String) -> Unit,
    onGraduationYearFilterChange: (Int?) -> Unit,
    onMajorFilterChange: (String) -> Unit,
    onCompanyFilterChange: (String) -> Unit,
    onJobTitleFilterChange: (String) -> Unit,
    onSkillsFilterChange: (String) -> Unit,
    onUniversityFilterChange: (String) -> Unit,
    onLocationFilterChange: (String) -> Unit
) {
    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        item {
            FilterChip(
                label = "Name",
                value = nameFilter,
                onValueChange = onNameFilterChange
            )
        }
        item {
            GraduationYearChip(
                value = graduationYearFilter,
                onValueChange = onGraduationYearFilterChange
            )
        }
        item {
            FilterChip(
                label = "Major",
                value = majorFilter,
                onValueChange = onMajorFilterChange
            )
        }
        item {
            FilterChip(
                label = "Company",
                value = companyFilter,
                onValueChange = onCompanyFilterChange
            )
        }
        item {
            FilterChip(
                label = "Job Title",
                value = jobTitleFilter,
                onValueChange = onJobTitleFilterChange
            )
        }
        item {
            FilterChip(
                label = "Skills",
                value = skillsFilter,
                onValueChange = onSkillsFilterChange
            )
        }
        item {
            FilterChip(
                label = "University",
                value = universityFilter,
                onValueChange = onUniversityFilterChange
            )
        }
        item {
            FilterChip(
                label = "Location",
                value = locationFilter,
                onValueChange = onLocationFilterChange
            )
        }
    }
}

@Composable
fun JobFilters(
    jobLocationFilter: JobLocation?,
    minExperienceFilter: Int?,
    jobGraduationYearFilter: Int?,
    jobTypeFilter: JobType?,
    branchFilter: String,
    degreeFilter: DegreeType?,
    onJobLocationFilterChange: (JobLocation?) -> Unit,
    onMinExperienceFilterChange: (Int?) -> Unit,
    onJobGraduationYearFilterChange: (Int?) -> Unit,
    onJobTypeFilterChange: (JobType?) -> Unit,
    onBranchFilterChange: (String) -> Unit,
    onDegreeFilterChange: (DegreeType?) -> Unit
) {
    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        item {
            DropdownFilterChip(
                label = "Location",
                selectedValue = jobLocationFilter?.toString()?.capitalize(),
                options = JobLocation.entries.map { it.toString().capitalize() },
                onSelect = { selection ->
                    val newFilter = when (selection) {
                        null -> null
                        else -> JobLocation.entries
                            .first { it.toString().capitalize() == selection }
                    }
                    onJobLocationFilterChange(newFilter)
                }
            )
        }
        item {
            NumberInputChip(
                label = "Min Experience",
                value = minExperienceFilter,
                onValueChange = onMinExperienceFilterChange
            )
        }
        item {
            GraduationYearChip(
                value = jobGraduationYearFilter,
                onValueChange = onJobGraduationYearFilterChange
            )
        }
        item {
            DropdownFilterChip(
                label = "Job Type",
                selectedValue = jobTypeFilter?.toString()?.split('_')
                    ?.joinToString(" ") { it.capitalize() },
                options = JobType.entries.map {
                    it.toString().split('_').joinToString(" ") { word -> word.capitalize() }
                },
                onSelect = { selection ->
                    val newFilter = when (selection) {
                        null -> null
                        else -> JobType.entries.first {
                            it.toString().split('_')
                                .joinToString(" ") { word -> word.capitalize() } == selection
                        }
                    }
                    onJobTypeFilterChange(newFilter)
                }
            )
        }
        item {
            FilterChip(
                label = "Branch",
                value = branchFilter,
                onValueChange = onBranchFilterChange
            )
        }
        item {
            DropdownFilterChip(
                label = "Degree",
                selectedValue = degreeFilter?.toString(),
                options = DegreeType.entries.map { it.toString() },
                onSelect = { selection ->
                    val newFilter = when (selection) {
                        null -> null
                        else -> DegreeType.entries.first { it.toString() == selection }
                    }
                    onDegreeFilterChange(newFilter)
                }
            )
        }
    }
}

@Composable
fun FilterChip(
    label: String,
    value: String,
    onValueChange: (String) -> Unit
) {
    var showDialog by remember { mutableStateOf(false) }

    Surface(
        shape = MaterialTheme.shapes.small,
        border = BorderStroke(
            width = 1.dp,
            color = if (value.isNotEmpty()) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface.copy(
                alpha = 0.12f
            )
        ),
        color = if (value.isNotEmpty()) MaterialTheme.colorScheme.primary.copy(alpha = 0.12f) else MaterialTheme.colorScheme.surface
    ) {
        Row(
            modifier = Modifier
                .clickable { showDialog = true }
                .padding(horizontal = 12.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = if (value.isEmpty()) label else "$label: $value",
                style = MaterialTheme.typography.bodyMedium,
                color = if (value.isNotEmpty()) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface
            )
            if (value.isNotEmpty()) {
                Spacer(modifier = Modifier.width(4.dp))
                Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = "Clear",
                    modifier = Modifier
                        .size(16.dp)
                        .clickable { onValueChange("") },
                    tint = MaterialTheme.colorScheme.primary
                )
            }
        }
    }

    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = { Text("Enter $label") },
            text = {
                TextField(
                    value = value,
                    onValueChange = onValueChange,
                    label = { Text(label) },
                    singleLine = true
                )
            },
            confirmButton = {
                TextButton(onClick = { showDialog = false }) {
                    Text("Done")
                }
            }
        )
    }
}

@Composable
fun GraduationYearChip(
    value: Int?,
    onValueChange: (Int?) -> Unit
) {
    var showDialog by remember { mutableStateOf(false) }
    val currentYear = Calendar.getInstance().get(Calendar.YEAR)
    val years = (currentYear downTo currentYear - 50).toList()

    Surface(
        shape = MaterialTheme.shapes.small,
        border = BorderStroke(
            width = 1.dp,
            color = if (value != null) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface.copy(
                alpha = 0.12f
            )
        ),
        color = if (value != null) MaterialTheme.colorScheme.primary.copy(alpha = 0.12f) else MaterialTheme.colorScheme.surface
    ) {
        Row(
            modifier = Modifier
                .clickable { showDialog = true }
                .padding(horizontal = 12.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = if (value == null) "Graduation Year" else "Year: $value",
                style = MaterialTheme.typography.bodyMedium,
                color = if (value != null) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface
            )
            if (value != null) {
                Spacer(modifier = Modifier.width(4.dp))
                Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = "Clear",
                    modifier = Modifier
                        .size(16.dp)
                        .clickable { onValueChange(null) },
                    tint = MaterialTheme.colorScheme.primary
                )
            }
        }
    }

    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = { Text("Select Graduation Year") },
            text = {
                LazyColumn(
                    modifier = Modifier.height(300.dp)
                ) {
                    items(years) { year ->
                        Text(
                            text = year.toString(),
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    onValueChange(year)
                                    showDialog = false
                                }
                                .padding(vertical = 12.dp, horizontal = 16.dp),
                            style = MaterialTheme.typography.bodyLarge,
                            color = if (value == year) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface
                        )
                    }
                }
            },
            confirmButton = {
                TextButton(onClick = { showDialog = false }) {
                    Text("Cancel")
                }
            }
        )
    }
}

@Composable
fun NumberInputChip(
    label: String,
    value: Int?,
    onValueChange: (Int?) -> Unit
) {
    var showDialog by remember { mutableStateOf(false) }
    var textValue by remember { mutableStateOf(value?.toString() ?: "") }

    Surface(
        shape = MaterialTheme.shapes.small,
        border = BorderStroke(
            width = 1.dp,
            color = if (value != null) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface.copy(
                alpha = 0.12f
            )
        ),
        color = if (value != null) MaterialTheme.colorScheme.primary.copy(alpha = 0.12f) else MaterialTheme.colorScheme.surface
    ) {
        Row(
            modifier = Modifier
                .clickable { showDialog = true }
                .padding(horizontal = 12.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = if (value == null) label else "$label: $value",
                style = MaterialTheme.typography.bodyMedium,
                color = if (value != null) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface
            )
            if (value != null) {
                Spacer(modifier = Modifier.width(4.dp))
                Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = "Clear",
                    modifier = Modifier
                        .size(16.dp)
                        .clickable { onValueChange(null) },
                    tint = MaterialTheme.colorScheme.primary
                )
            }
        }
    }

    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = { Text("Enter $label") },
            text = {
                TextField(
                    value = textValue,
                    onValueChange = { input ->
                        textValue = input.filter { it.isDigit() }
                    },
                    label = { Text(label) },
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = Modifier.fillMaxWidth()
                )
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        onValueChange(textValue.toIntOrNull())
                        showDialog = false
                    }
                ) {
                    Text("Done")
                }
            },
            dismissButton = {
                TextButton(onClick = { showDialog = false }) {
                    Text("Cancel")
                }
            }
        )
    }
}

@Composable
fun DropdownFilterChip(
    label: String,
    selectedValue: String?,
    options: List<String>,
    onSelect: (String?) -> Unit
) {
    var showDialog by remember { mutableStateOf(false) }

    Surface(
        shape = MaterialTheme.shapes.small,
        border = BorderStroke(
            width = 1.dp,
            color = if (selectedValue != null) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface.copy(
                alpha = 0.12f
            )
        ),
        color = if (selectedValue != null) MaterialTheme.colorScheme.primary.copy(alpha = 0.12f) else MaterialTheme.colorScheme.surface
    ) {
        Row(
            modifier = Modifier
                .clickable { showDialog = true }
                .padding(horizontal = 12.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = if (selectedValue == null) label else "$label: $selectedValue",
                style = MaterialTheme.typography.bodyMedium,
                color = if (selectedValue != null) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface
            )
            if (selectedValue != null) {
                Spacer(modifier = Modifier.width(4.dp))
                Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = "Clear",
                    modifier = Modifier
                        .size(16.dp)
                        .clickable { onSelect(null) },
                    tint = MaterialTheme.colorScheme.primary
                )
            }
        }
    }

    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = { Text("Select $label") },
            text = {
                LazyColumn(
                    modifier = Modifier.height(300.dp)
                ) {
                    items(options) { option ->
                        Text(
                            text = option,
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    onSelect(option)
                                    showDialog = false
                                }
                                .padding(vertical = 12.dp, horizontal = 16.dp),
                            style = MaterialTheme.typography.bodyLarge,
                            color = if (selectedValue == option) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface
                        )
                    }
                }
            },
            confirmButton = {
                TextButton(onClick = { showDialog = false }) {
                    Text("Cancel")
                }
            }
        )
    }
}

@Composable
fun EmptyResultsMessage(searchType: SearchType) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 32.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            imageVector = Icons.Default.Search,
            contentDescription = null,
            modifier = Modifier.size(64.dp),
            tint = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = when (searchType) {
                SearchType.ALUMNI -> "No alumni found matching your criteria"
                SearchType.JOBS -> "No jobs found matching your criteria"
            },
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f),
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "Try adjusting your search or filters",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f),
            textAlign = TextAlign.Center
        )
    }
}

@Composable
fun AlumniCard(
    alumni: User,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
            .clickable(onClick = onClick),
        elevation = CardDefaults.cardElevation(2.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Profile image
            Surface(
                modifier = Modifier.size(48.dp),
                shape = CircleShape,
                color = MaterialTheme.colorScheme.primary.copy(alpha = 0.2f)
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Text(
                        text = alumni.name.first().toString(),
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.primary
                    )

                }
            }

            // Info
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 16.dp)
            ) {
                Text(
                    text = alumni.name,
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold
                )

                if (alumni.currentJob.isNotEmpty()) {
                    Text(
                        text = "${alumni.jobTitle} at ${alumni.company}",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                    )
                }

                Row(
                    modifier = Modifier.padding(top = 4.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.School,
                        contentDescription = null,
                        modifier = Modifier.size(16.dp),
                        tint = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = "${alumni.degree} in ${alumni.major}, ${alumni.graduationYear}",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)
                    )
                }
            }

            // Arrow icon
            Icon(
                imageVector = Icons.Default.ChevronRight,
                contentDescription = "View profile",
                tint = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.3f)
            )
        }
    }
}

@Composable
fun JobCard(
    job: Job,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
            .clickable(onClick = onClick),
        elevation = CardDefaults.cardElevation(2.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            // Header - title and company
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = job.title,
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = job.company,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                    )
                }

                // Logo placeholder or company logo
                Surface(
                    modifier = Modifier.size(40.dp),
                    shape = RoundedCornerShape(4.dp),
                    color = MaterialTheme.colorScheme.surface,
                    border = BorderStroke(
                        1.dp,
                        MaterialTheme.colorScheme.onSurface.copy(alpha = 0.1f)
                    )
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Text(
                            text = job.company.first().toString(),
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.primary
                        )
                    }
                }
            }

            HorizontalDivider(
                modifier = Modifier.padding(vertical = 8.dp),
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.1f)
            )

            // Job details
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                JobDetailChip(
                    icon = Icons.Default.LocationOn,
                    text = job.location
                )

                JobDetailChip(
                    icon = Icons.Default.WorkOutline,
                    text = job.jobType.toString().split('-').joinToString(" ") { word ->
                        word.capitalize()
                    }
                )

                JobDetailChip(
                    icon = Icons.Default.Star,
                    text = "${job.minExperience}+ yrs"
                )

            }

            // Short description
            Text(
                text = job.description.take(100) + if (job.description.length > 100) "..." else "",
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(top = 8.dp),
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
            )


            // Posted date
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp),
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.AccessTime,
                    contentDescription = null,
                    modifier = Modifier.size(12.dp),
                    tint = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)
                )
                Spacer(modifier = Modifier.width(4.dp))

                Text(
                    text = "Posted on ${formatDateForDisplay((job.createdAt.toString()))}",
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)
                )
            }
        }
    }
}


@Composable
fun JobDetailChip(
    icon: ImageVector,
    text: String
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(end = 4.dp)
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            modifier = Modifier.size(12.dp),
            tint = MaterialTheme.colorScheme.primary
        )
        Spacer(modifier = Modifier.width(2.dp))
        Text(
            text = text,
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
        )
    }
}
