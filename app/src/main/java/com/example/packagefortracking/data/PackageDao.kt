package com.example.packagefortracking.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface PackageDao {

    @Query("SELECT * FROM packages")
    fun getAllPackages(): Flow<List<PackageEntity>>

    @Query("SELECT * FROM packages WHERE status != 'ARCHIVED'")
    fun getActivePackages(): Flow<List<PackageEntity>>

    @Query("SELECT * FROM packages WHERE status = 'ARCHIVED'")
    fun getArchivedPackages(): Flow<List<PackageEntity>>

    @Query("SELECT * FROM packages")
    suspend fun getAllPackagesOnce(): List<PackageEntity>

    @Insert
    suspend fun insertPackage(packageEntity: PackageEntity)

    @Update
    suspend fun updatePackage(packageEntity: PackageEntity)

    @Delete
    suspend fun deletePackage(packageEntity: PackageEntity)
}
