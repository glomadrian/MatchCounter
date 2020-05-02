package com.github.glomadrian.mvi

import com.github.glomadrian.background
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.scan

abstract class ViewModel<I : Intent, S : State, A : Action, R : Result> {

    abstract fun states(): Flow<S>
    open lateinit var state: S

    fun processIntents(intents: Flow<I>) {
        intents
            .map {
                intentToActionMatcher(it)
            }.flatMapConcat {
                actionExecutor(it)
            }.scan(state) { acc, value ->
                reduceMatcher(acc, value)
            }
            .distinctUntilChanged()
            .flowOn(background)
            .onEach {
                onNewState(it)
            }.launchIn(GlobalScope)
    }

    abstract suspend fun intentToActionMatcher(intent: I): A

    abstract fun actionExecutor(counterAction: A): Flow<R>

    abstract fun reduceMatcher(previousState: S, result: R): S

    abstract suspend fun onNewState(state: S)
}