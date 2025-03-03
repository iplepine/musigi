package com.zs.jyoon.musigi

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.zs.jyoon.currentplaying.ui.CurrentPlayingContent
import com.zs.jyoon.domain.model.Track
import com.zs.jyoon.domain.model.TrackInfo
import com.zs.jyoon.miniplayer.ui.MiniPlayer

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    navController: NavHostController,
    viewModel: MainViewModel = hiltViewModel()
) {
    val sheetState =
        rememberModalBottomSheetState(skipPartiallyExpanded = true) // ✅ BottomSheet 상태 관리
    var showSheet by remember { mutableStateOf(false) } // ✅ Sheet 열고 닫기 관리

    Scaffold(
        bottomBar = {
            MiniPlayer(
                artistName = "artistName",
                albumTitle = "albumTitle",
                trackName = "trackName",
                isPlaying = false,
                seekPosition = 0,
                artUriString = "artUriString",
                onClick = { showSheet = true }, // ✅ Sheet 열기
                onClickToggle = { } // ✅ 재생/일시정지
            )
        }
    ) { paddingValues ->
        Box(modifier = Modifier.padding(paddingValues)) {
            NavHost(navController, startDestination = "library") {
                addLibraryScreen(navController)
                addAlbumScreen(navController)
            }
        }

        if (showSheet) {
            ModalBottomSheet(
                onDismissRequest = { showSheet = false }, // ✅ 닫기
                sheetState = sheetState
            ) {
                CurrentPlayingContent(
                    isPlaying = false,
                    currentTrack = Track(
                        id = "id",
                        info = TrackInfo(
                            title = "title",
                            artistName = "artistName",
                            albumTitle = "albumTitle",
                            length = 0
                        ),
                        uriString = "uriString",
                        image = null
                    ),
                    progress = 0f,
                    volume = 0f,
                    onTogglePlayPause = { },
                    onSeekChanged = { },
                    onPrevious = { },
                    onNext = { },
                    onShuffle = { },
                    onRepeat = { },
                    onVolumeChange = { },
                    onClose = { showSheet = false } // ✅ Sheet 닫기
                )
            }
        }
    }
}