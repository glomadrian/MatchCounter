package com.github.glomadrian.counter

import com.github.glomadrian.domain.Team
import com.github.glomadrian.mvi.Action
import com.github.glomadrian.mvi.Intent
import com.github.glomadrian.mvi.State

sealed class CounterIntent : Intent {
    object InitView : CounterIntent()
    data class AddOnePointToTeam(val team: Team) : CounterIntent()
    data class AddThreePointToTeam(val team: Team) : CounterIntent()
    data class AddFivePointToTeam(val team: Team) : CounterIntent()
    object ClearCounter : CounterIntent()
}

sealed class CounterAction : Action {
    data class AddPointsToTeam(val points: Int, val team: Team) : CounterAction()
    object ClearTeamPoints : CounterAction()
}

data class CounterViewState(
    val isLoading: Boolean,
    val teamAPoints: Int,
    val teamBPoints: Int,
    val error: Throwable?
) : State