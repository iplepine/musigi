package com.zs.jyoon.media.repository

import android.content.Context
import android.content.res.AssetManager
import android.content.res.Resources
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@ExperimentalCoroutinesApi
@RunWith(RobolectricTestRunner::class) // ✅ Robolectric 실행
class MediaRepositoryImplTest {

    private lateinit var repository: MediaRepositoryImpl
    private lateinit var mockContext: Context
    private lateinit var mockAssets: AssetManager
    private lateinit var mockResources: Resources

    @Before
    fun setup() {
        // ✅ Mock Context 및 의존성 초기화
        mockContext = mockk(relaxed = true)
        mockAssets = mockk()
        mockResources = mockk()

        every { mockContext.assets } returns mockAssets
        every { mockContext.resources } returns mockResources
        every { mockContext.packageName } returns "com.example.app"

        repository = MediaRepositoryImpl(mockContext)

        every { mockResources.getIdentifier(any(), any(), any()) } returns 12345
    }

    @Test
    fun `getLibrary() should return correct structure`() = runTest {
        // ✅ Given - Mock AssetManager to return fake folder structure
        every { mockAssets.list("tracks") } returns arrayOf("Artist1")
        every { mockAssets.list("tracks/Artist1") } returns arrayOf("Album1")
        every { mockAssets.list("tracks/Artist1/Album1") } returns arrayOf("Song1.mp3", "Song2.mp3")

        // ✅ When - getLibrary() 호출
        val library = repository.getLibrary()

        // ✅ Then - Library 구조 검증
        assertEquals(1, library.artists.size)
        assertEquals("Artist1", library.artists[0].name)
        assertEquals(1, library.artists[0].albums.size)
        assertEquals("Album1", library.artists[0].albums[0].id)
        assertEquals(2, library.artists[0].albums[0].tracks.size)
        assertEquals("Song1", library.artists[0].albums[0].tracks[0].info.title)
        assertEquals("Song2", library.artists[0].albums[0].tracks[1].info.title)
    }

    @Test
    fun `getAlbum() should return correct album`() = runTest {
        // ✅ Given - Mock AssetManager의 list() 설정
        every { mockAssets.list("tracks") } returns arrayOf("Artist1")
        every { mockAssets.list("tracks/Artist1") } returns arrayOf("Album1", "Album2")
        every { mockAssets.list("tracks/Artist1/Album1") } returns arrayOf("Song1.mp3")
        every { mockAssets.list("tracks/Artist1/Album2") } returns arrayOf("Song2.mp3")

        repository.getLibrary() // ✅ Library 로드

        // ✅ When - 특정 앨범 가져오기
        val album = repository.getAlbum("Artist1", "Album1")

        // ✅ Then - 앨범 검증
        assertEquals("Album1", album.id)
        assertEquals(1, album.tracks.size)
        assertEquals("Song1", album.tracks[0].info.title)
    }
}