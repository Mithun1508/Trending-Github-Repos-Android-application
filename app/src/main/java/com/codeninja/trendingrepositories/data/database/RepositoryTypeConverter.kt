package com.codeninja.trendingrepositories.data.database

import androidx.room.TypeConverter
import com.codeninja.trendingrepositories.model.BuiltBy
import com.codeninja.trendingrepositories.model.RepositoriesResponse
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class RepositoryTypeConverter {
    var gson = Gson()

    @TypeConverter
    fun repositoriesToString(repositoriesResponse: List<RepositoriesResponse>): String {
        return gson.toJson(repositoriesResponse)
    }

    @TypeConverter
    fun stringToRepositories(data: String): List<RepositoriesResponse> {
        val listType = object : TypeToken<List<RepositoriesResponse>>() {}.type
        return gson.fromJson(data, listType)
    }

    @TypeConverter
    fun builtByToString(builtBy: BuiltBy): String {
        return gson.toJson(builtBy)
    }

    @TypeConverter
    fun stringToBuiltBy(data: String): BuiltBy {
        val listType = object : TypeToken<BuiltBy>() {}.type
        return gson.fromJson(data, listType)
    }

}