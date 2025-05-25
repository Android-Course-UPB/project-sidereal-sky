package com.example.tvshowtracker.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import com.example.tvshowtracker.models.Episode
import com.example.tvshowtracker.models.Show
import com.example.tvshowtracker.data.TvMazeRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import com.example.tvshowtracker.TvMazeApplication

/**
 * ViewModel for the show details screen
 * Manages the show details and episodes
 * @param repository The repository to fetch show data from
 */
class DetailsViewModel(private val repository: TvMazeRepository) : ViewModel() {
    /**
     * StateFlow containing the current show details
     * Updates automatically when show details are fetched
     */
    private val _showDetails = MutableStateFlow<Show?>(null)
    val showDetails: StateFlow<Show?> = _showDetails

    /**
     * StateFlow containing the current show's episodes
     * Updates automatically when episodes are fetched
     */
    private val _episodes = MutableStateFlow<List<Episode>>(emptyList())
    val episodes: StateFlow<List<Episode>> = _episodes

    /**
     * Loads the details for a specific show
     * @param showId The ID of the show to load details for
     */
    fun loadShowDetails(showId: Int) {
        viewModelScope.launch {
            _showDetails.value = repository.getShowDetails(showId)
        }
    }

    /**
     * Loads all episodes for a specific show
     * @param showId The ID of the show to load episodes for
     */
    fun loadEpisodes(showId: Int) {
        viewModelScope.launch {
            _episodes.value = repository.getEpisodes(showId)
        }
    }

    /**
     * Loads both show details and episodes for a specific show
     * @param showId The ID of the show to load data for
     */
    fun loadAll(showId: Int) {
        loadShowDetails(showId)
        loadEpisodes(showId)
    }

    companion object {
        /**
         * Factory for creating DetailsViewModel instances
         * Uses the application's container to get the repository instance
         */
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as TvMazeApplication)
                val repository = application.container.tvMazeRepository
                DetailsViewModel(repository = repository)
            }
        }
    }
}