package com.github.glomadrian.counter

import androidx.lifecycle.ViewModelProvider
import com.github.glomadrian.architecture.ActionExecutor
import com.github.glomadrian.architecture.Middleware
import com.github.glomadrian.architecture.Reducer
import com.github.glomadrian.architecture.ViewModel

class CounterViewModel(
    reducerHandler: Reducer<CounterResult, CounterViewState>,
    middleware: List<Middleware<CounterAction, CounterResult>>
) : ViewModel<CounterIntent, CounterViewState, CounterAction, CounterResult>(
    reducerHandler, middleware
) {

    override suspend fun handleIntent(intent: CounterIntent) =
        when (intent) {
            CounterIntent.InitView -> CounterAction.None
            is CounterIntent.AddOnePointToTeam -> CounterAction.AddPointsToTeam(1, intent.team)
            is CounterIntent.AddThreePointToTeam -> CounterAction.AddPointsToTeam(3, intent.team)
            is CounterIntent.AddFivePointToTeam -> CounterAction.AddPointsToTeam(5, intent.team)
            CounterIntent.ClearCounter -> CounterAction.ClearTeamPoints
        }

    override fun initialState() = CounterViewState(false, 0, 0, null)

    class Factory(
        private val reducerHandler: Reducer<CounterResult, CounterViewState>,
        private val middleware: List<Middleware<CounterAction, CounterResult>>
    ) : ViewModelProvider.Factory {
        override fun <T : androidx.lifecycle.ViewModel> create(modelClass: Class<T>): T =
            CounterViewModel(reducerHandler, middleware) as T
    }
}