package com.zs.jyoon.album.ui

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zs.jyoon.domain.model.Album
import com.zs.jyoon.domain.model.Track
import com.zs.jyoon.domain.repositoy.MediaRepository
import com.zs.jyoon.domain.repositoy.PlaybackRepository
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
    private val playbackRepository: PlaybackRepository
) : ViewModel() {

    private val _uiState: MutableStateFlow<UIState> = MutableStateFlow(UIState.Loading)
    val uiState: StateFlow<UIState> = _uiState

    val currentPlayingTrack: StateFlow<Track?> =
        playbackRepository.currentPlayingItem.filterIsInstance(Track::class).stateIn(
            viewModelScope,
            started = SharingStarted.WhileSubscribed(),
            initialValue = null
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

    }

    fun playWithShuffle() {

    }

    fun playTrack(track: Track) {

    }

    sealed interface UIState {
        data object Loading : UIState
        data class Success(val album: Album) : UIState
    }
}