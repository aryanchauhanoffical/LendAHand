package com.example.helpbeggers.data

import com.google.android.gms.maps.model.LatLng

data class NGO(
    val id: String,
    val name: String,
    val description: String,
    val location: LatLng,
    val services: List<String>,
    val contactNumber: String,
    val email: String
) 