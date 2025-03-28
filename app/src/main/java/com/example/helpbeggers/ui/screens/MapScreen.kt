package com.example.helpbeggers.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTransformGestures
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Place
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.helpbeggers.R
import com.example.helpbeggers.data.LocationType

private val JobsColor = Color(0xFFFFA000)
private val NGOColor = Color(0xFF7CB342)
private val HospitalColor = Color(0xFFE53935)

data class LocationData(
    val name: String,
    val address: String,
    val type: String,
    val distance: String,
    val salary: String? = null,
    val isJob: Boolean = false,
    val color: Color,
    val coordinates: Pair<Float, Float>
)

@Composable
private fun MapMarker(
    location: LocationData,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier.clickable(
            interactionSource = remember { MutableInteractionSource() },
            indication = null,
            onClick = onClick
        )
    ) {
        // Marker Pin
        Box(
            modifier = Modifier
                .size(48.dp)
                .padding(4.dp)
        ) {
            // Pin shadow
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .align(Alignment.Center)
                    .background(
                        color = Color.Black.copy(alpha = 0.1f),
                        shape = CircleShape
                    )
            )
            
            // Main pin body
            Box(
                modifier = Modifier
                    .size(36.dp)
                    .align(Alignment.Center)
                    .background(
                        color = location.color,
                        shape = CircleShape
                    )
                    .border(
                        width = 2.dp,
                        color = Color.White,
                        shape = CircleShape
                    )
            ) {
                // Icon based on type
                Text(
                    text = when {
                        location.isJob -> "💼"
                        location.type.contains("Hospital", ignoreCase = true) -> "🏥"
                        else -> "🤝"
                    },
                    modifier = Modifier.align(Alignment.Center),
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
        
        // Location name tooltip
        Card(
            modifier = Modifier.padding(top = 4.dp),
            colors = CardDefaults.cardColors(
                containerColor = Color.White
            ),
            elevation = CardDefaults.cardElevation(4.dp)
        ) {
            Text(
                text = location.name,
                style = MaterialTheme.typography.bodySmall,
                color = location.color,
                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                maxLines = 1
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MapScreen(navController: NavController) {
    var selectedTab by remember { mutableStateOf(LocationType.RECRUITER) }
    var selectedLocation by remember { mutableStateOf<LocationData?>(null) }
    var showDetails by remember { mutableStateOf(false) }
    
    // Map transformation states
    var scale by remember { mutableStateOf(1f) }
    var offset by remember { mutableStateOf(Offset.Zero) }
    
    // Sample data with coordinates
    val jobLocations = listOf(
        LocationData(
            "Construction Site Helper",
            "Metro Construction Site, Whitefield, Bangalore",
            "Construction Labor",
            "1.2 km",
            "₹500 - ₹600 per day",
            true,
            JobsColor,
            Pair(0.3f, 0.4f)
        ),
        LocationData(
            "Factory Packing Worker",
            "Industrial Area, Peenya, Bangalore",
            "Factory Labor",
            "2.5 km",
            "₹12,000 - ₹15,000 per month",
            true,
            JobsColor,
            Pair(0.6f, 0.3f)
        ),
        LocationData(
            "Loading/Unloading Worker",
            "Wholesale Market, K.R. Market, Bangalore",
            "Market Labor",
            "0.8 km",
            "₹450 - ₹500 per day",
            true,
            JobsColor,
            Pair(0.5f, 0.5f)
        ),
        LocationData(
            "Warehouse Helper",
            "Godown Area, Yeshwanthpur, Bangalore",
            "Warehouse Labor",
            "3.0 km",
            "₹400 - ₹450 per day",
            true,
            JobsColor,
            Pair(0.7f, 0.6f)
        ),
        LocationData(
            "Housekeeping Staff",
            "Commercial Complex, MG Road, Bangalore",
            "Cleaning Labor",
            "1.5 km",
            "₹10,000 - ₹12,000 per month",
            true,
            JobsColor,
            Pair(0.4f, 0.7f)
        )
    )

    val ngoLocations = listOf(
        LocationData(
            "Labor Support Foundation",
            "HSR Layout, Bangalore",
            "Worker Support NGO",
            "1.2 km",
            color = NGOColor,
            coordinates = Pair(0.4f, 0.5f)
        ),
        LocationData(
            "Daily Workers Welfare Center",
            "Indiranagar, Bangalore",
            "Labor Welfare",
            "2.0 km",
            color = NGOColor,
            coordinates = Pair(0.5f, 0.7f)
        ),
        LocationData(
            "Shramik Sahayata Kendra",
            "Koramangala, Bangalore",
            "Labor Rights NGO",
            "0.8 km",
            color = NGOColor,
            coordinates = Pair(0.3f, 0.3f)
        ),
        LocationData(
            "Workers Unity Center",
            "BTM Layout, Bangalore",
            "Labor Union",
            "1.7 km",
            color = NGOColor,
            coordinates = Pair(0.6f, 0.8f)
        )
    )

    val hospitalLocations = listOf(
        LocationData(
            "Workers Health Clinic",
            "Koramangala, Bangalore",
            "Primary Healthcare",
            "1.5 km",
            color = HospitalColor,
            coordinates = Pair(0.5f, 0.6f)
        ),
        LocationData(
            "Labor Welfare Hospital",
            "Jayanagar, Bangalore",
            "General Hospital",
            "2.8 km",
            color = HospitalColor,
            coordinates = Pair(0.7f, 0.7f)
        ),
        LocationData(
            "Industrial Area Health Center",
            "Peenya, Bangalore",
            "Worker Healthcare",
            "3.2 km",
            color = HospitalColor,
            coordinates = Pair(0.8f, 0.8f)
        ),
        LocationData(
            "Emergency Care Center",
            "Electronic City, Bangalore",
            "24/7 Emergency Care",
            "2.1 km",
            color = HospitalColor,
            coordinates = Pair(0.4f, 0.4f)
        )
    )

    // Get current locations based on selected tab
    val locations = when (selectedTab) {
        LocationType.RECRUITER -> jobLocations
        LocationType.NGO -> ngoLocations
        LocationType.HOSPITAL -> hospitalLocations
    }

    Box(modifier = Modifier.fillMaxSize()) {
        // Map Container with Border
        var containerSize by remember { mutableStateOf(Size.Zero) }
        
        // Main Content Area (Map takes 70% of screen height)
        Column(modifier = Modifier.fillMaxSize()) {
            // Map Area
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.7f)  // Map takes 70% of screen height
                    .padding(16.dp)
                    .onGloballyPositioned { coordinates ->
                        containerSize = Size(
                            width = coordinates.size.width.toFloat(),
                            height = coordinates.size.height.toFloat()
                        )
                    },
                shape = RoundedCornerShape(16.dp),
                elevation = CardDefaults.cardElevation(8.dp)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .pointerInput(Unit) {
                            detectTransformGestures { centroid, pan, zoom, rotation ->
                                val newScale = (scale.toFloat() * zoom.toFloat()).coerceIn(0.5f, 3f)
                                scale = newScale
                                offset += pan
                            }
                        }
                ) {
                    // Map Background
                    Image(
                        painter = painterResource(id = R.drawable.modern_map_image),
                        contentDescription = "Map of the area",
                        modifier = Modifier
                            .fillMaxSize()
                            .graphicsLayer(
                                scaleX = scale,
                                scaleY = scale,
                                translationX = offset.x,
                                translationY = offset.y
                            ),
                        contentScale = ContentScale.Crop
                    )
                    
                    // Map Controls
                    Column(
                        modifier = Modifier
                            .align(Alignment.TopEnd)
                            .padding(8.dp)
                    ) {
                        // Zoom Controls
                        Card(
                            modifier = Modifier
                                .padding(4.dp)
                                .size(40.dp),
                            colors = CardDefaults.cardColors(containerColor = Color.White),
                            elevation = CardDefaults.cardElevation(4.dp)
                        ) {
                            IconButton(
                                onClick = { scale = (scale * 1.2f).coerceIn(0.5f, 3f) }
                            ) {
                                Text(text = "+", style = MaterialTheme.typography.titleLarge)
                            }
                        }
                        Card(
                            modifier = Modifier
                                .padding(4.dp)
                                .size(40.dp),
                            colors = CardDefaults.cardColors(containerColor = Color.White),
                            elevation = CardDefaults.cardElevation(4.dp)
                        ) {
                            IconButton(
                                onClick = { scale = (scale * 0.8f).coerceIn(0.5f, 3f) }
                            ) {
                                Text(text = "−", style = MaterialTheme.typography.titleLarge)
                            }
                        }
                    }
                    
                    // Map Markers
                    locations.forEach { location ->
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .graphicsLayer(
                                    scaleX = scale,
                                    scaleY = scale,
                                    translationX = offset.x,
                                    translationY = offset.y
                                )
                        ) {
                            MapMarker(
                                location = location,
                                onClick = {
                                    selectedLocation = location
                                    showDetails = true
                                },
                                modifier = Modifier
                                    .align(Alignment.TopStart)
                                    .offset(
                                        x = (location.coordinates.first * containerSize.width).dp,
                                        y = (location.coordinates.second * containerSize.height).dp
                                    )
                            )
                        }
                    }
                }
            }

            // Options List Area (30% of screen height)
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            ) {
                item {
                    // Search and Filter Section
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = Color.White
                        ),
                        elevation = CardDefaults.cardElevation(4.dp)
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            // Search Bar
                            OutlinedTextField(
                                value = "",
                                onValueChange = { },
                                modifier = Modifier.fillMaxWidth(),
                                placeholder = { Text("Search in this area") },
                                singleLine = true,
                                leadingIcon = {
                                    Icon(
                                        imageVector = Icons.Default.Place,
                                        contentDescription = "Search"
                                    )
                                }
                            )
                            
                    Spacer(modifier = Modifier.height(8.dp))
                            
                            // Category Chips
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                FilterChip(
                                    selected = selectedTab == LocationType.RECRUITER,
                                    onClick = { 
                                        selectedTab = LocationType.RECRUITER
                                        showDetails = false
                                    },
                                    label = { Text("Jobs") }
                                )
                                FilterChip(
                                    selected = selectedTab == LocationType.NGO,
                                    onClick = { 
                                        selectedTab = LocationType.NGO
                                        showDetails = false
                                    },
                                    label = { Text("NGOs") }
                                )
                                FilterChip(
                                    selected = selectedTab == LocationType.HOSPITAL,
                                    onClick = { 
                                        selectedTab = LocationType.HOSPITAL
                                        showDetails = false
                                    },
                                    label = { Text("Hospitals") }
                                )
                            }
                        }
                    }
                }
                
                // Location Cards
                items(when (selectedTab) {
                    LocationType.RECRUITER -> jobLocations
                    LocationType.NGO -> ngoLocations
                    LocationType.HOSPITAL -> hospitalLocations
                }) { location ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp)
                            .clickable {
                                selectedLocation = location
                                showDetails = true
                            },
                        colors = CardDefaults.cardColors(
                            containerColor = Color.White
                        ),
                        elevation = CardDefaults.cardElevation(2.dp)
                    ) {
                        Row(
                            modifier = Modifier
                                .padding(12.dp)
                                .fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    text = location.name,
                                    style = MaterialTheme.typography.titleMedium,
                                    color = location.color
                                )
                                Text(
                                    text = location.address,
                                    style = MaterialTheme.typography.bodyMedium,
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis
                                )
                                location.salary?.let {
                                    Text(
                                        text = it,
                                        style = MaterialTheme.typography.bodyMedium,
                                        color = JobsColor
                                    )
                                }
                            }
                            Text(
                                text = location.distance,
                                style = MaterialTheme.typography.bodyMedium,
                                color = location.color
                            )
                        }
                    }
                }
            }
        }

        // Back Button
        IconButton(
            onClick = { navController.navigateUp() },
            modifier = Modifier
                .padding(16.dp)
                .size(40.dp)
                .background(Color.White, CircleShape)
        ) {
            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = "Back"
            )
        }

        // Location Details Bottom Sheet (if needed)
        if (showDetails && selectedLocation != null) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomCenter)
                    .padding(16.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color.White
                ),
                shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)
            ) {
                Column(
                    modifier = Modifier
                        .padding(16.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = selectedLocation!!.name,
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold,
                            color = selectedLocation!!.color
                        )
                        IconButton(onClick = { showDetails = false }) {
                            Icon(
                                imageVector = Icons.Default.Close,
                                contentDescription = "Close"
                            )
                        }
                    }
                    
                    Spacer(modifier = Modifier.height(8.dp))
                    
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.LocationOn,
                            contentDescription = "Location",
                            tint = selectedLocation!!.color
                        )
                        Text(
                            text = selectedLocation!!.address,
                            style = MaterialTheme.typography.bodyLarge
                        )
                    }
                    
                    Spacer(modifier = Modifier.height(8.dp))
                    
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Card(
                            colors = CardDefaults.cardColors(
                                containerColor = selectedLocation!!.color.copy(alpha = 0.1f)
                            ),
                            shape = MaterialTheme.shapes.small
                        ) {
                            Text(
                                text = selectedLocation!!.type,
                                style = MaterialTheme.typography.bodyMedium,
                                color = selectedLocation!!.color,
                                modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
                            )
                        }
                        
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Place,
                                contentDescription = "Distance",
                                tint = selectedLocation!!.color
                            )
                            Text(
                                text = selectedLocation!!.distance,
                                style = MaterialTheme.typography.bodyMedium,
                                color = selectedLocation!!.color
                            )
                        }
                    }
                    
                    selectedLocation?.salary?.let { salary ->
                        Spacer(modifier = Modifier.height(16.dp))
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Text(
                                text = "💰",
                                style = MaterialTheme.typography.bodyLarge
                            )
                            Text(
                                text = salary,
                                style = MaterialTheme.typography.bodyLarge,
                                fontWeight = FontWeight.Bold,
                                color = JobsColor
                            )
                        }
                    }
                    
                    Spacer(modifier = Modifier.height(16.dp))
                    
                    Button(
                        onClick = { /* Handle contact/apply action */ },
                        modifier = Modifier.fillMaxWidth(),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = selectedLocation!!.color
                        )
                    ) {
                        Text(
                            text = if (selectedLocation!!.isJob) "Apply Now" else "Contact",
                            modifier = Modifier.padding(vertical = 8.dp)
                        )
                    }
                }
            }
        }
    }
} 