package com.codeninja.trendingrepositories.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.codeninja.trendingrepositories.data.database.entities.RepositoryEntities
import kotlinx.coroutines.flow.Flow

@Dao
interface RepositoryDao {
    @Query("SELECT * FROM repository_table ORDER BY id ASC")
    fun getAllRepository(): Flow<List<RepositoryEntities>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertData(repoEntities: RepositoryEntities)

    @Query("DELETE FROM repository_table")
    suspend fun deleteAllData()
}