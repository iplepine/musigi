package com.zs.jyoon.album.ui

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zs.jyoon.domain.core.Logger
import com.zs.jyoon.domain.core.player.type.PlayingStrategy
import com.zs.jyoon.domain.model.Album
import com.zs.jyoon.domain.model.Track
import com.zs.jyoon.domain.player.MediaPlayer
import com.zs.jyoon.domain.repositoy.MediaRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AlbumViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val mediaRepository: MediaRepository,
    private val mediaPlayer: MediaPlayer,
    private val logger: Logger
) : ViewModel() {

    private val _uiState: MutableStateFlow<UIState> = MutableStateFlow(UIState.Loading)
    val uiState: StateFlow<UIState> = _uiState

    val currentPlayingTrack: StateFlow<Track?> =
        mediaPlayer.currentMediaItem.filterIsInstance<Track>().stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(),
            null
        )

    init {
        val artistName = savedStateHandle.get<String>("artistName")
            ?: throw IllegalArgumentException("artistName is required")
        val albumId = savedStateHandle.get<String>("albumId")
            ?: throw IllegalArgumentException("albumId is required")

        viewModelScope.launch {
            val album = mediaRepository.getAlbum(artistName, albumId)
            _uiState.value = UIState.Success(album)
        }
    }

    fun playAlbum() {
        val state = uiState.value as? UIState.Success ?: return
        mediaPlayer.playList(state.album.tracks)
    }

    fun playWithShuffle() {
        val state = uiState.value as? UIState.Success ?: return
        mediaPlayer.setPlayingStrategy(PlayingStrategy.SHUFFLE)
        mediaPlayer.playList(state.album.tracks)
    }

    fun playTrack(track: Track) {
        val state = uiState.value as? UIState.Success ?: return
        mediaPlayer.playList(state.album.tracks)
        mediaPlayer.playItem(track)
    }

    sealed interface UIState {
        data object Loading : UIState
        data class Success(val album: Album) : UIState
    }
}