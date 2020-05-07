package com.github.glomadrian.architecture

interface Reducer <R: Result, S: State> {

    fun reduce(previousState: S, result: R): S

    fun reduceUnexpectedError(previousState: S, error: Throwable): S
}