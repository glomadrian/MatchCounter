package com.github.glomadrian.mvi

import kotlinx.coroutines.flow.Flow

interface View<S: State, I: Intent> {
    fun intents(): Flow<I>
}