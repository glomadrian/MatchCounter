package com.github.glomadrian

import com.github.glomadrian.domain.Team

class CounterRepository(
    private val memoryDataSource: CounterMemoryDataSource
) {

    fun addPointsToTeam(points: Int, team: Team) =
        when (team) {
            Team.A -> memoryDataSource.addPointsToTeamA(points)
            Team.B -> memoryDataSource.addPointsToTeamB(points)
        }

    fun clearCounter() = memoryDataSource.clear()
}