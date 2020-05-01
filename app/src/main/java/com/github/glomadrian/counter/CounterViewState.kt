package com.github.glomadrian.counter

import com.github.glomadrian.mvi.State

data class CounterViewState(
    val isLoading: Boolean,
    val teamAPoints: Int,
    val teamBPoints: Int,
    val error: Throwable?
) : State