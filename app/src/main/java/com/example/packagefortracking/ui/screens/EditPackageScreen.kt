package com.example.packagefortracking.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Inventory2
import androidx.compose.material.icons.filled.LocalShipping
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.QrCode2
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.packagefortracking.data.PackageEntity
import com.example.packagefortracking.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditPackageScreen(
    packageEntity: PackageEntity?,
    onBackClick: () -> Unit,
    onSaveChanges: (PackageEntity) -> Unit,
    onDeletePackage: (PackageEntity) -> Unit
) {
    if (packageEntity == null) {
        MissingEditPackageScreen(onBackClick = onBackClick)
        return
    }

    var packageName by remember(packageEntity.id) { mutableStateOf(packageEntity.name) }
    var trackingNumber by remember(packageEntity.id) { mutableStateOf(packageEntity.trackingNumber) }
    var selectedProvider by remember(packageEntity.id) { mutableStateOf(packageEntity.postalService) }
    var notificationsEnabled by remember(packageEntity.id) {
        mutableStateOf(packageEntity.notificationsEnabled)
    }

    val providers = listOf("BH Post", "HP Mostar", "Pošta Srpske")

    Scaffold(
        containerColor = AppBackground,
        topBar = {
            TopAppBar(
                title = { Text("Edit Package", fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Primary,
                    titleContentColor = AppSurface,
                    navigationIconContentColor = AppSurface
                )
            )
        }
    ) { paddingValues ->

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(AppBackground)
                .padding(paddingValues),
            contentPadding = PaddingValues(20.dp),
            verticalArrangement = Arrangement.spacedBy(18.dp)
        ) {
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(26.dp),
                    colors = CardDefaults.cardColors(containerColor = AppSurface),
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                ) {
                    Column(modifier = Modifier.padding(22.dp)) {
                        Text(
                            text = "Update package details",
                            color = TextDark,
                            fontSize = 22.sp,
                            fontWeight = FontWeight.Bold
                        )

                        Text(
                            text = "Edit package name, tracking number or notification settings.",
                            color = TextMuted,
                            fontSize = 14.sp
                        )

                        Spacer(modifier = Modifier.height(24.dp))

                        OutlinedTextField(
                            value = packageName,
                            onValueChange = { packageName = it },
                            label = { Text("Package name") },
                            leadingIcon = {
                                Icon(Icons.Default.Inventory2, contentDescription = null)
                            },
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(16.dp),
                            singleLine = true
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        OutlinedTextField(
                            value = trackingNumber,
                            onValueChange = { trackingNumber = it },
                            label = { Text("Tracking number") },
                            leadingIcon = {
                                Icon(Icons.Default.QrCode2, contentDescription = null)
                            },
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(16.dp),
                            singleLine = true
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        EditProviderSelector(
                            providers = providers,
                            selectedProvider = selectedProvider,
                            onProviderSelected = { selectedProvider = it }
                        )

                        Spacer(modifier = Modifier.height(18.dp))

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(AppBackground, RoundedCornerShape(18.dp))
                                .padding(16.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                Icons.Default.Notifications,
                                contentDescription = null,
                                tint = Primary
                            )

                            Spacer(modifier = Modifier.width(12.dp))

                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    text = "Notifications",
                                    color = TextDark,
                                    fontWeight = FontWeight.SemiBold
                                )

                                Text(
                                    text = "Notify me when status changes.",
                                    color = TextMuted,
                                    fontSize = 12.sp
                                )
                            }

                            Switch(
                                checked = notificationsEnabled,
                                onCheckedChange = { notificationsEnabled = it },
                                colors = SwitchDefaults.colors(
                                    checkedThumbColor = AppSurface,
                                    checkedTrackColor = Primary,
                                    uncheckedThumbColor = AppSurface,
                                    uncheckedTrackColor = TextMuted.copy(alpha = 0.4f)
                                )
                            )
                        }
                    }
                }
            }

            item {
                OutlinedButton(
                    onClick = {
                        onDeletePackage(packageEntity)
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp),
                    shape = RoundedCornerShape(18.dp),
                    colors = ButtonDefaults.outlinedButtonColors(
                        contentColor = Error
                    )
                ) {
                    Icon(Icons.Default.Delete, contentDescription = null)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Delete Package", fontWeight = FontWeight.Bold)
                }
            }

            item {
                Button(
                    onClick = {
                        onSaveChanges(
                            packageEntity.copy(
                                name = packageName.ifBlank { "Unnamed Package" },
                                trackingNumber = trackingNumber.ifBlank { "Unknown" },
                                postalService = selectedProvider,
                                notificationsEnabled = notificationsEnabled
                            )
                        )
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(58.dp),
                    shape = RoundedCornerShape(18.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Accent,
                        contentColor = Primary
                    )
                ) {
                    Icon(Icons.Default.LocalShipping, contentDescription = null)
                    Spacer(modifier = Modifier.width(10.dp))
                    Text(
                        text = "Save Changes",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun MissingEditPackageScreen(
    onBackClick: () -> Unit
) {
    Scaffold(
        containerColor = AppBackground,
        topBar = {
            TopAppBar(
                title = { Text("Edit Package", fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Primary,
                    titleContentColor = AppSurface,
                    navigationIconContentColor = AppSurface
                )
            )
        }
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "Package not found",
                color = TextMuted,
                fontSize = 16.sp
            )
        }
    }
}

@Composable
private fun EditProviderSelector(
    providers: List<String>,
    selectedProvider: String,
    onProviderSelected: (String) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    Column {
        Text(
            text = "Postal service",
            color = TextDark,
            fontSize = 14.sp,
            fontWeight = FontWeight.SemiBold
        )

        Spacer(modifier = Modifier.height(8.dp))

        Box {
            OutlinedButton(
                onClick = { expanded = true },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.outlinedButtonColors(contentColor = Primary)
            ) {
                Icon(Icons.Default.LocalShipping, contentDescription = null)

                Spacer(modifier = Modifier.width(10.dp))

                Text(
                    text = selectedProvider,
                    modifier = Modifier.weight(1f),
                    fontWeight = FontWeight.SemiBold
                )
            }

            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                providers.forEach { provider ->
                    DropdownMenuItem(
                        text = { Text(provider) },
                        onClick = {
                            onProviderSelected(provider)
                            expanded = false
                        }
                    )
                }
            }
        }
    }
}