package com.github.glomadrian.domain

import com.github.glomadrian.CounterRepository
import com.github.glomadrian.counter.CounterResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map

class AddPointsToTeam(private val counterRepository: CounterRepository) {

    operator fun invoke(points: Int, team: Team): Flow<CounterResult> =
        counterRepository.addPointsToTeam(points, team).map {
            CounterResult.AddPointsResult.PointsAdded(points, team)
        }.catch {
            CounterResult.AddPointsResult.PointNotAddedError
        }

}