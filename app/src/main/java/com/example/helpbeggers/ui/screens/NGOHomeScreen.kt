package com.example.helpbeggers.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.helpbeggers.navigation.Screen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NGOHomeScreen(navController: NavController) {
    // Debug logging
    println("=== NGOHomeScreen Debug Logs ===")
    println("1. NGOHomeScreen composition started")
    println("2. Current route: ${navController.currentDestination?.route}")
    println("3. Previous route: ${navController.previousBackStackEntry?.destination?.route}")
    println("4. NavController: $navController")

    var showAddServiceDialog by remember { mutableStateOf(false) }
    var showStatsDialog by remember { mutableStateOf(false) }

    // Add initialization check
    LaunchedEffect(Unit) {
        println("5. NGOHomeScreen LaunchedEffect triggered")
        println("6. Current destination: ${navController.currentDestination}")
        println("7. Back stack entries: ${navController.currentBackStack.value.map { it.destination.route }}")
    }

    DisposableEffect(Unit) {
        println("8. NGOHomeScreen mounted")
        onDispose {
            println("9. NGOHomeScreen disposed")
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text("Support Services") },
                    navigationIcon = {
                        IconButton(onClick = { 
                            println("10. Back navigation clicked")
                            try {
                                navController.navigateUp()
                                println("11. Back navigation successful")
                            } catch (e: Exception) {
                                println("ERROR: Back navigation failed - ${e.message}")
                                e.printStackTrace()
                            }
                        }) {
                            Icon(
                                imageVector = Icons.Default.ArrowBack,
                                contentDescription = "Back"
                            )
                        }
                    }
                )
            },
            floatingActionButton = {
                FloatingActionButton(
                    onClick = { showAddServiceDialog = true }
                ) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = "Add Service"
                    )
                }
            }
        ) { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .verticalScroll(rememberScrollState())
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // Welcome Section
                Text(
                    text = "Welcome to Support Services",
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold
                )

                // Quick Stats Cards
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Card(
                        modifier = Modifier.width(160.dp)
                    ) {
                        Column(
                            modifier = Modifier.padding(16.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                text = "⚡",
                                style = MaterialTheme.typography.titleLarge
                            )
                            Text(
                                text = "12",
                                style = MaterialTheme.typography.headlineMedium,
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                text = "Active Services",
                                style = MaterialTheme.typography.bodyMedium
                            )
                        }
                    }

                    Card(
                        modifier = Modifier.width(160.dp)
                    ) {
                        Column(
                            modifier = Modifier.padding(16.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                text = "👥",
                                style = MaterialTheme.typography.titleLarge
                            )
                            Text(
                                text = "1,234",
                                style = MaterialTheme.typography.headlineMedium,
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                text = "Beneficiaries",
                                style = MaterialTheme.typography.bodyMedium
                            )
                        }
                    }
                }

                // Action Cards
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            println("5. Navigating to Service Management")
                            navController.navigate(Screen.ServiceManagement.route)
                        }
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(
                            text = "⚙️ Manage Services",
                            style = MaterialTheme.typography.titleLarge
                        )
                        Text(
                            text = "Add and manage support services",
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                }

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            println("6. Navigating to Map")
                            navController.navigate(Screen.Map.route)
                        }
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(
                            text = "🗺️ Service Locations",
                            style = MaterialTheme.typography.titleLarge
                        )
                        Text(
                            text = "View and manage service locations",
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                }
            }
        }

        // Add Service Dialog
        if (showAddServiceDialog) {
            AlertDialog(
                onDismissRequest = { showAddServiceDialog = false },
                title = { Text("Add New Service") },
                text = {
                    Text("Service creation functionality coming soon...")
                },
                confirmButton = {
                    TextButton(onClick = { showAddServiceDialog = false }) {
                        Text("Close")
                    }
                }
            )
        }

        // Stats Dialog
        if (showStatsDialog) {
            AlertDialog(
                onDismissRequest = { showStatsDialog = false },
                title = { Text("Service Statistics") },
                text = {
                    Text("Detailed statistics coming soon...")
                },
                confirmButton = {
                    TextButton(onClick = { showStatsDialog = false }) {
                        Text("Close")
                    }
                }
            )
        }
    }
} 