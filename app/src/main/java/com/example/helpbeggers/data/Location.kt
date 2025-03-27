package com.example.helpbeggers.data

import com.google.android.gms.maps.model.LatLng

data class Location(
    val id: String,
    val name: String,
    val type: LocationType,
    val position: LatLng,
    val description: String
)

enum class LocationType {
    RECRUITER,
    NGO,
    HOSPITAL
} 