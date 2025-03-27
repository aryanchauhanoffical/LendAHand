package com.example.helpbeggers.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.helpbeggers.ui.screens.*

@Composable
fun NavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Screen.Splash.route
    ) {
        composable(route = Screen.Splash.route) {
            SplashScreen(navController = navController)
        }

        composable(route = Screen.UserTypeSelection.route) {
            UserTypeSelectionScreen(navController = navController)
        }

        composable(route = Screen.NGOHome.route) {
            NGOHomeScreen(navController = navController)
        }

        composable(route = Screen.ServiceManagement.route) {
            ServiceManagementScreen(navController = navController)
        }

        composable(route = Screen.Map.route) {
            MapScreen(navController = navController)
        }

        composable(route = Screen.Donate.route) {
            DonateScreen(navController = navController)
        }

        composable(route = Screen.RecruiterHome.route) {
            RecruiterHomeScreen(navController = navController)
        }

        composable(route = Screen.JobListings.route) {
            JobListingsScreen(navController = navController)
        }

        composable(route = Screen.NGODirectory.route) {
            NGODirectoryScreen(navController = navController)
        }

        composable(route = Screen.Profile.route) {
            ProfileScreen(navController = navController)
        }

        composable(route = Screen.PostJob.route) {
            PostJobScreen(navController = navController)
        }

        composable(route = Screen.SearchJobs.route) {
            SearchJobsScreen(navController = navController)
        }
    }
} 