package com.github.glomadrian.architecture

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.consumeEach
import kotlinx.coroutines.launch

typealias OnActionListener = (Action) -> Unit

object ActionChannel {
    private val actionChannel = Channel<Action>()
    private val listeners = mutableListOf<OnActionListener>()

    init {
        GlobalScope.launch {
            actionChannel.consumeEach { action ->
                callListeners(action)
            }
        }
    }

    private fun CoroutineScope.callListeners(action: Action) {
        try {
            listeners.map {
                async {
                    it(action)
                }
            }
        } catch (ignored: Throwable) {
        }
    }

    suspend fun emitAction(action: Action) {
        actionChannel.send(action)
    }

    fun addOnActionListener(listener: OnActionListener) {
        listeners.add(listener)
    }

    fun removeOnActionListener(listener: OnActionListener) {
        listeners.remove(listener)
    }
}