package com.zs.jyoon.musigi.extension

import android.net.Uri
import com.zs.jyoon.domain.core.player.model.MediaItem
import androidx.media3.common.MediaItem as MediaItem3

fun MediaItem.toMedia3OrNull(): MediaItem3? {
    return runCatching { MediaItem3.fromUri(Uri.parse(this.sourceString)) }.getOrNull()
}