package com.github.glomadrian.domain

import com.github.glomadrian.CounterRepository
import com.github.glomadrian.counter.CounterResult
import kotlinx.coroutines.flow.map

class ClearCounter(
    private val repository: CounterRepository
) {

    operator fun invoke() = repository.clearCounter().map {
        CounterResult.CounterCleared
    }
}