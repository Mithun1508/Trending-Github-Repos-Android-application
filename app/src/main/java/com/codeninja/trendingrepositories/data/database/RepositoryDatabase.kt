package com.codeninja.trendingrepositories.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.codeninja.trendingrepositories.data.database.entities.RepositoryEntities

@Database(entities = [RepositoryEntities::class], version = 1, exportSchema = false)
@TypeConverters(RepositoryTypeConverter::class)
abstract class RepositoryDatabase : RoomDatabase() {
    abstract fun repositoryData(): RepositoryDao
}