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

abstract class ViewModel<I : Intent, S : State, A : Action, R : Result> (
    private val actionExecutor: ActionExecutor<A, R>,
    private val reducerHandler: Reducer<R, S>
): ViewModel() {

    private val stateLiveData: MutableLiveData<S> = MutableLiveData()

    fun processIntents(intents: Flow<I>) {
        intents
            .map { handleIntent(it) }
            .flatMapMerge { actionExecutor(it) }
            .scan(currentStateOrDefault()) { acc, value -> reducerHandler.reduce(acc, value) }
            .distinctUntilChanged()
            .catch { error ->
                emit(reducerHandler.reduceUnexpectedError(currentStateOrDefault(), error))
            }
            .flowOn(background)
            .onEach { stateLiveData.value = it }
            .launchIn(viewModelScope)
    }

    private fun currentStateOrDefault() = stateLiveData.value ?: initialState()

    abstract suspend fun handleIntent(intent: I): A

    fun state(): LiveData<S> = stateLiveData

    abstract fun initialState(): S
}