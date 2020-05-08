package com.github.glomadrian.counter

import com.github.glomadrian.architecture.Action
import com.github.glomadrian.architecture.Intent
import com.github.glomadrian.architecture.Result
import com.github.glomadrian.architecture.State
import com.github.glomadrian.counter.model.Team

sealed class CounterIntent : Intent {
    object InitView : CounterIntent()
    data class AddOnePointToTeam(val team: Team) : CounterIntent()
    data class AddThreePointToTeam(val team: Team) : CounterIntent()
    data class AddFivePointToTeam(val team: Team) : CounterIntent()
    object ClearCounter : CounterIntent()
}

sealed class CounterAction : Action {
    object None : CounterAction()
    data class AddPointsToTeam(val points: Int, val team: Team) : CounterAction()
    object ClearTeamPoints : CounterAction()
}

data class CounterViewState(
    val isLoading: Boolean = false,
    val teamAPoints: Int = 0,
    val teamBPoints: Int = 0,
    val error: Throwable?
) : State {

    companion object {
        fun initState() = CounterViewState(false, 0 ,0, null)
    }
}

sealed class CounterResult : Result {
    object NoResult : CounterResult()
    sealed class AddPointsResult : CounterResult() {
        data class PointsAdded(val points: Int, val team: Team) : AddPointsResult()
        object PointNotAddedError : AddPointsResult()
    }
    data class CounterClearedError(val error: Throwable) : CounterResult()
    object CounterCleared : CounterResult()
    object Loading : CounterResult()
}