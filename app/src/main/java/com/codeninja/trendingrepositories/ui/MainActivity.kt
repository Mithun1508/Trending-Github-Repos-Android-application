package com.codeninja.trendingrepositories.ui

import android.app.SearchManager
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.codeninja.trendingrepositories.R
import com.codeninja.trendingrepositories.adapter.ListAdapter
import com.codeninja.trendingrepositories.databinding.ActivityMainBinding
import com.codeninja.trendingrepositories.util.NetworkListener
import com.codeninja.trendingrepositories.util.NetworkResult
import com.codeninja.trendingrepositories.viewModel.MainViewModel
import com.codeninja.trendingrepositories.viewModel.observeOnce
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private var isLoading: Boolean = false

    private lateinit var mainViewModel: MainViewModel

    private val adapter: ListAdapter by lazy { ListAdapter() }

    private lateinit var networkListener: NetworkListener

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.myToolbar)

        mainViewModel = ViewModelProvider(this@MainActivity)[MainViewModel::class.java]

        setUpRecyclerView()

        lifecycleScope.launchWhenStarted {
            networkListener = NetworkListener()
            networkListener.checkNetworkAvailability(this@MainActivity).collect {
                mainViewModel.networkStatus = it
                mainViewModel.showNetworkStatus()
                readDatabase()
            }
        }

        binding.swipeRefreshLayout.setOnRefreshListener {
            mainViewModel.deleteLocalRepository()
            adapter.clearData()
            readDatabase()
            binding.swipeRefreshLayout.isRefreshing = false
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.toolbar_menu, menu)

        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        (menu?.findItem(R.id.action_search)?.actionView as SearchView).apply {
            setSearchableInfo(searchManager.getSearchableInfo(componentName))
            setOnQueryTextListener(object : SearchView.OnQueryTextListener{
                override fun onQueryTextSubmit(query: String?): Boolean {
                    adapter.filter.filter(query.toString().trim())
                    return true
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    adapter.filter.filter(newText.toString().trim())
                    return true
                }

            })
        }

        return super.onCreateOptionsMenu(menu)
    }

    private fun readDatabase() {
        lifecycleScope.launch {
            mainViewModel.getAllRepo.observeOnce(this@MainActivity) { database ->
                if (database.isNotEmpty()) {
                    adapter.setData(database.first().repositoriesResponse)
                } else {
                    requestApiData()
                }
            }
        }
    }

    private fun requestApiData() {
        mainViewModel.getRepoList()
        mainViewModel.repoResponse.observe(this) { response ->
            when (response) {
                is NetworkResult.Success -> {
                    response.data?.let { adapter.setData(it) }
                    isLoading = false
                }
                is NetworkResult.Error -> {
                    loadDataFromCache()
                    isLoading = false
                    Toast.makeText(this@MainActivity, response.message.toString(), Toast.LENGTH_SHORT).show()
                }
                is NetworkResult.Loading -> {
                    isLoading = true
                }
            }
        }
    }

    private fun loadDataFromCache() {
        lifecycleScope.launch {
            mainViewModel.getAllRepo.observe(this@MainActivity) { database ->
                if (database.isNotEmpty()) {
                    adapter.setData(database.first().repositoriesResponse)
                }
            }
        }
    }

    private fun setUpRecyclerView() {
        val recyclerView = binding.recyclerView
        recyclerView.adapter = adapter
        recyclerView.isNestedScrollingEnabled = false
        val linearLayoutManager = LinearLayoutManager(this@MainActivity, RecyclerView.VERTICAL, false)
        recyclerView.layoutManager = linearLayoutManager
    }
}