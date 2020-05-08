package com.github.glomadrian.counter.midleware

import com.github.glomadrian.CounterRepository
import com.github.glomadrian.architecture.Middleware
import com.github.glomadrian.counter.CounterAction
import com.github.glomadrian.counter.CounterResult
import com.github.glomadrian.counter.model.Team
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.map

class AddPointsToTeam(
    private val counterRepository: CounterRepository
) : Middleware<CounterAction, CounterResult> {

    override fun bind(actions: Flow<CounterAction>): Flow<CounterResult.AddPointsResult> = actions
        .filter { it is CounterAction.AddPointsToTeam }
        .map { it as CounterAction.AddPointsToTeam }
        .flatMapConcat {
            addPointsToTeam(it.points, it.team)
        }

    private fun addPointsToTeam(points: Int, team: Team) = counterRepository.addPointsToTeam(
        points, team
    ).map<Unit, CounterResult.AddPointsResult> {
        CounterResult.AddPointsResult.PointsAdded(points, team)
    }.catch {
        emit(CounterResult.AddPointsResult.PointNotAddedError)
    }
}