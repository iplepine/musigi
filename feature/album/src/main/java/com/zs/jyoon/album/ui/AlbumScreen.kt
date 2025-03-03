package com.zs.jyoon.album.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.zs.jyoon.domain.model.Album
import com.zs.jyoon.domain.model.AlbumInfo
import com.zs.jyoon.domain.model.Track

@Composable
fun AlbumScreen(
    navController: NavHostController, // 네비게이션을 위한 컨트롤러
    viewModel: AlbumViewModel = hiltViewModel(),
) {
    val uiState = viewModel.uiState.collectAsStateWithLifecycle().value
    val currentPlayingTrack = viewModel.currentPlayingTrack.collectAsStateWithLifecycle().value

    when (uiState) {
        is AlbumViewModel.UIState.Loading -> {
            LoadingComponent()
        }

        is AlbumViewModel.UIState.Success -> {
            AlbumContent(
                album = uiState.album,
                currentPlayingTrack = currentPlayingTrack,
                onPlayAlbum = viewModel::playAlbum,
                onPlayWithShuffle = viewModel::playWithShuffle,
                onClickTrack = viewModel::playTrack
            )
        }
    }
}

@Composable
fun LoadingComponent() {
    Box(modifier = Modifier.fillMaxSize()) {
        Text(text = "로딩 중...", modifier = Modifier.align(Alignment.Center))
    }
}

@Composable
fun AlbumContent(
    album: Album,
    currentPlayingTrack: Track?,
    onPlayAlbum: () -> Unit, // 앨범 전체 재생
    onPlayWithShuffle: () -> Unit, // 앨범 전체 임의 재생
    onClickTrack: (Track) -> Unit, // 곡을 선택했을 때
) {
    Box(/*modifier = Modifier.padding(paddingValues)*/) {
        Column(modifier = Modifier.fillMaxSize()) {
            // 앨범 제목과 아티스트 이름 표시
            Text(
                text = album.info.title,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(16.dp)
            )
            Text(
                text = album.info.artistName,
                color = Color.Gray,
                modifier = Modifier.padding(start = 16.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            // 앨범 전체 재생 버튼
            Row {
                Button(
                    onClick = onPlayAlbum,
                    modifier = Modifier
                        .weight(1f)
                        .padding(16.dp)
                ) {
                    Text("전체 재생")
                }

                Button(
                    onClick = onPlayAlbum,
                    modifier = Modifier
                        .weight(1f)
                        .padding(16.dp)
                ) {
                    Text("셔플")
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // 곡 목록 표시
            LazyColumn(
                modifier = Modifier.fillMaxSize()
            ) {
                items(album.tracks) { track ->
                    TrackItem(
                        track = track,
                        onClick = { onClickTrack(track) },
                        isPlaying = currentPlayingTrack == track
                    )
                }
            }
        }
    }
}

@Composable
fun TrackItem(track: Track, onClick: () -> Unit, isPlaying: Boolean) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(if (isPlaying) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.background)
            .clickable { onClick() }
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // 트랙 이미지
        AsyncImage(
            model = track.image,
            contentDescription = track.info.title,
            modifier = Modifier
                .size(50.dp)
                .clip(RoundedCornerShape(8.dp))
        )

        Spacer(modifier = Modifier.width(16.dp))

        // 트랙 제목과 아티스트
        Column(modifier = Modifier.weight(1f)) {
            Text(text = track.info.title, fontWeight = FontWeight.Bold)
            Text(text = track.info.artistName, color = Color.Gray)
        }
    }
}


@Preview(backgroundColor = 0xFFFFFFFF, showBackground = true)
@Composable
fun AlbumContentPreview() {
    val album = Album(
        id = "albumId",
        info = AlbumInfo(
            art = "https://picsum.photos/250/250",
            title = "Album Title",
            artistName = "Artist Name"
        ),
        tracks = listOf(
            Track(
                id = "trackId",
                info = com.zs.jyoon.domain.model.TrackInfo(
                    title = "Track Title",
                    artistName = "Artist Name",
                    albumTitle = "Album Title",
                    length = 0
                ),
                sourceString = "uriString",
                image = "https://picsum.photos/250/250"
            ),
            Track(
                id = "trackId2",
                info = com.zs.jyoon.domain.model.TrackInfo(
                    title = "Track Title2",
                    artistName = "Artist Name2",
                    albumTitle = "Album Title2",
                    length = 0
                ),
                sourceString = "uriString",
                image = "https://picsum.photos/250/250"
            ),
        )
    )
    AlbumContent(
        album = album,
        currentPlayingTrack = Track(
            id = "trackId",
            info = com.zs.jyoon.domain.model.TrackInfo(
                title = "Track Title",
                artistName = "Artist Name",
                albumTitle = "Album Title",
                length = 0
            ),
            sourceString = "uriString",
            image = "https://picsum.photos/250/250"
        ),
        onPlayAlbum = {},
        onPlayWithShuffle = {},
        onClickTrack = {}
    )
}