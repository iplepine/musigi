package com.zs.jyoon.media.repository

import android.content.Context
import android.net.Uri
import com.zs.jyoon.domain.model.Album
import com.zs.jyoon.domain.model.AlbumInfo
import com.zs.jyoon.domain.model.Artist
import com.zs.jyoon.domain.model.Library
import com.zs.jyoon.domain.model.Track
import com.zs.jyoon.domain.model.TrackInfo
import com.zs.jyoon.domain.repositoy.MediaRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MediaRepositoryImpl @Inject constructor(
    @ApplicationContext private val context: Context
) : MediaRepository {

    private var cachedLibrary: Library? = null

    // TODO 변경 감지 필요
    override suspend fun getLibrary(): Library {
        val assets = context.assets
        val artistNames = assets.list("tracks") ?: emptyArray()

        val artists = artistNames.map { artistName ->
            val albumNames = assets.list("tracks/$artistName") ?: emptyArray()

            val albums = albumNames.map { albumName ->
                val trackFiles = assets.list("tracks/$artistName/$albumName") ?: emptyArray()
                val tracks = trackFiles.map { fileName ->
                    Track(
                        id = fileName,
                        info = TrackInfo(
                            title = fileName.removeSuffix(".mp3"),
                            artistName = artistName,
                            albumTitle = albumName,
                            length = 0
                        ),
                        uriString = getRawResourceUri(fileName).toString(),
                        image = "https://picsum.photos/250/250"
                    )
                }
                Album(
                    id = albumName,
                    info = AlbumInfo(
                        art = "https://picsum.photos/250/250",
                        title = albumName,
                        artist = artistName
                    ),
                    tracks = tracks
                )
            }

            Artist(
                name = artistName,
                albums = albums
            )
        }

        return Library(artists).also {
            cachedLibrary = it
        }
    }

    override suspend fun getAlbum(artistName: String, albumId: String): Album {
        val library = cachedLibrary ?: getLibrary()
        return library.artists.find {
            it.name == artistName
        }?.albums?.find {
            it.id == albumId
        } ?: throw IllegalArgumentException("Album not found")
    }

    // TODO 음악 파일에서 이미지 resource 추출 필요
    override suspend fun getTrackArt(
        artistId: String,
        albumId: String,
        trackId: String
    ): Flow<String> {
        TODO("Not yet implemented")
    }

    private fun getRawResourceUri(fileName: String): Uri {
        val resourceId = context.resources.getIdentifier(
            fileName.removeSuffix(".mp3"), "raw", context.packageName
        )
        return Uri.parse("android.resource://${context.packageName}/$resourceId")
    }
}
