package com.zs.jyoon.domain.core.player.model

import com.zs.jyoon.domain.core.player.type.MediaType

interface MediaItem {
    val id: String
    val title: String
    val mediaType: MediaType
}