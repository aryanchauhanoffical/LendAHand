package com.example.helpbeggers.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.helpbeggers.data.JobType

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PostJobScreen(navController: NavController) {
    var title by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var pay by remember { mutableStateOf("") }
    var selectedJobType by remember { mutableStateOf<JobType?>(null) }
    var requirements by remember { mutableStateOf("") }
    var location by remember { mutableStateOf("") }

    // Form validation function
    fun validateJobForm(): Boolean {
        if (title.isBlank()) {
            // Show error
            return false
        }
        if (description.isBlank()) {
            // Show error
            return false
        }
        if (pay.isBlank() || pay.toDoubleOrNull() == null) {
            // Show error
            return false
        }
        if (selectedJobType == null) {
            // Show error
            return false
        }
        if (requirements.isBlank()) {
            // Show error
            return false
        }
        if (location.isBlank()) {
            // Show error
            return false
        }
        return true
    }

    // Function to post job
    fun postJob() {
        // TODO: Implement actual job posting logic
        // For now, just navigate back
        navController.navigateUp()
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Post a Job") },
                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Text(
                            text = "⬅️",
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
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Job Title
            OutlinedTextField(
                value = title,
                onValueChange = { title = it },
                label = { Text("Job Title") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            // Job Description
            OutlinedTextField(
                value = description,
                onValueChange = { description = it },
                label = { Text("Job Description") },
                modifier = Modifier.fillMaxWidth(),
                minLines = 3
            )

            // Pay
            OutlinedTextField(
                value = pay,
                onValueChange = { pay = it },
                label = { Text("Daily Pay (₹)") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                prefix = { Text("₹") }
            )

            // Job Type
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

            // Requirements
            OutlinedTextField(
                value = requirements,
                onValueChange = { requirements = it },
                label = { Text("Requirements (comma-separated)") },
                modifier = Modifier.fillMaxWidth(),
                minLines = 2
            )

            // Location
            OutlinedTextField(
                value = location,
                onValueChange = { location = it },
                label = { Text("Location") },
                modifier = Modifier.fillMaxWidth(),
                leadingIcon = {
                    Text(
                        text = "📍",
                        style = MaterialTheme.typography.titleMedium
                    )
                }
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Submit Button
            Button(
                onClick = {
                    // Validate and post job
                    if (validateJobForm()) {
                        postJob()
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Post Job")
            }
        }
    }
} 