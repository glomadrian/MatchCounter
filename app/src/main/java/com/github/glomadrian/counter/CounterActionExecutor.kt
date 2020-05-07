package com.github.glomadrian.counter

import android.util.Log
import com.github.glomadrian.architecture.ActionExecutor
import com.github.glomadrian.domain.AddPointsToTeam
import com.github.glomadrian.domain.ClearCounter
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onStart

class CounterActionExecutor(
    private val addPointsToTeam: AddPointsToTeam,
    private val clearCounter: ClearCounter
) : ActionExecutor<CounterAction, CounterResult> {

    override operator fun invoke(action: CounterAction): Flow<CounterResult> =
        when (action) {
            is CounterAction.AddPointsToTeam -> addPointsToTeam(
                action.points,
                action.team
            ).catch {
                Log.d("MVI", "Add Point Catch")
            }
            CounterAction.ClearTeamPoints -> clearCounterAction()
            CounterAction.None -> flow { emit(CounterResult.NoResult) }
        }

    private fun clearCounterAction() = clearCounter()
        .catch {
            emit(CounterResult.CounterClearedError(it))
        }.onStart {
            emit(CounterResult.Loading)
        }
}