package com.example.helpbeggers.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.helpbeggers.data.Job
import com.example.helpbeggers.data.JobType

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchJobsScreen(navController: NavController) {
    var searchQuery by remember { mutableStateOf("") }
    var selectedJobType by remember { mutableStateOf<JobType?>(null) }
    var minPay by remember { mutableStateOf("") }
    var maxPay by remember { mutableStateOf("") }
    var location by remember { mutableStateOf("") }
    var showFilters by remember { mutableStateOf(false) }

    // Sample jobs (in a real app, these would come from a database or API)
    val jobs = remember {
        listOf(
            Job(
                "1",
                "Daily Laborer Needed",
                "Looking for someone to help with construction work",
                com.google.android.gms.maps.model.LatLng(28.6139, 77.2090),
                500.0,
                JobType.DAILY_LABOR,
                listOf("Physical strength", "Basic construction knowledge"),
                "Recruiter1",
                "recruiter1@example.com"
            ),
            Job(
                "2",
                "Office Cleaning",
                "Need someone to clean office premises",
                com.google.android.gms.maps.model.LatLng(28.6140, 77.2091),
                300.0,
                JobType.CLEANING,
                listOf("Basic cleaning skills"),
                "Recruiter2",
                "recruiter2@example.com"
            )
        )
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Search Jobs") },
                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                },
                actions = {
                    IconButton(onClick = { showFilters = !showFilters }) {
                        Text(
                            text = "⚙️",
                            style = MaterialTheme.typography.titleLarge
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            // Search Bar
            OutlinedTextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                placeholder = { Text("Search jobs...") },
                leadingIcon = { 
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "Search"
                    )
                },
                singleLine = true
            )

            // Filters Section
            if (showFilters) {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        Text(
                            text = "Filters",
                            style = MaterialTheme.typography.titleMedium
                        )

                        // Job Type Filter
                        ExposedDropdownMenuBox(
                            expanded = false,
                            onExpandedChange = { },
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            OutlinedTextField(
                                value = selectedJobType?.name ?: "",
                                onValueChange = { },
                                readOnly = true,
                                label = { Text("Job Type") },
                                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = false) },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .menuAnchor()
                            )

                            ExposedDropdownMenu(
                                expanded = false,
                                onDismissRequest = { }
                            ) {
                                JobType.values().forEach { type ->
                                    DropdownMenuItem(
                                        text = { Text(type.name) },
                                        onClick = { selectedJobType = type }
                                    )
                                }
                            }
                        }

                        // Pay Range
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            OutlinedTextField(
                                value = minPay,
                                onValueChange = { minPay = it },
                                label = { Text("Min Pay") },
                                modifier = Modifier.weight(1f),
                                prefix = { Text("₹") }
                            )
                            OutlinedTextField(
                                value = maxPay,
                                onValueChange = { maxPay = it },
                                label = { Text("Max Pay") },
                                modifier = Modifier.weight(1f),
                                prefix = { Text("₹") }
                            )
                        }

                        // Location
                        OutlinedTextField(
                            value = location,
                            onValueChange = { location = it },
                            label = { Text("Location") },
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                }
            }

            // Job Listings
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(jobs.filter { job ->
                    (selectedJobType == null || job.type == selectedJobType) &&
                    (searchQuery.isEmpty() || 
                     job.title.contains(searchQuery, ignoreCase = true) ||
                     job.description.contains(searchQuery, ignoreCase = true)) &&
                    (minPay.isEmpty() || job.pay >= minPay.toDoubleOrNull() ?: 0.0) &&
                    (maxPay.isEmpty() || job.pay <= maxPay.toDoubleOrNull() ?: Double.MAX_VALUE) &&
                    (location.isEmpty() || job.location.toString().contains(location, ignoreCase = true))
                }) { job ->
                    JobCard(job = job, onJobClick = { /* TODO: Implement job details */ })
                }
            }
        }
    }
} 