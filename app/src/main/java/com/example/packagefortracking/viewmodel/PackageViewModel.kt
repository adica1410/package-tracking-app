package com.example.packagefortracking.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.packagefortracking.data.PackageEntity
import com.example.packagefortracking.data.PackageRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import com.example.packagefortracking.viewmodel.PackageViewModel
import com.example.packagefortracking.tracking.TrackingProviderFactory

class PackageViewModel(
    private val repository: PackageRepository
) : ViewModel() {

    val activePackages = repository
        .getActivePackages()
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            emptyList()
        )

    val archivedPackages = repository
        .getArchivedPackages()
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            emptyList()
        )
    val allPackages = repository
        .getAllPackages()
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            emptyList()
        )

    fun addPackage(packageEntity: PackageEntity) {
        viewModelScope.launch {
            repository.insertPackage(packageEntity)
        }
    }

    fun updatePackage(packageEntity: PackageEntity) {
        viewModelScope.launch {
            repository.updatePackage(packageEntity)
        }
    }

    fun deletePackage(packageEntity: PackageEntity) {
        viewModelScope.launch {
            repository.deletePackage(packageEntity)
        }
    }
    fun refreshPackage(packageEntity: PackageEntity) {
        viewModelScope.launch {
            val provider = TrackingProviderFactory.createProvider(
                packageEntity.postalService
            )

            val result = provider.track(
                packageEntity.trackingNumber
            )

            val updatedPackage = packageEntity.copy(
                status = result.status,
                lastUpdate = result.lastUpdate,
                estimatedDelivery = result.estimatedDelivery,
                trackingEvents = result.events.joinToString("||") { event ->
                    "${event.title}###${event.description}###${event.date}###${event.completed}"
                }
            )

            repository.updatePackage(updatedPackage)
        }
    }
    fun refreshPackageAndReturn(
        packageEntity: PackageEntity,
        onUpdated: (PackageEntity) -> Unit
    ) {
        viewModelScope.launch {
            val provider = TrackingProviderFactory.createProvider(
                packageEntity.postalService
            )

            val result = provider.track(
                packageEntity.trackingNumber
            )

            val updatedPackage = packageEntity.copy(
                status = result.status,
                lastUpdate = result.lastUpdate,
                estimatedDelivery = result.estimatedDelivery,
                trackingEvents = result.events.joinToString("||") { event ->
                    "${event.title}###${event.description}###${event.date}###${event.completed}"
                }
            )

            repository.updatePackage(updatedPackage)
            onUpdated(updatedPackage)
        }
    }
}

class PackageViewModelFactory(
    private val repository: PackageRepository
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PackageViewModel::class.java)) {
            return PackageViewModel(repository) as T
        }

        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
