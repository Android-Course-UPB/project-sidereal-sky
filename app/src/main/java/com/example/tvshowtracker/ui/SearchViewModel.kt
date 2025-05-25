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

class SearchViewModel(private val repository: TvMazeRepository) : ViewModel() {

    private val _searchResults = MutableStateFlow<List<SearchResult>>(emptyList())
    val searchResults: StateFlow<List<SearchResult>> = _searchResults

    fun search(query: String) {
        viewModelScope.launch {
            _searchResults.value = repository.searchShows(query)
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as TvMazeApplication)
                val repository = application.container.tvMazeRepository
                SearchViewModel(repository = repository)
            }
        }
    }
}
