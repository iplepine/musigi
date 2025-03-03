package com.zs.jyoon.domain.core.player.model

import com.zs.jyoon.domain.core.player.type.MediaType

interface MediaItem {
    val id: String
    val title: String
    val image: String?
    val artistName: String
    val mediaType: MediaType
    val sourceString: String
}