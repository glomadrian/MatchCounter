package com.github.glomadrian.architecture

import kotlinx.coroutines.flow.Flow

/**
 * Given an Action this will handle it and execute the logic to create a result
 * Side effects goes here
 * Many results can be send using emit(result)
 */
interface ActionExecutor<A : Action, R : Result> {

    operator fun invoke(counterAction: A): Flow<R>
}