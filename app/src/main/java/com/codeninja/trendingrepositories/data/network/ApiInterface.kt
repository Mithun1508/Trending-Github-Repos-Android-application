package com.codeninja.trendingrepositories.data.network

import com.codeninja.trendingrepositories.model.RepositoriesResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiInterface {

    @GET("repositories")
    suspend fun getRepositoryList(@Query("since") since: String): Response<List<RepositoriesResponse>>

}