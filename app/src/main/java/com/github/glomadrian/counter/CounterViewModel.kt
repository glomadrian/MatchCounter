package com.github.glomadrian.counter

import android.util.Log
import androidx.lifecycle.ViewModelProvider
import com.github.glomadrian.domain.AddPointsToTeam
import com.github.glomadrian.domain.ClearCounter
import com.github.glomadrian.domain.Team
import com.github.glomadrian.architecture.ViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.retry
import kotlinx.coroutines.runBlocking

class CounterViewModel(
    private val addPointsToTeam: AddPointsToTeam,
    private val clearCounter: ClearCounter
) : ViewModel<CounterIntent, CounterViewState, CounterAction, CounterResult>() {

    init {
        runBlocking {
            flow {
                throw IllegalArgumentException()
                emit("asd")
            }.catch {
                Log.d("MVI","Crash")
                emit("crash")
            }.collect {
                Log.d("MVI",it)
            }
            // clearCounter()
            //     .catch {
            //         Log.d("MVI","Crash")
            //         emit(CounterResult.Loading)
            //     }
            //     .collect {
            //
            //     }
        }

    }

    override suspend fun intentToActionMatcher(intent: CounterIntent) =
        when (intent) {
            CounterIntent.InitView -> CounterAction.None
            is CounterIntent.AddOnePointToTeam -> CounterAction.AddPointsToTeam(1, intent.team)
            is CounterIntent.AddThreePointToTeam -> CounterAction.AddPointsToTeam(3, intent.team)
            is CounterIntent.AddFivePointToTeam -> CounterAction.AddPointsToTeam(5, intent.team)
            CounterIntent.ClearCounter -> CounterAction.ClearTeamPoints
        }

    override fun actionExecutor(counterAction: CounterAction) =
        when (counterAction) {
            is CounterAction.AddPointsToTeam -> addPointsToTeam(
                counterAction.points,
                counterAction.team
            )
            CounterAction.ClearTeamPoints -> clearCounterAction()
            CounterAction.None -> flow { emit(CounterResult.NoResult) }
        }

    private fun clearCounterAction() = clearCounter()
        .catch {
            Log.d("MVI", "Clear counter error")
        }
        .retry(3) {
            Log.d("MVI", "Retry clear counter")
            it is IllegalArgumentException
        }
        .onStart {
            emit(CounterResult.Loading)
        }


    override fun reduceMatcher(
        previousState: CounterViewState,
        result: CounterResult
    ) = when (result) {
        is CounterResult.AddPointsResult.PointsAdded -> reducePointsAdded(previousState, result)
        is CounterResult.AddPointsResult.PointNotAddedError -> pointNotAddedErrorReducer(
            previousState
        )
        CounterResult.NoResult -> previousState
        CounterResult.CounterCleared -> initialState()
        CounterResult.Loading ->  loadingReducer(previousState)
    }

    private fun reducePointsAdded(
        previousState: CounterViewState,
        result: CounterResult.AddPointsResult.PointsAdded
    ) = when (result.team) {
        is Team.A -> previousState.copy(
            isLoading = false,
            teamAPoints = previousState.teamAPoints + result.points
        )
        is Team.B -> previousState.copy(
            isLoading = false,
            teamBPoints = previousState.teamBPoints + result.points
        )
    }

    private fun pointNotAddedErrorReducer(previousState: CounterViewState) = previousState

    private fun loadingReducer(previousState: CounterViewState) = previousState.copy(isLoading =  true)

    class Factory(
        private val addPointsToTeam: AddPointsToTeam,
        private val clearCounter: ClearCounter
    ) : ViewModelProvider.Factory {
        override fun <T : androidx.lifecycle.ViewModel> create(modelClass: Class<T>): T =
            CounterViewModel(addPointsToTeam, clearCounter) as T
    }

    override fun initialState()
        = CounterViewState(false, 0, 0, null)

    override fun errorReducer(previousState: CounterViewState, error: Throwable) =
        previousState.copy(error = error, isLoading = false)
}