package com.example.tvshowtracker

import android.app.Application
import com.example.tvshowtracker.data.AppContainer
import com.example.tvshowtracker.data.DefaultAppContainer

class TvMazeApplication : Application() {
    /** AppContainer instance used by the rest of classes to obtain dependencies */
    lateinit var container: AppContainer
    override fun onCreate() {
        super.onCreate()
        container = DefaultAppContainer()
    }
}

