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

class DetailsViewModel(private val repository: TvMazeRepository) : ViewModel() {
    private val _showDetails = MutableStateFlow<Show?>(null)
    val showDetails: StateFlow<Show?> = _showDetails

    private val _episodes = MutableStateFlow<List<Episode>>(emptyList())
    val episodes: StateFlow<List<Episode>> = _episodes

    fun loadShowDetails(showId: Int) {
        viewModelScope.launch {
            _showDetails.value = repository.getShowDetails(showId)
        }
    }

    fun loadEpisodes(showId: Int) {
        viewModelScope.launch {
            _episodes.value = repository.getEpisodes(showId)
        }
    }

    fun loadAll(showId: Int) {
        loadShowDetails(showId)
        loadEpisodes(showId)
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as TvMazeApplication)
                val repository = application.container.tvMazeRepository
                DetailsViewModel(repository = repository)
            }
        }
    }
}