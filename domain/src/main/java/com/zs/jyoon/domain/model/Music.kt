package com.zs.jyoon.domain.model

import com.zs.jyoon.domain.core.player.model.MediaItem
import com.zs.jyoon.domain.core.player.type.MediaType

data class Music(
    override val id: String,
    val info: MusicInfo
) : MediaItem {
    override val title: String
        get() = info.title

    override val mediaType: MediaType = MediaType.AUDIO
}