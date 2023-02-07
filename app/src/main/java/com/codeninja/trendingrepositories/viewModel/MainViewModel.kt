package com.codeninja.trendingrepositories.viewModel

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.widget.Toast
import androidx.lifecycle.*
import com.codeninja.trendingrepositories.data.DataStoreRepository
import com.codeninja.trendingrepositories.data.Repository
import com.codeninja.trendingrepositories.data.database.entities.RepositoryEntities
import com.codeninja.trendingrepositories.model.RepositoriesResponse
import com.codeninja.trendingrepositories.util.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class MainViewModel  @Inject constructor(application: Application, private val dataStoreRepository: DataStoreRepository, private val repository: Repository) : AndroidViewModel(application) {

    var networkStatus = false
    var backOnline = false

    val readBackOnline = dataStoreRepository.readBackOnline.asLiveData()

    private fun saveBackOnline(backOnline: Boolean) =
        viewModelScope.launch(Dispatchers.IO) {
            dataStoreRepository.saveBackOnline(backOnline)
        }

    fun showNetworkStatus() {
        if (!networkStatus) {
            Toast.makeText(getApplication(), "No Internet Connection.", Toast.LENGTH_SHORT).show()
            saveBackOnline(true)
        } else if (networkStatus) {
            if (backOnline) {
                Toast.makeText(getApplication(), "We're back online.", Toast.LENGTH_SHORT).show()
                saveBackOnline(false)
            }
        }
    }

    /** ROOM DATABASE */

    val getAllRepo: LiveData<List<RepositoryEntities>> = repository.local.getAllRepository().asLiveData()

    private fun insertRepo(repoEntities: RepositoryEntities) =
        viewModelScope.launch(Dispatchers.IO) {
            repository.local.insertRepository(repoEntities)
        }

    fun deleteLocalRepository (){
        viewModelScope.launch(Dispatchers.IO) {

            repository.local.deleteAllRepository()
        }
    }

    /** RETROFIT */
    var repoResponse: MutableLiveData<NetworkResult<List<RepositoriesResponse>>> = MutableLiveData()

    fun getRepoList() = viewModelScope.launch {
        getRepoSafeCall()
    }

    private suspend fun getRepoSafeCall() {
        repoResponse.value = NetworkResult.Loading()
        if (hasInternetConnection()) {
            try {
                val response = repository.remote.getRepository()
                repoResponse.value = NetworkResult.Success(response.body()!!)

                val repo = repoResponse.value!!.data
                if(repo != null) {
                    offlineCacheRepo(repo)
                }
            } catch (e: Exception) {
                repoResponse.value = NetworkResult.Error("Repo not found.")
            }
        } else {
            repoResponse.value = NetworkResult.Error("No Internet Connection.")
        }
    }

    private fun offlineCacheRepo(repoResponse: List<RepositoriesResponse>) {
        val repoEntity = RepositoryEntities(repoResponse)
        insertRepo(repoEntity)
    }

    private fun hasInternetConnection(): Boolean {
        val connectivityManager = getApplication<Application>().getSystemService(
            Context.CONNECTIVITY_SERVICE
        ) as ConnectivityManager
        val activeNetwork = connectivityManager.activeNetwork ?: return false
        val capabilities = connectivityManager.getNetworkCapabilities(activeNetwork) ?: return false
        return when {
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
            else -> false
        }
    }
}