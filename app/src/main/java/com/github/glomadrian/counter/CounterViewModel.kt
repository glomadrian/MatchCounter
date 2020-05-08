package com.github.glomadrian.counter

import androidx.lifecycle.ViewModelProvider
import com.github.glomadrian.architecture.Store
import com.github.glomadrian.architecture.ViewModel

class CounterViewModel(
    store: Store<CounterAction, CounterResult, CounterViewState>
) : ViewModel<CounterIntent, CounterViewState, CounterAction, CounterResult>(
    store
) {

    override suspend fun handleIntent(intent: CounterIntent) =
        when (intent) {
            CounterIntent.InitView -> CounterAction.None
            is CounterIntent.AddOnePointToTeam -> CounterAction.AddPointsToTeam(1, intent.team)
            is CounterIntent.AddThreePointToTeam -> CounterAction.AddPointsToTeam(3, intent.team)
            is CounterIntent.AddFivePointToTeam -> CounterAction.AddPointsToTeam(5, intent.team)
            CounterIntent.ClearCounter -> CounterAction.ClearTeamPoints
        }

    class Factory(
        private val store: Store<CounterAction, CounterResult, CounterViewState>
    ) : ViewModelProvider.Factory {
        override fun <T : androidx.lifecycle.ViewModel> create(modelClass: Class<T>): T =
            CounterViewModel(store) as T
    }
}