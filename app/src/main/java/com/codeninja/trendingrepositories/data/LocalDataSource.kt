package com.codeninja.trendingrepositories.data

import com.codeninja.trendingrepositories.data.database.RepositoryDao
import com.codeninja.trendingrepositories.data.database.entities.RepositoryEntities
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LocalDataSource @Inject constructor(
    private val repositoryDao: RepositoryDao
) {

    fun getAllRepository(): Flow<List<RepositoryEntities>> {
        return repositoryDao.getAllRepository()
    }

    suspend fun insertRepository(repositoryEntities: RepositoryEntities) {
        repositoryDao.insertData(repositoryEntities)
    }

    suspend fun deleteAllRepository(){
        repositoryDao.deleteAllData()
    }

}