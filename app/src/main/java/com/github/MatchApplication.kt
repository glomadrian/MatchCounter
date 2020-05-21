package com.github

import android.app.Application
import com.github.glomadrian.architecture.actionwatcher.ActionDebugger
import com.github.glomadrian.actionlogger.DebugActionLogger

class MatchApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        DebugActionLogger.init()
        ActionDebugger.init()
    }
}