package com.example.tvshowtracker.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.tvshowtracker.models.Episode
import com.example.tvshowtracker.ui.DetailsViewModel
import com.example.tvshowtracker.utils.removeHtmlTags
import kotlinx.serialization.json.Json
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailsScreen(
    showId: Int,
    navController: NavController,
    viewModel: DetailsViewModel = viewModel(factory = DetailsViewModel.Factory)
) {
    val show by viewModel.showDetails.collectAsState()
    val episodes by viewModel.episodes.collectAsState()

    LaunchedEffect(showId) {
        viewModel.loadAll(showId)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        TopAppBar(
            title = { Text("Show Details", color = MaterialTheme.colorScheme.onPrimary) },
            navigationIcon = {
                IconButton(onClick = { navController.popBackStack() }) {
                    Icon(Icons.Filled.ArrowBack, contentDescription = "Back", tint = MaterialTheme.colorScheme.onPrimary)
                }
            },
            actions = {
                IconButton(onClick = { navController.navigate("home") }) {
                    Icon(Icons.Filled.Home, contentDescription = "Home", tint = MaterialTheme.colorScheme.onPrimary)
                }
                IconButton(onClick = { navController.navigate("settings") }) {
                    Icon(Icons.Filled.Settings, contentDescription = "Settings", tint = MaterialTheme.colorScheme.onPrimary)
                }
            },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = MaterialTheme.colorScheme.primary
            )
        )

        val scrollState = rememberScrollState()
        val seasons = episodes.map { it.season }.distinct().sorted()
        var selectedSeasonIndex by remember { mutableIntStateOf(0) }

        LaunchedEffect(seasons) {
            if (selectedSeasonIndex >= seasons.size) selectedSeasonIndex = 0
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
                .padding(16.dp),
            verticalArrangement = Arrangement.Top
        ) {
            show?.let {
                it.image?.original?.let { imageUrl ->
                    Image(
                        painter = rememberAsyncImagePainter(imageUrl),
                        contentDescription = it.name,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(220.dp)
                            .padding(bottom = 12.dp)
                    )
                }
                Text(
                    text = it.name,
                    style = MaterialTheme.typography.headlineMedium,
                    color = MaterialTheme.colorScheme.onBackground
                )
                Spacer(modifier = Modifier.height(8.dp))
                it.summary?.let { it1 ->
                    Text(
                        text = it1.removeHtmlTags(),
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                }
                Spacer(modifier = Modifier.height(16.dp))
                Divider(thickness = 2.dp, color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.2f))
                Spacer(modifier = Modifier.height(12.dp))
            }

            Text("Episodes",
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.onBackground)
            Spacer(modifier = Modifier.height(8.dp))

            if (seasons.isNotEmpty()) {
                ScrollableTabRow(
                    selectedTabIndex = selectedSeasonIndex,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    seasons.forEachIndexed { index, season ->
                        Tab(
                            selected = selectedSeasonIndex == index,
                            onClick = { selectedSeasonIndex = index },
                            text = {
                                Text(
                                    "Season $season",
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .width(100.dp),
                                    textAlign = TextAlign.Center
                                )
                            }
                        )
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))
                val selectedSeason = seasons.getOrNull(selectedSeasonIndex)
                episodes.filter { it.season == selectedSeason }.forEach { episode ->
                    EpisodeItem(episode = episode, navController = navController)
                }
            } else {
                Text("No episodes available", style = MaterialTheme.typography.bodyLarge)
            }
        }
    }
}

@Composable
fun EpisodeItem(episode: Episode, navController: NavController) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp)
            .clickable {
                val episodeJson = Json.encodeToString(Episode.serializer(), episode)
                val encodedJson = URLEncoder.encode(episodeJson, StandardCharsets.UTF_8.toString())
                navController.navigate("episode/$encodedJson")
            },
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Text(
                text = "S${episode.season}E${episode.number}: ${episode.name}",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onSurface
            )
            episode.summary?.let {
                Spacer(modifier = Modifier.height(4.dp))
                Text(text = it.removeHtmlTags(), style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurface)
            }
        }
    }
}
