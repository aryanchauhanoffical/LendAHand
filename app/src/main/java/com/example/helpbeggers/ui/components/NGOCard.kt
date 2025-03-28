package com.example.helpbeggers.ui.components


import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.helpbeggers.data.NGO

@Composable
fun NGOCard(ngo: NGO) {
    val context = LocalContext.current
    Card(
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(
                text = ngo.name,
                style = MaterialTheme.typography.titleMedium
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = ngo.description,
                style = MaterialTheme.typography.bodyMedium
            )
            Spacer(modifier = Modifier.height(8.dp))
            
            // Services
            Text(
                text = "Services:",
                style = MaterialTheme.typography.titleSmall
            )
            ngo.services.forEach { service ->
                Row(
                    modifier = Modifier.padding(start = 8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.Check,
                        contentDescription = null,
                        modifier = Modifier.size(16.dp),
                        tint = MaterialTheme.colorScheme.primary
                    )
                    Text(
                        text = service,
                        style = MaterialTheme.typography.bodySmall,
                        modifier = Modifier.padding(start = 4.dp)
                    )
                }
            }
            
            Spacer(modifier = Modifier.height(8.dp))
            
            // Contact Information
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    Text(
                        text = "Contact:",
                        style = MaterialTheme.typography.titleSmall
                    )
                    Text(
                        text = ngo.contactNumber,
                        style = MaterialTheme.typography.bodySmall
                    )
                    Text(
                        text = ngo.email,
                        style = MaterialTheme.typography.bodySmall
                    )
                }
                IconButton(
                    onClick = {
                        // Launch phone dialer
                        val intent = Intent(Intent.ACTION_DIAL).apply {
                            data = Uri.parse("tel:${ngo.contactNumber}")
                        }
                        context.startActivity(intent)
                    }
                ) {
                    Icon(
                        imageVector = Icons.Default.Phone,
                        contentDescription = "Call NGO"
                    )
                }
            }
        }
    }
} 