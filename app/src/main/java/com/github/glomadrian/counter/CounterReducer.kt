package com.github.glomadrian.counter

import com.github.glomadrian.architecture.Reducer
import com.github.glomadrian.domain.Team

class CounterReducer : Reducer<CounterResult, CounterViewState> {
    override fun reduce(previousState: CounterViewState, result: CounterResult) =
    when (result) {
        is CounterResult.AddPointsResult.PointsAdded -> reducePointsAdded(previousState, result)
        is CounterResult.AddPointsResult.PointNotAddedError -> pointNotAddedErrorReducer(
        previousState
        )
        CounterResult.NoResult -> previousState
        CounterResult.CounterCleared -> CounterViewState(false, 0, 0, null)
        is CounterResult.CounterClearedError -> counterClearedErrorReducer(
        previousState,
        result.error
        )
        CounterResult.Loading -> loadingReducer(previousState)
    }

    private fun reducePointsAdded(
        previousState: CounterViewState,
        result: CounterResult.AddPointsResult.PointsAdded
    ) = when (result.team) {
        is Team.A -> previousState.copy(
            isLoading = false,
            teamAPoints = previousState.teamAPoints + result.points
        )
        is Team.B -> previousState.copy(
            isLoading = false,
            teamBPoints = previousState.teamBPoints + result.points
        )
    }

    private fun pointNotAddedErrorReducer(previousState: CounterViewState) = previousState

    private fun loadingReducer(previousState: CounterViewState) =
        previousState.copy(isLoading = true)

    private fun counterClearedErrorReducer(previousState: CounterViewState, error: Throwable) =
        previousState.copy(error = error, isLoading = false)

    override fun reduceUnexpectedError(
        previousState: CounterViewState,
        error: Throwable
    ) = previousState.copy(error = error, isLoading = false)
}