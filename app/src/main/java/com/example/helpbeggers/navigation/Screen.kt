package com.example.helpbeggers.navigation

sealed class Screen(val route: String) {
    // Initial screens
    object Splash : Screen("splash")
    object UserTypeSelection : Screen("user_type_selection")

    // NGO related screens
    object NGOHome : Screen("ngo_home")
    object ServiceManagement : Screen("service_management")

    // Recruiter related screens
    object RecruiterHome : Screen("recruiter_home")
    object PostJob : Screen("post_job")
    object JobListings : Screen("job_listings")

    // Common screens
    object Map : Screen("map")
    object Donate : Screen("donate")
    object NGODirectory : Screen("ngo_directory")
    object Profile : Screen("profile")
    object SearchJobs : Screen("search_jobs")

    companion object {
        fun fromRoute(route: String): Screen {
            return when (route) {
                "splash" -> Splash
                "user_type_selection" -> UserTypeSelection
                "ngo_home" -> NGOHome
                "service_management" -> ServiceManagement
                "recruiter_home" -> RecruiterHome
                "post_job" -> PostJob
                "job_listings" -> JobListings
                "map" -> Map
                "donate" -> Donate
                "ngo_directory" -> NGODirectory
                "profile" -> Profile
                "search_jobs" -> SearchJobs
                else -> throw IllegalArgumentException("Route $route is not recognized")
            }
        }
    }
} 