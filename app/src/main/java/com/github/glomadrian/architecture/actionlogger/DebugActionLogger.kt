package com.github.glomadrian.architecture.actionlogger

import android.util.Log
import com.github.glomadrian.BuildConfig
import com.github.glomadrian.architecture.ActionChannel

object DebugActionLogger {
    private var isRunning = false

    fun init() {
        if (BuildConfig.DEBUG && !isRunning) {
            isRunning = true
            ActionChannel.addOnActionListener {
                Log.d("Action Logger", "Executed action -> $it")
            }
        }
    }
}