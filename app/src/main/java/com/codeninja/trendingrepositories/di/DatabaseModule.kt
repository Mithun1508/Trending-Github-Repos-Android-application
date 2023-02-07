package com.codeninja.trendingrepositories.di

import android.content.Context
import androidx.room.Room
import com.codeninja.trendingrepositories.data.database.RepositoryDatabase
import com.codeninja.trendingrepositories.util.Constants.Companion.DATABASE_NAME
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Singleton
    @Provides
    fun provideDatabase(
        @ApplicationContext context: Context
    ) = Room.databaseBuilder(
        context,
        RepositoryDatabase::class.java,
        DATABASE_NAME
    ).build()

    @Singleton
    @Provides
    fun repositoryDao(database: RepositoryDatabase) = database.repositoryData()

}