package com.github.glomadrian.architecture

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.glomadrian.background
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapMerge
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.scan

abstract class ViewModel<I : Intent, S : State, A : Action, R : Result> : ViewModel() {

    private val stateLiveData: MutableLiveData<S> = MutableLiveData()

    fun processIntents(intents: Flow<I>) {
        intents
            .map { intentToActionMatcher(it) }
            .flatMapMerge { actionExecutor(it) }
            .scan(currentStateOrDefault()) { acc, value -> reduceMatcher(acc, value) }
            .distinctUntilChanged()
            .catch { error ->
                emit(genericErrorReducer(currentStateOrDefault(), error))
            }
            .flowOn(background)
            .onEach { stateLiveData.value = it }
            .launchIn(viewModelScope)
    }

    private fun currentStateOrDefault() = stateLiveData.value ?: initialState()

    abstract suspend fun intentToActionMatcher(intent: I): A

    abstract fun actionExecutor(counterAction: A): Flow<R>

    abstract fun reduceMatcher(previousState: S, result: R): S

    fun state(): LiveData<S> = stateLiveData

    abstract fun initialState(): S

    abstract fun genericErrorReducer(previousState: S, error: Throwable): S
}