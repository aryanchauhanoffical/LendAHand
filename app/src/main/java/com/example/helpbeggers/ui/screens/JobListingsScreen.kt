package com.example.helpbeggers.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.helpbeggers.data.Job
import com.example.helpbeggers.data.JobType
import com.example.helpbeggers.navigation.Screen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun JobListingsScreen(navController: NavController) {
    var searchQuery by remember { mutableStateOf("") }
    var selectedJobType by remember { mutableStateOf<JobType?>(null) }
    var showFilterDialog by remember { mutableStateOf(false) }

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
                title = { Text("Job Listings") },
                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                },
                actions = {
                    IconButton(onClick = { showFilterDialog = true }) {
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
                     job.description.contains(searchQuery, ignoreCase = true))
                }) { job ->
                    JobCard(job = job, onJobClick = { /* TODO: Implement job details */ })
                }
            }
        }

        // Filter Dialog
        if (showFilterDialog) {
            AlertDialog(
                onDismissRequest = { showFilterDialog = false },
                title = { Text("Filter Jobs") },
                text = {
                    Column {
                        JobType.values().forEach { type ->
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable { selectedJobType = if (selectedJobType == type) null else type }
                                    .padding(vertical = 8.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                RadioButton(
                                    selected = selectedJobType == type,
                                    onClick = { selectedJobType = if (selectedJobType == type) null else type }
                                )
                                Text(
                                    text = type.name,
                                    modifier = Modifier.padding(start = 8.dp)
                                )
                            }
                        }
                    }
                },
                confirmButton = {
                    TextButton(onClick = { showFilterDialog = false }) {
                        Text("Done")
                    }
                }
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun JobCard(job: Job, onJobClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onJobClick)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(
                text = job.title,
                style = MaterialTheme.typography.titleMedium
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = job.description,
                style = MaterialTheme.typography.bodyMedium
            )
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "₹${job.pay}/day",
                    style = MaterialTheme.typography.bodyMedium
                )
                Text(
                    text = job.type.name,
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }
    }
} 