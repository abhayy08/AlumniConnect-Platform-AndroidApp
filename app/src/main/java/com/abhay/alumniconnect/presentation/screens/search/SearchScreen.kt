package com.abhay.alumniconnect.presentation.screens.search

import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material.icons.rounded.FilterAlt
import androidx.compose.material.icons.rounded.FilterList
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.hilt.navigation.compose.hiltViewModel
import com.abhay.alumniconnect.presentation.components.AlumniCard
import com.abhay.alumniconnect.presentation.screens.job.components.JobCard
import java.util.Calendar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(
    viewModel: SearchViewModel = hiltViewModel(),
    onAlumniSelected: (userId: String) -> Unit,
    onJobSelected: (jobId: String, alreadyApplied: Boolean) -> Unit
) {
    val searchType by viewModel.searchType.collectAsState()
    val searchQuery by viewModel.searchQuery.collectAsState()
    val alumniResults by viewModel.alumniResults.collectAsState()
    val jobResults by viewModel.jobResults.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val error by viewModel.error.collectAsState()
    val alumniFilters by viewModel.alumniFilters.collectAsState()
    val jobFilters by viewModel.jobFilters.collectAsState()

    var showFilters by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
    ) {

        SearchTypeRow(
            searchType = searchType
        ) {
            val selectedType = when (it) {
                0 -> SearchType.ALUMNI
                1 -> SearchType.JOBS
                else -> SearchType.ALUMNI
            }
            viewModel.onEvent(SearchUiEvent.SetSearchType(selectedType))
        }

        SearchBar(
            query = searchQuery,
            onQueryChange = { viewModel.onEvent(SearchUiEvent.SetSearchQuery(it)) },
            onSearch = { viewModel.onEvent(SearchUiEvent.PerformSearch) },
            toggleFilters = { showFilters = !showFilters },
            showFilters = showFilters
        )

        AnimatedVisibility(visible = showFilters) {
            Column(modifier = Modifier.padding(bottom = 8.dp)) {
                when (searchType) {
                    SearchType.ALUMNI -> {
                        AlumniFiltersUI(
                            filters = alumniFilters,
                            onUpdate = { viewModel.onEvent(SearchUiEvent.UpdateAlumniFilters(it)) },
                            onClear = { viewModel.onEvent(SearchUiEvent.ClearFilters) })
                    }

                    SearchType.JOBS -> {
                        JobFiltersUI(
                            filters = jobFilters,
                            onUpdate = { viewModel.onEvent(SearchUiEvent.UpdateJobFilters(it)) },
                            onClear = { viewModel.onEvent(SearchUiEvent.ClearFilters) })
                    }
                }
            }
        }

        error?.let {
            Text(
                text = it,
                color = MaterialTheme.colorScheme.error,
                modifier = Modifier.padding(8.dp),
                style = MaterialTheme.typography.bodyMedium
            )
        }

        Box(modifier = Modifier.weight(1f)) {
            if (isLoading) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(top = 32.dp),
                    contentAlignment = Alignment.TopCenter
                ) {
                    CircularProgressIndicator()
                }
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    contentPadding = PaddingValues(bottom = 16.dp)
                ) {
                    when (searchType) {
                        SearchType.ALUMNI -> {
                            if (alumniResults.isEmpty()) {
                                item { EmptyResultsMessage(SearchType.ALUMNI) }
                            } else {
                                items(alumniResults, key = { it.id }) { user ->
                                    Log.d("AlumniCard", "$user")
                                    AlumniCard(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(vertical = 4.dp),
                                        alumni = user,
                                    )
                                    {
                                        onAlumniSelected(user.id)
                                    }
                                }
                            }
                        }

                        SearchType.JOBS -> {
                            if (jobResults.isEmpty()) {
                                item { EmptyResultsMessage(SearchType.JOBS) }
                            } else {
                                items(jobResults, key = { it._id }) { job ->
                                    JobCard(
                                        job = job,
                                        onClick = {
                                            onJobSelected(
                                                job._id,
                                                job.alreadyApplied == true
                                            )
                                        },
                                        onApplyClick = {},
                                        alreadyApplied = false,
                                        showApplyButton = false
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun SearchTypeRow(
    searchType: SearchType,
    onSearchTypeChange: (Int) -> Unit,
) {
    val options = listOf("Alumni", "Jobs")
    val selectedIndex = when (searchType) {
        SearchType.ALUMNI -> 0
        SearchType.JOBS -> 1
    }

    TabRow(
        selectedTabIndex = selectedIndex,
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 12.dp, bottom = 4.dp)
            .shadow(6.dp, MaterialTheme.shapes.small)
            .clip(MaterialTheme.shapes.small),
        containerColor = MaterialTheme.colorScheme.secondaryContainer,
        indicator = { tabPositions ->
            Box(
                Modifier
                    .tabIndicatorOffset(tabPositions[selectedIndex])
                    .fillMaxHeight()
                    .zIndex(-1f)
                    .padding(5.dp)
                    .background(
                        color = MaterialTheme.colorScheme.primary,
                        shape = MaterialTheme.shapes.small
                    )
            )
        },
        divider = {}
    ) {
        options.forEachIndexed { index, option ->
            Tab(
                selected = selectedIndex == index,
                onClick = { onSearchTypeChange(index) },
                text = {
                    Text(
                        text = option,
                        style = MaterialTheme.typography.titleMedium,
                        color = if (selectedIndex == index) Color.White else Color.Gray,
                        fontWeight = if (selectedIndex == index) FontWeight.Bold else FontWeight.Normal
                    )
                }
            )
        }
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
            .padding(vertical = 10.dp)
            .shadow(2.dp, RoundedCornerShape(12.dp)),
        shape = RoundedCornerShape(12.dp),
        color = MaterialTheme.colorScheme.surface
    ) {
        TextField(
            value = query,
            onValueChange = onQueryChange,
            placeholder = { Text("Search alumni or jobs...") },
            singleLine = true,
            leadingIcon = {
                Icon(imageVector = Icons.Rounded.Search, contentDescription = "Search")
            },
            trailingIcon = {
                IconButton(onClick = toggleFilters) {
                    Icon(
                        imageVector = if (showFilters) Icons.Rounded.FilterList else Icons.Rounded.FilterAlt,
                        contentDescription = "Toggle filters"
                    )
                }
            },
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
            keyboardActions = KeyboardActions(onSearch = { onSearch() }),
            colors = TextFieldDefaults.colors(
                focusedContainerColor = MaterialTheme.colorScheme.background,
                unfocusedContainerColor = MaterialTheme.colorScheme.background,
                disabledContainerColor = MaterialTheme.colorScheme.background
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 4.dp)
        )
    }
}

@Composable
fun AlumniFiltersUI(
    filters: AlumniFilters,
    onUpdate: (AlumniFilters) -> Unit,
    onClear: () -> Unit
) {
    Column(modifier = Modifier.padding(8.dp)) {
        LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            item {
                GraduationYearChip(filters.graduationYear) { onUpdate(filters.copy(graduationYear = it)) }
            }
            item {
                FilterChip("Major", filters.major) { onUpdate(filters.copy(major = it)) }
            }
            item {
                FilterChip("Company", filters.company) { onUpdate(filters.copy(company = it)) }
            }
            item {
                FilterChip("Job Title", filters.jobTitle) { onUpdate(filters.copy(jobTitle = it)) }
            }
            item {
                FilterChip("Skills", filters.skills) { onUpdate(filters.copy(skills = it)) }
            }
            item {
                FilterChip(
                    "University", filters.university
                ) { onUpdate(filters.copy(university = it)) }
            }
        }
        TextButton(onClick = onClear) { Text("Clear All") }
    }
}

@Composable
fun JobFiltersUI(
    filters: JobFilters,
    onUpdate: (JobFilters) -> Unit,
    onClear: () -> Unit
) {
    Column(modifier = Modifier.padding(8.dp)) {
        LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            item {
                DropdownFilterChip(
                    label = "Location",
                    selectedValue = filters.jobLocation?.name,
                    options = JobLocation.entries.map { it.name },
                    onSelect = {
                        val loc = JobLocation.entries.firstOrNull { it.name == it.toString() }
                        onUpdate(filters.copy(jobLocation = loc))
                    })
            }
            item {
                NumberInputChip("Min Experience", filters.minExperience) {
                    onUpdate(filters.copy(minExperience = it))
                }
            }
            item {
                GraduationYearChip(filters.graduationYear) {
                    onUpdate(filters.copy(graduationYear = it))
                }
            }
            item {
                DropdownFilterChip(
                    label = "Job Type",
                    selectedValue = filters.jobType?.name,
                    options = JobType.entries.map { it.name },
                    onSelect = {
                        val jt = JobType.entries.firstOrNull { it.name == it.toString() }
                        onUpdate(filters.copy(jobType = jt))
                    })
            }
            item {
                FilterChip("Branch", filters.branch) { onUpdate(filters.copy(branch = it)) }
            }
            item {
                DropdownFilterChip(
                    label = "Degree",
                    selectedValue = filters.degree?.name,
                    options = DegreeType.entries.map { it.name },
                    onSelect = {
                        val d = DegreeType.entries.firstOrNull { it.name == it.toString() }
                        onUpdate(filters.copy(degree = d))
                    })
            }
        }
        TextButton(onClick = onClear) { Text("Clear All") }
    }
}

@Composable
fun FilterChip(
    label: String, value: String, onValueChange: (String) -> Unit
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
            verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = if (value.isEmpty()) label else "$label: $value",
                style = MaterialTheme.typography.bodyMedium,
                color = if (value.isNotEmpty()) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface
            )
            if (value.isNotEmpty()) {
                Spacer(modifier = Modifier.width(4.dp))
                Icon(
                    imageVector = Icons.Rounded.Close,
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
            shape = MaterialTheme.shapes.small,
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
            })
    }
}

@Composable
fun GraduationYearChip(
    value: Int?, onValueChange: (Int?) -> Unit
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
            verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = if (value == null) "Graduation Year" else "Year: $value",
                style = MaterialTheme.typography.bodyMedium,
                color = if (value != null) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface
            )
            if (value != null) {
                Spacer(modifier = Modifier.width(4.dp))
                Icon(
                    imageVector = Icons.Rounded.Close,
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
            shape = MaterialTheme.shapes.small,
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
                            color = if (value == year) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface)
                    }
                }
            },
            confirmButton = {
                TextButton(onClick = { showDialog = false }) {
                    Text("Cancel")
                }
            })
    }
}

@Composable
fun NumberInputChip(
    label: String, value: Int?, onValueChange: (Int?) -> Unit
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
            verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = if (value == null) label else "$label: $value",
                style = MaterialTheme.typography.bodyMedium,
                color = if (value != null) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface
            )
            if (value != null) {
                Spacer(modifier = Modifier.width(4.dp))
                Icon(
                    imageVector = Icons.Rounded.Close,
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
            shape = MaterialTheme.shapes.small,
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
                    }) {
                    Text("Done")
                }
            },
            dismissButton = {
                TextButton(onClick = { showDialog = false }) {
                    Text("Cancel")
                }
            })
    }
}

@Composable
fun DropdownFilterChip(
    label: String, selectedValue: String?, options: List<String>, onSelect: (String?) -> Unit
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
            verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = if (selectedValue == null) label else "$label: $selectedValue",
                style = MaterialTheme.typography.bodyMedium,
                color = if (selectedValue != null) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface
            )
            if (selectedValue != null) {
                Spacer(modifier = Modifier.width(4.dp))
                Icon(
                    imageVector = Icons.Rounded.Close,
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
            shape = MaterialTheme.shapes.small,
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
                            color = if (selectedValue == option) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface)
                    }
                }
            },
            confirmButton = {
                TextButton(onClick = { showDialog = false }) {
                    Text("Cancel")
                }
            })
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
            imageVector = Icons.Rounded.Search,
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