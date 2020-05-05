package com.github.glomadrian.domain

import com.github.glomadrian.CounterRepository
import com.github.glomadrian.counter.CounterResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlin.random.Random

class ClearCounter(
    private val repository: CounterRepository
) {

    operator fun invoke(): Flow<CounterResult> =
        repository.clearCounter().map {
        CounterResult.CounterCleared
        }.flatMapConcat {
            randomlyFail(it)
        }

    private fun randomlyFail(result: CounterResult) = flow {
        if (Random.nextBoolean()) {
            throw IllegalAccessException()
        }
        emit(result)
    }
}