package com.zs.jyoon.miniplayer.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zs.jyoon.domain.player.MediaPlayer
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MiniPlayerViewModel @Inject constructor(
    private val mediaPlayer: MediaPlayer
) : ViewModel() {
    val artistName: MutableStateFlow<String> = MutableStateFlow("")
    val albumTitle: MutableStateFlow<String> = MutableStateFlow("")
    val trackName: MutableStateFlow<String> = MutableStateFlow("")
    val artUriString: MutableStateFlow<String> = MutableStateFlow("")

    val isPlaying: StateFlow<Boolean> = mediaPlayer.isPlaying
    val seekPosition: StateFlow<Long> = mediaPlayer.seekPosition

    init {
        viewModelScope.launch {
            mediaPlayer.currentMediaItem.collectLatest {
                artistName.value = it?.artistName ?: "가수 없음"
                albumTitle.value = it?.title ?: "앨범 없음"
                trackName.value = it?.title ?: "가수 없음"
                artUriString.value = it?.image ?: ""
            }
        }
    }

    fun togglePlay() {
        if (mediaPlayer.isPlaying.value) {
            mediaPlayer.pause()
        } else {
            mediaPlayer.play()
        }
    }
}