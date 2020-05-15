package com.github.glomadrian.architecture.actionwatcher

import com.github.glomadrian.architecture.Action

class ActionInfo(val action: Action, val timestamp: Long = System.currentTimeMillis())