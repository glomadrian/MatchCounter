package com.github.glomadrian.counter.midleware

import com.github.glomadrian.CounterRepository
import com.github.glomadrian.architecture.Middleware
import com.github.glomadrian.counter.CounterAction
import com.github.glomadrian.counter.CounterResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.flow.map

class ClearCounter(
    private val repository: CounterRepository
) : Middleware<CounterAction, CounterResult> {

    override fun bind(actions: Flow<CounterAction>) =
        actions.filterIsInstance<CounterAction.ClearTeamPoints>()
            .map {
                repository.clearCounter()
            }.map {
                CounterResult.CounterCleared
            }
}