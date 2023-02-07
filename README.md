# Github Trending Repository 

![Screenshot 2023-02-07 151640](https://user-images.githubusercontent.com/93249038/217210014-91d0b8bc-161c-42e8-b15e-8ecda6db5ea0.png)


## Functionality
App that lists trending Github repositories

## Features

The android app lets you:

- Users can view the most trending repositories in Android from Github.
  
- Users can search the trending repository

- Offline Support

## Screenshots

![Screenshot 2023-02-07 104229](https://user-images.githubusercontent.com/93249038/217155219-69cda7bf-6b5c-436c-b4ed-5b09fc905c42.png)


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
