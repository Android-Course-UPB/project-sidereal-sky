# TV Show Finder

A simple and intuitive Android application built with Jetpack Compose that helps users discover TV shows. 
The app uses the TV Maze API to fetch show information and provides a clean interface for browsing shows and their details.

## Main Features

- `Search for TV shows by name`
- `View detailed show information including:`
  - Show image and title
  - Summary
  - List of episodes organized by season
- `Dark/Light theme support`

## How to Use

1. **Home Screen**
   - The app starts with a welcome screen

2. **Search Screen**
   - Enter a show name in the search bar
   - Results appear as you type
   - Each result displays the show's image and title
   - Tap on a show to view its details

3. **Details Screen**
   - View the show's full information
   - Browse episodes by season using the tab row
   - Each episode shows:
     - Episode number and title
     - Summary

4. **Settings Screen**
   - Toggle between light and dark theme

## Project Structure

```
com/example/tvshowtracker/
├── data/
│   ├── AppContainer.kt
│   └── TvMazeRepository.kt
├── models/
│   ├── Episode.kt
│   ├── Image.kt
│   ├── SearchResult.kt
│   └── Show.kt
├── navigation/
│   └── TvShowNavHost.kt
├── network/
│   └── TvMazeApiService.kt
├── ui/
│   ├── screens/
│   │   ├── DetailsScreen.kt
│   │   ├── HomeScreen.kt
│   │   ├── SearchScreen.kt
│   │   └── SettingsScreen.kt
│   ├── theme/
│   ├── DetailsViewModel.kt
│   └── SearchViewModel.kt
├── TvMazeApplication.kt
└── MainActivity.kt
```

## Architecture

The app follows the MVVM (Model-View-ViewModel) architecture pattern:

- **Model**: Data classes and repository layer
- **View**: Compose UI components
- **ViewModel**: State management and business logic

The app also uses a manual dependency injection approach with an Application Container to manage dependencies.

## Technical Stack

- **UI Framework**: Jetpack Compose
- **Architecture**: MVVM (Model-View-ViewModel)
- **Dependency Injection**: Manual DI with Application Container
- **Networking**: Retrofit with Kotlin Serialization
- **Image Loading**: Coil
- **State Management**: StateFlow
- **Navigation**: Jetpack Navigation Compose
- **Theme**: Material 3 with dynamic color support

## Resources

- [TV Maze API](https://www.tvmaze.com/api) for providing the show data
- [Jetpack Compose](https://developer.android.com/jetpack/compose) for the modern UI toolkit
- [Material 3](https://m3.material.io/) for the design system 