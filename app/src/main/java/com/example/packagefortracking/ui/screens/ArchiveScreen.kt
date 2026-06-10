package com.example.packagefortracking.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Archive
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.packagefortracking.data.PackageStatus
import com.example.packagefortracking.data.dummyPackages
import com.example.packagefortracking.ui.components.PackageCard
import com.example.packagefortracking.ui.theme.AppBackground
import com.example.packagefortracking.ui.theme.AppSurface
import com.example.packagefortracking.ui.theme.Primary

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ArchiveScreen(
    onBackClick: () -> Unit
) {
    val archivedPackages =
        dummyPackages.filter {
            it.status == PackageStatus.DELIVERED ||
                    it.status == PackageStatus.ARCHIVED
        }

    Scaffold(
        containerColor = AppBackground,
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "Archive",
                        fontWeight = FontWeight.Bold
                    )
                },
                navigationIcon = {
                    IconButton(
                        onClick = onBackClick
                    ) {
                        Icon(
                            Icons.Default.ArrowBack,
                            contentDescription = null
                        )
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

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(18.dp),
                colors = CardDefaults.cardColors(
                    containerColor = AppSurface
                )
            ) {
                Row(
                    modifier = Modifier.padding(18.dp)
                ) {

                    Icon(
                        Icons.Default.Archive,
                        contentDescription = null
                    )

                    Spacer(modifier = Modifier.width(12.dp))

                    Text(
                        text = "${archivedPackages.size} archived packages",
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }

            LazyColumn {

                items(archivedPackages) { packageItem ->

                    Box(
                        modifier = Modifier.padding(
                            horizontal = 18.dp,
                            vertical = 8.dp
                        )
                    ) {
                        PackageCard(
                            packageItem = packageItem,
                            onClick = {}
                        )
                    }
                }
            }
        }
    }
}