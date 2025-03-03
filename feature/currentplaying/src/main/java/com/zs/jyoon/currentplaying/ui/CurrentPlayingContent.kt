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
import androidx.compose.material.icons.filled.Refresh
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
import coil.compose.AsyncImage
import com.zs.jyoon.domain.model.Track


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CurrentPlayingContent(
    isPlaying: Boolean,
    currentTrack: Track,
    progress: Float,
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
        onDismissRequest = { onClose() },
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
                IconButton(onClick = onShuffle) {
                    Icon(Icons.Filled.Refresh, contentDescription = "셔플 재생")
                }
                IconButton(onClick = onPrevious) {
                    Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "이전 곡")
                }
                IconButton(onClick = onTogglePlayPause) {
                    Icon(
                        imageVector = if (isPlaying) Icons.Filled.Menu else Icons.Filled.PlayArrow,
                        contentDescription = "재생/일시정지"
                    )
                }
                IconButton(onClick = onNext) {
                    Icon(Icons.AutoMirrored.Filled.KeyboardArrowRight, contentDescription = "다음 곡")
                }
                IconButton(onClick = onRepeat) {
                    Icon(Icons.Filled.Refresh, contentDescription = "반복")
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

            // SeekBar (프로그레스 바)
            Slider(
                value = progress,
                onValueChange = onSeekChanged,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}