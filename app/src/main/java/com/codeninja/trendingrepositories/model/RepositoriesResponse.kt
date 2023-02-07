package com.codeninja.trendingrepositories.model

import com.google.gson.annotations.SerializedName

data class RepositoriesResponse (

    @SerializedName("author") val author : String,
    @SerializedName("name") val name : String,
    @SerializedName("avatar") val avatar : String,
    @SerializedName("url") val url : String,
    @SerializedName("description") val description : String,
    @SerializedName("language") val language : String,
    @SerializedName("languageColor") val languageColor : String,
    @SerializedName("stars") val stars : Int,
    @SerializedName("forks") val forks : Int,
    @SerializedName("currentPeriodStars") val currentPeriodStars : Int,
    @SerializedName("builtBy") val builtBy : List<BuiltBy>

    )

data class BuiltBy (

    @SerializedName("username") val username : String,
    @SerializedName("href") val href : String,
    @SerializedName("avatar") val avatar : String
)