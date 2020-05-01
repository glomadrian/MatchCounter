package com.github.glomadrian.mvi

import kotlinx.coroutines.flow.Flow

interface ViewModel<I: Intent, S: State> {
    fun states(): Flow<S>
    fun processIntents(intents: Flow<I>)
}