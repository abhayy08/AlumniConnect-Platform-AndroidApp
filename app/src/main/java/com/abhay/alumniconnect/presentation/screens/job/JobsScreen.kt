package com.abhay.alumniconnect.presentation.screens.job

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.abhay.alumniconnect.presentation.dummyJobs
import com.abhay.alumniconnect.presentation.screens.job.pages.ApplicationsPage
import com.abhay.alumniconnect.presentation.screens.job.pages.OpportunitiesPage
import com.example.compose.AlumniConnectTheme
import kotlinx.coroutines.launch


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun JobsScreen(
    jobScreenState: JobScreenState,
    uiState: JobUIState,
    onApplyClick: (String) -> Unit = { },
    onJobCardClick: (id: String, applied: Boolean) -> Unit = { id, applied -> },
    onShowSnackbarMessage: (String) -> Unit = {}
) {

    LaunchedEffect(uiState) {
        when (uiState) {
            is JobUIState.Error -> {
                onShowSnackbarMessage(uiState.message)
            }

            is JobUIState.Success -> {
                if (uiState.message != null) {
                    onShowSnackbarMessage(uiState.message)
                }
            }

            else -> {}
        }
    }
    val pages = listOf("Opportunities", "Applications", "Offers")

    val pagerState = rememberPagerState(
        initialPage = 0, initialPageOffsetFraction = 0f, pageCount = { pages.size })

    val coroutineScope = rememberCoroutineScope()

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        TabRow(
            selectedTabIndex = pagerState.currentPage,
            modifier = Modifier.height(60.dp),
        ) {
            pages.forEachIndexed { index, title ->
                Tab(
                    selected = pagerState.currentPage == index, onClick = {
                        coroutineScope.launch {
                            pagerState.animateScrollToPage(index)
                        }
                    }) {
                    Text(
                        text = title,
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.titleSmall,
                        modifier = Modifier.padding(16.dp)
                    )
                }
            }
        }

        HorizontalPager(
            state = pagerState, modifier = Modifier.weight(1f)
        ) { index ->
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                if (uiState == JobUIState.Loading) {
                    CircularProgressIndicator()
                } else {
                    when (pages[index]) {
                        "Opportunities" -> {
                            OpportunitiesPage(
                                jobs = jobScreenState.jobs,
                                onApplyClick = onApplyClick,
                                onJobCardClick = onJobCardClick
                            )
                        }

                        "Applications" -> {
                            ApplicationsPage(
                                jobs = jobScreenState.jobsAppliedTo,
                                onJobCardClick = onJobCardClick
                            )
                        }

                        "Offers" -> {

                        }
                    }
                }
            }
        }
    }
}


@Preview(
    showBackground = true
)
@Composable
private fun JobScreenPreview() {
    AlumniConnectTheme {
        JobsScreen(
            jobScreenState = JobScreenState(
                jobs = dummyJobs,
            ),
            uiState = JobUIState.Success(),
        )
    }
}