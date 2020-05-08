package com.github.glomadrian.architecture

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.glomadrian.background
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.flatMapMerge
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map

abstract class ViewModel<I : Intent, S : State, A : Action, R : Result>(
    private val store: Store<A, R, S>
) : ViewModel() {

    fun processIntents(intents: Flow<I>) {
        intents
            .map { handleIntent(it) }
            .flatMapMerge { store.dispatch(flowOf(it)) { processIntents(intents) } }
            .launchIn(viewModelScope)
    }

    abstract suspend fun handleIntent(intent: I): A

    fun state(): LiveData<S> = store.getState()
}