package com.example.helpbeggers

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.helpbeggers.navigation.Screen
import com.example.helpbeggers.ui.screens.*
import com.example.helpbeggers.ui.theme.HelpbeggersTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            HelpbeggersTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    
                    NavHost(
                        navController = navController,
                        startDestination = Screen.Splash.route
                    ) {
                        // Initial screens
                        composable(Screen.Splash.route) {
                            SplashScreen(navController)
                        }
                        composable(Screen.UserTypeSelection.route) {
                            UserTypeSelectionScreen(navController)
                        }

                        // NGO related screens
                        composable(Screen.NGOHome.route) {
                            NGOHomeScreen(navController)
                        }
                        composable(Screen.ServiceManagement.route) {
                            ServiceManagementScreen(navController)
                        }

                        // Recruiter related screens
                        composable(Screen.RecruiterHome.route) {
                            RecruiterHomeScreen(navController)
                        }
                        composable(Screen.PostJob.route) {
                            PostJobScreen(navController)
                        }
                        composable(Screen.JobListings.route) {
                            JobListingsScreen(navController)
                        }

                        // Common screens
                        composable(Screen.Map.route) {
                            MapScreen(navController)
                        }
                        composable(Screen.Donate.route) {
                            DonateScreen(navController)
                        }
                        composable(Screen.NGODirectory.route) {
                            NGODirectoryScreen(navController)
                        }
                        composable(Screen.Profile.route) {
                            ProfileScreen(navController)
                        }
                        composable(Screen.SearchJobs.route) {
                            SearchJobsScreen(navController)
                        }
                    }
                }
            }
        }
    }
}