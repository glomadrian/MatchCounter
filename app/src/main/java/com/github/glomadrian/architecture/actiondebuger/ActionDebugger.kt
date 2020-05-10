package com.github.glomadrian.architecture.actiondebuger

import com.github.glomadrian.architecture.ActionChannel

object ActionDebugger {
    private lateinit var actionList: CircularList<ActionInfo>
    private var isRunning = false

    fun init(size: Int = 50) {
        isRunning = true
        actionList = CircularList(size)
        subscribeToActions()
    }

    private fun subscribeToActions() {
        ActionChannel.addOnActionListener {
            actionList.add(ActionInfo(it))
        }
    }

    fun isRunning() = isRunning

    fun getActions() = actionList as ArrayList<ActionInfo>
}