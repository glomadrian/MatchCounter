package com.github.glomadrian.architecture

import kotlinx.coroutines.flow.Flow

interface View<S: State, I: Intent> {
    fun intents(): Flow<I>
}