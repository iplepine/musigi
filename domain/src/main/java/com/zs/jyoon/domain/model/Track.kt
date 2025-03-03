package com.zs.jyoon.domain.model

import com.zs.jyoon.domain.core.player.model.MediaItem
import com.zs.jyoon.domain.core.player.type.MediaType

data class Track(
    override val id: String,
    val info: TrackInfo,
    override val image: String?,
    override val sourceString: String
) : MediaItem {
    override val title: String
        get() = info.title

    override val artistName: String
        get() = info.artistName

    override val mediaType: MediaType = MediaType.AUDIO
}