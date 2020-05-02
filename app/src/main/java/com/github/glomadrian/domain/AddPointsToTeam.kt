package com.github.glomadrian.domain

import com.github.glomadrian.CounterRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map

class AddPointsToTeam(private val counterRepository: CounterRepository) {

    operator fun invoke(points: Int, team: Team): Flow<Result> =
        counterRepository.addPointsToTeam(points, team).map {
            Result.PointsAdded(points, team)
        }.catch {
            Result.PointNotAddedError
        }

    sealed class Result {
        data class PointsAdded(val points: Int, val team: Team) : Result()
        object PointNotAddedError : Result()
    }
}