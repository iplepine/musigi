package com.zs.jyoon.library

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.zs.jyoon.domain.model.Album
import com.zs.jyoon.domain.model.AlbumInfo

@Composable
fun LibraryScreen(
    viewModel: LibraryViewModel = hiltViewModel(),
) {
    val library = viewModel.library.collectAsStateWithLifecycle().value

    AlbumList(
        albums = library.artists.flatMap { it.albums },
        onClickAlbum = { },
    )
}

@Composable
fun AlbumList(
    albums: List<Album>,
    onClickAlbum: (Album) -> Unit,
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = Modifier
            .fillMaxSize()
    ) {
        items(albums) { album ->
            AlbumItem(
                album = album,
                onClick = { onClickAlbum(album) }
            )
        }
    }
}

@Composable
fun AlbumItem(album: Album, onClick: () -> Unit) {
    Column(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.background)
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(16.dp),
    ) {
        AsyncImage(
            model = album.info.art,
            contentDescription = album.info.title,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
        )
        Spacer(modifier = Modifier.width(16.dp))
        Column {
            Text(
                text = album.info.title,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = album.info.artist,
                color = Color.Gray
            )
        }
    }
}

@Composable
@Preview
fun AlbumListPreview() {
    AlbumList(
        albums = listOf(
            Album(
                id = "1",
                info = AlbumInfo(
                    title = "Album",
                    artist = "Artist",
                    art = "https://picsum.photos/250/250"
                ),
                tracks = emptyList()
            ),
            Album(
                id = "2",
                info = AlbumInfo(
                    title = "Album",
                    artist = "Artist",
                    art = "https://picsum.photos/250/250"
                ),
                tracks = emptyList()
            ),
            Album(
                id = "3",
                info = AlbumInfo(
                    title = "Album",
                    artist = "Artist",
                    art = "https://picsum.photos/250/250"
                ),
                tracks = emptyList()
            ),
        ),
        onClickAlbum = {},
    )
}

@Composable
@Preview
fun AlbumItemPreview() {
    AlbumItem(
        album = Album(
            id = "1",
            info = AlbumInfo(
                title = "Album",
                artist = "Artist",
                art = ""
            ),
            tracks = emptyList()
        ),
        onClick = {}
    )
}