package com.example.tvshowtracker

import android.app.Application
import com.example.tvshowtracker.data.AppContainer
import com.example.tvshowtracker.data.DefaultAppContainer

/**
 * Application class for the TV Show Finder app
 * Initializes the dependency injection container and provides access to it
 */
class TvMazeApplication : Application() {
    /**
     * AppContainer instance used by the rest of classes to obtain dependencies
     */
    lateinit var container: AppContainer

    override fun onCreate() {
        super.onCreate()
        container = DefaultAppContainer()
    }
}

