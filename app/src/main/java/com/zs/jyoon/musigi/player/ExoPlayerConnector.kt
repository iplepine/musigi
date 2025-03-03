package com.zs.jyoon.musigi.player

import android.content.Context
import android.media.AudioManager
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.media3.common.Player
import androidx.media3.exoplayer.ExoPlayer
import com.zs.jyoon.domain.core.Logger
import com.zs.jyoon.domain.core.player.type.RepeatType
import com.zs.jyoon.domain.player.MediaPlayer
import com.zs.jyoon.domain.usecase.PlayNextMedia
import com.zs.jyoon.musigi.extension.toMedia3OrNull
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ExoPlayerConnector @Inject constructor(
    @ApplicationContext context: Context,
    private val mediaPlayer: MediaPlayer,
    private val exoPlayer: ExoPlayer,
    private val playNextMedia: PlayNextMedia,
    private val logger: Logger
) {

    private val coroutineScope = CoroutineScope(SupervisorJob() + Dispatchers.Main.immediate)

    init {
        val audioManager = context.getSystemService(Context.AUDIO_SERVICE) as AudioManager
        val currentVolume = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC)
        mediaPlayer.setVolume(currentVolume.toFloat())

        exoPlayer.addListener(object : Player.Listener {
            override fun onPlaybackStateChanged(playbackState: Int) {
                when (playbackState) {
                    Player.STATE_READY -> {
                        logger.e("ExoPlayerConnector", "PlaybackState: STATE_READY")
                        mediaPlayer.play()
                    }

                    Player.STATE_ENDED -> {
                        logger.e("ExoPlayerConnector", "PlaybackState: STATE_ENDED")
                        coroutineScope.launch { playNextMedia.invoke(Unit) }
                    }

                    Player.STATE_IDLE -> {
                        logger.e("ExoPlayerConnector", "PlaybackState: STATE_IDLE")
                        mediaPlayer.pause()
                    }
                }
            }

            override fun onRepeatModeChanged(repeatMode: Int) {
                logger.e("ExoPlayerConnector", "RepeatMode: $repeatMode")
            }

            override fun onVolumeChanged(volume: Float) {
                logger.e("ExoPlayerConnector", "Volume: $volume")
            }
        })
    }

    /**
     * LifecycleOwner의 생명주기에 맞춰 ExoPlayer와 MediaPlayer를 연결
     */
    fun connectWithLifecycle(lifecycleOwner: LifecycleOwner) {
        lifecycleOwner.lifecycleScope.launch { connectPlayingList() }
        lifecycleOwner.lifecycleScope.launch { connectPlayingItem() }
        lifecycleOwner.lifecycleScope.launch { connectSeekPosition() }
        lifecycleOwner.lifecycleScope.launch { connectIsPlaying() }
        lifecycleOwner.lifecycleScope.launch { connectRepeatMode() }
        lifecycleOwner.lifecycleScope.launch { connectVolume() }

        lifecycleOwner.lifecycleScope.launch { syncSeekBar() }
    }

    /**
     * 현재 재생 목록을 ExoPlayer에 설정
     */
    private suspend fun connectPlayingList() {
        mediaPlayer.currentPlayingList.collectLatest { list ->
            logger.d("ExoPlayerConnector", "PlayingList: $list")
            val mediaItems = list.mapNotNull { it.toMedia3OrNull() }
            exoPlayer.setMediaItems(mediaItems)
            exoPlayer.prepare()
            exoPlayer.play()
        }
    }

    /**
     * 현재 재생 중인 곡을 ExoPlayer에 설정
     */
    private suspend fun connectPlayingItem() {
        mediaPlayer.currentMediaItem.collectLatest { item ->
            logger.d("ExoPlayerConnector", "PlayingItem: $item")
            item?.toMedia3OrNull()?.let {
                exoPlayer.setMediaItem(it)
                exoPlayer.prepare()
                exoPlayer.play()
            }
        }
    }

    /**
     * SeekBar 변경 감지 → ExoPlayer에 반영
     */
    private suspend fun connectSeekPosition() {
        mediaPlayer.seekToPosition.collectLatest { position ->
            exoPlayer.seekTo(position)
            logger.d("ExoPlayerConnector", "SeekPosition: $position")
        }
    }

    /**
     * 재생/일시정지 상태 동기화
     */
    private suspend fun connectIsPlaying() {
        mediaPlayer.isPlaying.collectLatest { isPlaying ->
            if (isPlaying) exoPlayer.play() else exoPlayer.pause()

            logger.d("ExoPlayerConnector", "IsPlaying: $isPlaying")
        }
    }

    /**
     * 반복 모드 설정
     */
    private suspend fun connectRepeatMode() {
        mediaPlayer.repeatType.collectLatest { repeatType ->
            exoPlayer.repeatMode = when (repeatType) {
                RepeatType.NONE -> ExoPlayer.REPEAT_MODE_OFF
                RepeatType.ALL -> ExoPlayer.REPEAT_MODE_ALL
                RepeatType.ONE -> ExoPlayer.REPEAT_MODE_ONE
            }

            logger.d("ExoPlayerConnector", "RepeatMode: $repeatType")
        }
    }

    /**
     * 볼륨 조절
     */
    private suspend fun connectVolume() {
        mediaPlayer.volume.collectLatest { volume ->
            exoPlayer.volume = volume

            logger.d("ExoPlayerConnector", "Volume: $volume")
        }
    }

    private suspend fun syncSeekBar() {
        while (true) {
            mediaPlayer.updateSeekPosition(exoPlayer.currentPosition)
            mediaPlayer.updateDuration(exoPlayer.duration)
            delay(500L)
        }
    }
}
