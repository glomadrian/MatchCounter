package com.github.glomadrian.feed

import com.github.glomadrian.mvi.State

data class FeedViewState(
    val isLoading: Boolean,
    val error: Throwable?
) : State