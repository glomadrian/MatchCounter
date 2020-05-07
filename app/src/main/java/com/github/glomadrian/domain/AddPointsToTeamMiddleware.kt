package com.github.glomadrian.domain

import com.github.glomadrian.CounterRepository
import com.github.glomadrian.architecture.Middleware
import com.github.glomadrian.counter.CounterAction
import com.github.glomadrian.counter.CounterResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.map

class AddPointsToTeamMiddleware(
    private val counterRepository: CounterRepository
) : Middleware<CounterAction, CounterResult> {

    override fun bind(actions: Flow<CounterAction>) = actions
        .filter { it is CounterAction.AddPointsToTeam }
        .map { it as CounterAction.AddPointsToTeam }
        .flatMapConcat { (points, team) ->
            counterRepository.addPointsToTeam(points, team).map {
                CounterResult.AddPointsResult.PointsAdded(points, team)
            }.catch {
                CounterResult.AddPointsResult.PointNotAddedError
            }
        }
}