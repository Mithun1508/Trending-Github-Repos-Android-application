package com.codeninja.trendingrepositories.data.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.codeninja.trendingrepositories.model.RepositoriesResponse
import com.codeninja.trendingrepositories.util.Constants.Companion.REPOSITORY_TABLE

@Entity(tableName = REPOSITORY_TABLE)
class RepositoryEntities(
    var repositoriesResponse: List<RepositoriesResponse>
){
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}