package com.github.glomadrian.counter

import androidx.lifecycle.ViewModelProvider
import com.github.glomadrian.domain.AddPointsToTeam
import com.github.glomadrian.domain.Team
import com.github.glomadrian.mvi.ViewModel
import kotlinx.coroutines.flow.flow

class CounterViewModel(
    private val addPointsToTeam: AddPointsToTeam
) : ViewModel<CounterIntent, CounterViewState, CounterAction, CounterResult>() {

    override suspend fun intentToActionMatcher(intent: CounterIntent) =
        when (intent) {
            CounterIntent.InitView -> CounterAction.None
            is CounterIntent.AddOnePointToTeam -> CounterAction.AddPointsToTeam(1, intent.team)
            is CounterIntent.AddThreePointToTeam -> CounterAction.AddPointsToTeam(3, intent.team)
            is CounterIntent.AddFivePointToTeam -> CounterAction.AddPointsToTeam(5, intent.team)
            CounterIntent.ClearCounter -> CounterAction.ClearTeamPoints
        }

    override fun actionExecutor(counterAction: CounterAction) =
        when (counterAction) {
            is CounterAction.AddPointsToTeam -> addPointsToTeam(
                counterAction.points,
                counterAction.team
            )
            CounterAction.ClearTeamPoints -> addPointsToTeam(2, Team.A)
            CounterAction.None -> flow { emit(CounterResult.NoResult) }
        }

    override fun reduceMatcher(
        previousState: CounterViewState,
        result: CounterResult
    ) = when (result) {
        is CounterResult.AddPointsResult.PointsAdded -> reducePointsAdded(previousState, result)
        is CounterResult.AddPointsResult.PointNotAddedError -> reducePointNotAddedError(
            previousState
        )
        CounterResult.NoResult -> previousState
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

    private fun reducePointNotAddedError(previousState: CounterViewState) = previousState

    class Factory(
        private val addPointsToTeam: AddPointsToTeam
    ) : ViewModelProvider.Factory {
        override fun <T : androidx.lifecycle.ViewModel> create(modelClass: Class<T>): T =
            CounterViewModel(addPointsToTeam) as T
    }

    override fun initialState()
        = CounterViewState(false, 0, 0, null)
}