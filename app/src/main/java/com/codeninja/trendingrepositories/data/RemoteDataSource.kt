package com.codeninja.trendingrepositories.data

import com.codeninja.trendingrepositories.data.network.ApiInterface
import com.codeninja.trendingrepositories.model.RepositoriesResponse
import retrofit2.Response
import javax.inject.Inject

class RemoteDataSource @Inject constructor(
    private val apiInterface: ApiInterface
) {

    suspend fun getRepository(): Response<List<RepositoriesResponse>> {
        return apiInterface.getRepositoryList("weekly")
    }

}