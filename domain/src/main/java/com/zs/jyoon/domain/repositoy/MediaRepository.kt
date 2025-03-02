package com.zs.jyoon.domain.repositoy

import com.zs.jyoon.domain.model.Album
import com.zs.jyoon.domain.model.Library

interface MediaRepository {
    suspend fun getLibrary(): Library
    suspend fun getAlbum(albumId: String): Album
}