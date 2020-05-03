package com.github.glomadrian

import kotlinx.coroutines.flow.flow

object CounterMemoryDataSource {
    var teamAPoints = 0
    var teamBPoints = 0

    fun addPointsToTeamA(points: Int) = flow {
        teamAPoints += points
        if (teamAPoints == 10) {
            throw IllegalAccessError()
        }
        emit(Unit)
    }

    fun addPointsToTeamB(points: Int) = flow {
        teamBPoints += points
        emit(Unit)
    }

    fun clear() = flow {
        kotlinx.coroutines.delay(3000)
        teamAPoints = 0
        teamBPoints = 0
        emit(Unit)
    }
}