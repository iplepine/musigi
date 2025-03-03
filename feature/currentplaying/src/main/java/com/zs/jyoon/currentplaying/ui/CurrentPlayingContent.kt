package com.zs.jyoon.currentplaying.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.zs.jyoon.domain.core.player.type.PlayingStrategy
import com.zs.jyoon.domain.core.player.type.RepeatType
import com.zs.jyoon.domain.model.Track


@Composable
fun CurrentPlayingScreen(
    viewModel: CurrentPlayingViewModel = hiltViewModel(),
    onClose: () -> Unit
) {
    val currentTrack = viewModel.currentPlayingTrack.collectAsStateWithLifecycle().value
    val seekPosition = viewModel.seekPosition.collectAsStateWithLifecycle().value
    val duration = viewModel.duration.collectAsStateWithLifecycle().value
    val isPlaying = viewModel.isPlaying.collectAsStateWithLifecycle().value
    val volume = viewModel.volume.collectAsStateWithLifecycle().value
    val playingStrategy = viewModel.playingStrategy.collectAsStateWithLifecycle().value
    val repeatType = viewModel.repeatType.collectAsStateWithLifecycle().value

    if (currentTrack == null) {
        LoadingContent()
    } else {
        CurrentPlayingContent(
            isPlaying = isPlaying,
            currentTrack = currentTrack,
            seekPosition = seekPosition,
            duration = duration,
            playingStrategy = playingStrategy,
            repeatType = repeatType,
            volume = volume,
            onTogglePlayPause = viewModel::togglePlay,
            onSeekChanged = viewModel::seekTo,
            onPrevious = viewModel::playPrevious,
            onNext = viewModel::playNext,
            onShuffle = viewModel::toggleShuffle,
            onRepeat = viewModel::toggleRepeat,
            onVolumeChange = viewModel::changeVolume,
            onClose = onClose
        )
    }
}

@Composable
fun LoadingContent() {
    Text(text = "Loading...")
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CurrentPlayingContent(
    isPlaying: Boolean,
    currentTrack: Track,
    seekPosition: Long,
    duration: Long,
    playingStrategy: PlayingStrategy,
    repeatType: RepeatType,
    volume: Float,
    onTogglePlayPause: () -> Unit,
    onSeekChanged: (Float) -> Unit,
    onPrevious: () -> Unit,
    onNext: () -> Unit,
    onShuffle: () -> Unit,
    onRepeat: () -> Unit,
    onVolumeChange: (Float) -> Unit,
    onClose: () -> Unit
) {
    // ✅ 현재 재생 중인 곡 정보
    ModalBottomSheet(
        onDismissRequest = onClose,
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .background(MaterialTheme.colorScheme.background),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // 현재 재생 중인 앨범아트
            AsyncImage(
                model = currentTrack.image,
                contentDescription = currentTrack.info.title,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(160.dp)
                    .clip(RoundedCornerShape(16.dp))
            )

            Spacer(modifier = Modifier.height(16.dp))

            // 트랙 정보
            Text(
                text = currentTrack.info.title,
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = currentTrack.info.artistName,
                style = MaterialTheme.typography.bodyMedium,
                color = Color.Gray
            )

            Spacer(modifier = Modifier.height(8.dp))

            // 컨트롤 버튼 (이전, 재생/일시정지, 다음)
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Button(
                    onClick = onShuffle,
                    modifier = Modifier.weight(1f)
                ) {
                    Text(text = playingStrategy.name)
                }
                IconButton(
                    onClick = onPrevious,
                    modifier = Modifier.weight(1f)
                ) {
                    Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "이전 곡")
                }
                IconButton(
                    onClick = onTogglePlayPause,
                    modifier = Modifier.weight(1f)
                ) {
                    Icon(
                        imageVector = if (isPlaying) Icons.Filled.Menu else Icons.Filled.PlayArrow,
                        contentDescription = "재생/일시정지"
                    )
                }
                IconButton(
                    onClick = onNext,
                    modifier = Modifier.weight(1f)
                ) {
                    Icon(Icons.AutoMirrored.Filled.KeyboardArrowRight, contentDescription = "다음 곡")
                }
                Button(
                    onClick = onRepeat,
                    modifier = Modifier.weight(1f)
                ) {
                    Text(text = repeatType.name)
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            // 볼륨 컨트롤
            Text(text = "Volume", style = MaterialTheme.typography.bodySmall)
            Slider(
                value = volume,
                onValueChange = onVolumeChange,
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(36.dp))

            // SeekBar (재생 위치)
            Slider(
                value = seekPosition.toFloat(),
                onValueChange = onSeekChanged,
                valueRange = 0f..duration.toFloat(),
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}