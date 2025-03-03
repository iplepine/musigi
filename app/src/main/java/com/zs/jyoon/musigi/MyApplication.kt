package com.zs.jyoon.musigi

import android.app.Application
import androidx.lifecycle.ProcessLifecycleOwner
import com.zs.jyoon.musigi.player.ExoPlayerConnector
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
class MyApplication : Application() {

    @Inject
    lateinit var exoPlayerConnector: ExoPlayerConnector

    override fun onCreate() {
        super.onCreate()

        connectExoPlayer()
    }

    private fun connectExoPlayer() {
        val processLifeCycleOwner = ProcessLifecycleOwner.get()
        exoPlayerConnector.connectWithLifecycle(processLifeCycleOwner)
    }
}