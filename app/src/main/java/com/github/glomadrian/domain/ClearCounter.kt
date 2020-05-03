package com.github.glomadrian.domain

import com.github.glomadrian.CounterRepository
import com.github.glomadrian.counter.CounterResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.lang.IllegalArgumentException

class ClearCounter(
    private val repository: CounterRepository
) {

    operator fun invoke(): Flow<CounterResult> {
        throw IllegalArgumentException()
    }
}