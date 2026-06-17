package com.example.packagefortracking.data

import kotlinx.coroutines.flow.Flow

class PackageRepository(
    private val packageDao: PackageDao
) {
    fun getAllPackages(): Flow<List<PackageEntity>> {
        return packageDao.getAllPackages()
    }

    fun getActivePackages(): Flow<List<PackageEntity>> {
        return packageDao.getActivePackages()
    }

    fun getArchivedPackages(): Flow<List<PackageEntity>> {
        return packageDao.getArchivedPackages()
    }

    suspend fun insertPackage(packageEntity: PackageEntity) {
        packageDao.insertPackage(packageEntity)
    }

    suspend fun updatePackage(packageEntity: PackageEntity) {
        packageDao.updatePackage(packageEntity)
    }

    suspend fun deletePackage(packageEntity: PackageEntity) {
        packageDao.deletePackage(packageEntity)
    }
}