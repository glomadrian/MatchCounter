package com.github.glomadrian.counter

import com.github.glomadrian.CounterMemoryDataSource
import com.github.glomadrian.CounterRepository
import com.github.glomadrian.ViewModelBehaviourTest
import com.github.glomadrian.architecture.Store
import com.github.glomadrian.counter.midleware.AddPointsToTeam
import com.github.glomadrian.counter.midleware.ClearCounter
import com.github.glomadrian.counter.model.Team
import org.junit.Test

class CounterViewModelBehaviourTest :
    ViewModelBehaviourTest<CounterIntent, CounterViewState, CounterAction, CounterResult>() {

    //Replace with injector
    val repository = CounterRepository(CounterMemoryDataSource)
    val middlewares = listOf(
        AddPointsToTeam(repository),
        ClearCounter(repository)
    )
    val store = Store(CounterReducer(), middlewares, CounterViewState.initState())

    override val viewModel = CounterViewModel.Factory(
        store
    ).create(CounterViewModel::class.java)
    //Replace with injector

    private val initialState = CounterViewState(
        false,
        0,
        0,
        null
    )

    @Test
    fun `should increase by one the counter when the user increase by one the counter`() {
        whenViewEmitIntents {
            emit(CounterIntent.AddOnePointToTeam(Team.A))
        }

        assertViewStatesReceived(
            initialState,
            initialState.copy(teamAPoints = 1)
        )
    }
}