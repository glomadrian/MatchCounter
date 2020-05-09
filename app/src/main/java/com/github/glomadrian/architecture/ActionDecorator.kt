package com.github.glomadrian.architecture

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

abstract class ActionDecorator {
    fun bind(action: Flow<Action>, state: State) = action.map {
        decorate(it, state)
    }

    abstract fun decorate(action: Action, state: State): Action
}