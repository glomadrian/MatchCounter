package com.github.glomadrian.mvi

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.github.glomadrian.background
import com.github.glomadrian.mainThread
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.scan

abstract class ViewModel<I : Intent, S : State, A : Action, R : Result>: ViewModel() {

    private val stateLiveData: MutableLiveData<S> = MutableLiveData()

    private var state: S = initialState()

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
                stateLiveData.value = it
            }.flowOn(mainThread)
            .launchIn(GlobalScope)
    }

    abstract suspend fun intentToActionMatcher(intent: I): A

    abstract fun actionExecutor(counterAction: A): Flow<R>

    abstract fun reduceMatcher(previousState: S, result: R): S

    fun state(): LiveData<S> = stateLiveData

    abstract fun initialState(): S
}