package com.zs.jyoon.domain.repositoy

import com.zs.jyoon.domain.model.Album
import com.zs.jyoon.domain.model.Library
import kotlinx.coroutines.flow.Flow

interface MediaRepository {
    suspend fun getLibrary(): Library
    suspend fun getAlbum(artistName: String, albumId: String): Album
    suspend fun getTrackArt(artistId: String, albumId: String, trackId: String): Flow<String>
}