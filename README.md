# Github Trending Repository 

App that lists trending Github repositories

## Features

The android app lets you:

- Users can view the most trending repositories in Android from Github.
  
- Users can search the trending repository

- Offline Support

## Screenshots

[<img src="/readme/ss1.png" align="left"
width="200"
hspace="10" vspace="10">](/readme/ss1.png)
[<img src="/readme/ss2.png" align="center"
width="200"
hspace="10" vspace="10">](/readme/ss2.png)
[<img src="/readme/ss3.png" align="left"
width="200"
hspace="10" vspace="10">](/readme/ss3.png)
[<img src="/readme/ss4.png" align="center"
width="200"
hspace="10" vspace="10">](/readme/ss4.png)

## Permissions

On Android versions prior to Android 6.0, trending repository app requires the following permissions:

- Full Network Access.
  
- View Network Connections.

## API
Since there is no official API for Trending Repositories (it is one of the internal GitHub API’s),
<br />
I have decided to use [GitHub Trending API](https://github-trending-api-wonder.herokuapp.com/)

## Tech stack
- Minimum SDK level 26
- [Kotlin](https://kotlinlang.org/) based + [Coroutines](https://github.com/Kotlin/kotlinx.coroutines) for asynchronous.
- Dagger-Hilt (alpha) for dependency injection.
- JetPack
    - LiveData - notify domain layer data to views.
    - Lifecycle - dispose of observing data when lifecycle state changes.
    - ViewModel - UI related data holder, lifecycle aware.
    - Navigation Component - handle everything needed for in-app navigation.
    - View Binding - bind UI elements to data.
- Architecture
    - MVVM Architecture (View - DataBinding - ViewModel - Model)
    - Repository pattern
- [Glide](https://github.com/bumptech/glide) - loading images.
- [Retrofit2 & OkHttp3](https://github.com/square/retrofit) - construct the REST APIs and paging network data.
- [Material-Components](https://github.com/material-components/material-components-android) - Material design components like ripple animation, cardView.