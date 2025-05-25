package com.example.tvshowtracker.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import com.example.tvshowtracker.models.SearchResult
import com.example.tvshowtracker.data.TvMazeRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import com.example.tvshowtracker.TvMazeApplication

/**
 * ViewModel for the search screen
 * Manages the search state and handles show searches
 * @param repository The repository to fetch show data from
 */
class SearchViewModel(private val repository: TvMazeRepository) : ViewModel() {

    /**
     * StateFlow containing the current search results
     * Updates automatically when new results are fetched
     */
    private val _searchResults = MutableStateFlow<List<SearchResult>>(emptyList())
    val searchResults: StateFlow<List<SearchResult>> = _searchResults

    /**
     * Performs a search for TV shows
     * Updates the searchResults state with the results
     * @param query The search term to look for
     */
    fun search(query: String) {
        viewModelScope.launch {
            _searchResults.value = repository.searchShows(query)
        }
    }

    companion object {
        /**
         * Factory for creating SearchViewModel instances
         * Uses the application's container to get the repository instance
         */
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as TvMazeApplication)
                val repository = application.container.tvMazeRepository
                SearchViewModel(repository = repository)
            }
        }
    }
}
