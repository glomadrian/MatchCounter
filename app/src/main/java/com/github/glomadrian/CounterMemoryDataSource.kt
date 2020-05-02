package com.github.glomadrian

import kotlinx.coroutines.flow.flow

object CounterMemoryDataSource {
    var teamAPoints = 0
    var teamBPoints = 0

    fun addPointsToTeamA(points: Int) = flow {
        teamAPoints += points
        emit(Unit)
    }

    fun addPointsToTeamB(points: Int) = flow {
        teamBPoints += points
        emit(Unit)
    }
}