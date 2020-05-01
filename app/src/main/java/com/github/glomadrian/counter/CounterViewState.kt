package com.github.glomadrian.counter

import com.github.glomadrian.mvi.State

data class CounterViewState(
    val isLoading: Boolean,
    val error: Throwable?
) : State