package com.zs.jyoon.miniplayer.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.rememberAsyncImagePainter

@Composable
fun MiniPlayer(
    viewModel: MiniPlayerViewModel = hiltViewModel(),
    onClick: () -> Unit
) {
    val seekPosition = viewModel.seekPosition.collectAsStateWithLifecycle().value
    val isPlaying = viewModel.isPlaying.collectAsStateWithLifecycle().value
    val artistName = viewModel.artistName.collectAsStateWithLifecycle().value
    val albumTitle = viewModel.albumTitle.collectAsStateWithLifecycle().value
    val artUriString = viewModel.artUriString.collectAsStateWithLifecycle().value
    val trackName = viewModel.trackName.collectAsStateWithLifecycle().value

    MiniPlayerContent(
        seekPosition = seekPosition,
        isPlaying = isPlaying,
        artistName = artistName,
        albumTitle = albumTitle,
        artUriString = artUriString,
        trackName = trackName,
        onClick = onClick,
        onClickToggle = viewModel::togglePlay
    )
}

@Composable
fun MiniPlayerContent(
    seekPosition: Long,
    isPlaying: Boolean,
    artistName: String,
    albumTitle: String,
    artUriString: String,
    trackName: String,
    onClick: () -> Unit,
    onClickToggle: () -> Unit
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(8.dp),
        color = MaterialTheme.colorScheme.surface
    ) {
        Column {
            LinearProgressIndicator(
                progress = { seekPosition.toFloat() / 100 },
                color = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier
                    .height(1.dp)
                    .fillMaxWidth(),
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = onClickToggle) {
                    Icon(
                        imageVector = if (isPlaying) Icons.Default.Menu else Icons.Default.PlayArrow,
                        contentDescription = "Play/Pause"
                    )
                }
                Spacer(modifier = Modifier.width(8.dp))
                Column(modifier = Modifier.weight(1f)) {
                    Text(text = trackName, fontWeight = FontWeight.Bold)
                    Text(text = albumTitle)
                    Text(text = artistName, color = Color.Gray)
                }

                Image(
                    painter = rememberAsyncImagePainter(artUriString),
                    contentDescription = "$albumTitle album art",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(48.dp)
                        .clip(MaterialTheme.shapes.small)
                )
            }
        }
    }
}

@Composable
@Preview
fun MiniPlayerPreview() {
    MiniPlayerContent(
        artistName = "Artist",
        albumTitle = "Album",
        trackName = "Track",
        isPlaying = true,
        seekPosition = 0,
        artUriString = "",
        onClick = {},
        onClickToggle = {}
    )
}