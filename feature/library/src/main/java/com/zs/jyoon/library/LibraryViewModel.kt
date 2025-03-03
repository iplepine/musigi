package com.zs.jyoon.library

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zs.jyoon.domain.model.Library
import com.zs.jyoon.domain.model.Track
import com.zs.jyoon.domain.player.MediaPlayer
import com.zs.jyoon.domain.usecase.GetLibrary
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class LibraryViewModel @Inject constructor(
    private val getLibrary: GetLibrary,
    private val player: MediaPlayer
) : ViewModel() {

    val library: StateFlow<Library> = getLibrary.invoke(Unit).stateIn(
        viewModelScope,
        started = SharingStarted.Eagerly,
        initialValue = Library(emptyList())
    )

    val currentPlayingTrack: StateFlow<Track?> = player.currentMediaItem
        .filterIsInstance(Track::class)
        .stateIn(
            viewModelScope,
            started = SharingStarted.Eagerly,
            initialValue = null
        )

    val currentAlbumName: StateFlow<String> = currentPlayingTrack.map {
        it?.info?.albumTitle ?: ""
    }.stateIn(
        viewModelScope,
        started = SharingStarted.Eagerly,
        initialValue = ""
    )
}