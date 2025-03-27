package com.example.helpbeggers.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

data class NGOItem(
    val name: String,
    val description: String,
    val location: String,
    val services: List<String>
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NGODirectoryScreen(navController: NavController) {
    // Sample NGO data - replace with actual data from your backend
    val ngos = remember {
        listOf(
            NGOItem(
                name = "Hope Foundation",
                description = "Providing education and healthcare support to underprivileged communities",
                location = "New Delhi",
                services = listOf("Education", "Healthcare", "Food Distribution")
            ),
            NGOItem(
                name = "Green Earth",
                description = "Environmental conservation and sustainable development initiatives",
                location = "Mumbai",
                services = listOf("Environmental Protection", "Tree Planting", "Clean Energy")
            ),
            NGOItem(
                name = "Women Empowerment",
                description = "Supporting women's rights and economic independence",
                location = "Bangalore",
                services = listOf("Skill Development", "Legal Aid", "Microfinance")
            )
        )
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("NGO Directory") },
                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(ngos) { ngo ->
                Card(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp)
                    ) {
                        Text(
                            text = ngo.name,
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = ngo.description,
                            style = MaterialTheme.typography.bodyMedium
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "📍 ${ngo.location}",
                            style = MaterialTheme.typography.bodyMedium
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "Services:",
                            style = MaterialTheme.typography.titleSmall,
                            fontWeight = FontWeight.Medium
                        )
                        ngo.services.forEach { service ->
                            Text(
                                text = "• $service",
                                style = MaterialTheme.typography.bodyMedium
                            )
                        }
                    }
                }
            }
        }
    }
} 