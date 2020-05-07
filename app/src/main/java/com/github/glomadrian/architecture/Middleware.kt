package com.github.glomadrian.architecture

import kotlinx.coroutines.flow.Flow

interface Middleware<A: Action, R: Result> {

    fun bind(actions: Flow<A>): Flow<R>
}