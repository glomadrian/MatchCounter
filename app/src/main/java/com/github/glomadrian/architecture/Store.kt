package com.github.glomadrian.architecture

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.github.glomadrian.actiondecorator.ActionPassthrough
import com.github.glomadrian.background
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapMerge
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.scan

class Store<A : Action, R : Result, S : State>(
    private val reducer: Reducer<R, S>,
    private val middleware: List<Middleware<A, R>>,
    private val initState: S,
    private val actionDecorator: List<ActionDecorator> = listOf(ActionPassthrough())
) {
    private val stateLiveData: MutableLiveData<S> = MutableLiveData()

    fun getState(): LiveData<S> = stateLiveData

    fun dispatch(action: Flow<A>, onUnexpectedError: () -> Unit?): Flow<*> =
        actionDecorator.asFlow()
            .flatMapMerge {
                dispatchToMiddleware(it.bind(action, currentStateOrDefault()) as Flow<A>, onUnexpectedError)
            }

    private fun dispatchToMiddleware(actions: Flow<A>, onUnexpectedError: () -> Unit?) =
        middleware.asFlow()
            .flatMapMerge { it.bind(actions) }
            .scan(currentStateOrDefault()) { acc, value -> reducer.reduce(acc, value) }
            .distinctUntilChanged()
            .catch { error ->
                emit(reducer.reduceUnexpectedError(currentStateOrDefault(), error))
                onUnexpectedError()
            }
            .flowOn(background)
            .onEach { stateLiveData.value = it }

    private fun currentStateOrDefault() = stateLiveData.value ?: initState
}