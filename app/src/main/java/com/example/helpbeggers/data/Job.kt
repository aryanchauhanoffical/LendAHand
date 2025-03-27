package com.example.helpbeggers.data

import com.google.android.gms.maps.model.LatLng

data class Job(
    val id: String,
    val title: String,
    val description: String,
    val location: LatLng,
    val pay: Double,
    val type: JobType,
    val requirements: List<String>,
    val contactNumber: String,
    val email: String
) 