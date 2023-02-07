package com.codeninja.trendingrepositories.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.codeninja.trendingrepositories.databinding.LayoutRepoListItemBinding
import com.codeninja.trendingrepositories.model.RepositoriesResponse
import java.util.*
import kotlin.collections.ArrayList

class ListAdapter :
    RecyclerView.Adapter<ListAdapter.ViewHolder>(), Filterable {

    private var repoFilterList = mutableListOf<RepositoriesResponse>()
    private var repo = mutableListOf<RepositoriesResponse>()

    init {
        repoFilterList = repo
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.setContent(repoFilterList[position])
    }

    override fun getItemCount(): Int {
        return repoFilterList.size
    }

    class ViewHolder(private val binding: LayoutRepoListItemBinding) : RecyclerView.ViewHolder(binding.root) {

        fun setContent(repositoriesResponse: RepositoriesResponse) {
            Glide.with(binding.imgRepo.context)
                .load(repositoriesResponse.avatar)
                .into(binding.imgRepo)

            binding.txtAuthor.text = repositoriesResponse.author
            binding.txtName.text = repositoriesResponse.name
            binding.txtDescription.text = repositoriesResponse.description

            binding.imgLangColour.setColorFilter(Color.parseColor(repositoriesResponse.languageColor))
            binding.txtLang.text = repositoriesResponse.language

            binding.txtStart.text = repositoriesResponse.currentPeriodStars.toString()
        }

        companion object{
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = LayoutRepoListItemBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }
    }

    fun setData(newData: List<RepositoriesResponse>){
        repo.clear()
        repoFilterList.clear()
        repo.addAll(newData)
        notifyDataSetChanged()
    }

    fun clearData(){
        repo.removeAll(mutableListOf())
        repoFilterList.removeAll(mutableListOf())
        notifyDataSetChanged()
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val charSearch = constraint.toString()
                if (charSearch.isEmpty()) {
                    repoFilterList = repo
                } else {
                    val resultList = ArrayList<RepositoriesResponse>()
                    for (row in repo) {
                        if (row.name.lowercase(Locale.ROOT).contains(charSearch.lowercase(Locale.ROOT)) || row.name.lowercase(Locale.ROOT).contains(charSearch.lowercase(Locale.ROOT))) {
                            resultList.add(row)
                        }
                    }
                    repoFilterList = resultList
                }
                val filterResults = FilterResults()
                filterResults.values = repoFilterList
                return filterResults
            }

            @Suppress("UNCHECKED_CAST")
            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                repoFilterList = results?.values as ArrayList<RepositoriesResponse>
                notifyDataSetChanged()
            }

        }
    }
}