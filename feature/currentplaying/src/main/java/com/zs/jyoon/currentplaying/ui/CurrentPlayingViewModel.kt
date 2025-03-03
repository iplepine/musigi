package com.zs.jyoon.currentplaying.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zs.jyoon.domain.core.player.type.PlayingStrategy
import com.zs.jyoon.domain.core.player.type.RepeatType
import com.zs.jyoon.domain.model.Track
import com.zs.jyoon.domain.player.MediaPlayer
import com.zs.jyoon.domain.usecase.PlayNextMedia
import com.zs.jyoon.domain.usecase.PlayPreviousMedia
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CurrentPlayingViewModel @Inject constructor(
    private val mediaPlayer: MediaPlayer,
    private val playNextMedia: PlayNextMedia,
    private val playPreviousMedia: PlayPreviousMedia,
) : ViewModel() {
    val currentPlayingTrack: StateFlow<Track?> =
        mediaPlayer.currentMediaItem.filterIsInstance<Track>().stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(),
            null
        )

    val seekPosition: StateFlow<Long> = mediaPlayer.seekPosition
    val duration: StateFlow<Long> = mediaPlayer.duration
    val isPlaying: StateFlow<Boolean> = mediaPlayer.isPlaying
    val playingStrategy: StateFlow<PlayingStrategy> = mediaPlayer.playingStrategy
    val repeatType: StateFlow<RepeatType> = mediaPlayer.repeatType
    val volume: StateFlow<Float> = mediaPlayer.volume


    /**
     * onTogglePlayPause = viewModel::togglePlay,
     *         onSeekChanged = viewModel::seekTo,
     *         onPrevious = viewModel::playPrevious,
     *         onNext = viewModel::playNext,
     *         onShuffle = viewModel::toggleShuffle,
     *         onRepeat = viewModel::toggleRepeat,
     *         onVolumeChange = viewModel::changeVolume
     */

    fun togglePlay() {
        viewModelScope.launch {
            if (isPlaying.value) {
                mediaPlayer.pause()
            } else {
                mediaPlayer.play()
            }
        }
    }

    fun seekTo(position: Float) {
        viewModelScope.launch {
            mediaPlayer.seekTo(position.toLong())
        }
    }

    fun playPrevious() {
        viewModelScope.launch {
            playPreviousMedia.invoke(Unit)
        }
    }

    fun playNext() {
        viewModelScope.launch {
            playNextMedia.invoke(Unit)
        }
    }

    fun toggleShuffle() {
        viewModelScope.launch {
            val nextStrategy = when (mediaPlayer.playingStrategy.value) {
                PlayingStrategy.SEQUENTIAL -> PlayingStrategy.SHUFFLE
                PlayingStrategy.SHUFFLE -> PlayingStrategy.SEQUENTIAL
            }
            mediaPlayer.setPlayingStrategy(nextStrategy)
        }
    }

    fun toggleRepeat() {
        viewModelScope.launch {
            val nextRepeatType = when (mediaPlayer.repeatType.value) {
                RepeatType.NONE -> RepeatType.ALL
                RepeatType.ALL -> RepeatType.ONE
                RepeatType.ONE -> RepeatType.NONE
            }

            mediaPlayer.setRepeatType(nextRepeatType)
        }
    }

    fun changeVolume(volume: Float) {
        viewModelScope.launch {
            mediaPlayer.setVolume(volume)
        }
    }
}