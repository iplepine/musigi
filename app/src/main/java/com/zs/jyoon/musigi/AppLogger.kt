package com.zs.jyoon.musigi

import android.util.Log
import com.zs.jyoon.domain.core.Logger

object AppLogger : Logger {
    override fun d(tag: String, message: String) {
        Log.d("AppLogger | $tag", message)
    }

    override fun e(tag: String, message: String) {
        Log.e("AppLogger | $tag", message)
    }
}