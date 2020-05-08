package com.github.glomadrian.counter

import com.github.glomadrian.CounterMemoryDataSource
import com.github.glomadrian.CounterRepository
import com.github.glomadrian.ViewModelBehaviourTest
import com.github.glomadrian.domain.AddPointsToTeam
import com.github.glomadrian.domain.Team
import org.junit.Test

class CounterViewModelBehaviourTest :
    ViewModelBehaviourTest<CounterIntent, CounterViewState, CounterAction, CounterResult>() {

    //Replace with injector
    val repository = CounterRepository(CounterMemoryDataSource)

    override val viewModel = CounterViewModel.Factory(
        CounterReducer(),
        listOf(AddPointsToTeam(repository))
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

    @Test
    fun `should render same state when the points are incremented to 10 but is dropped because is repeated`() {
        whenViewEmitIntents {
            emit(CounterIntent.AddFivePointToTeam(Team.A))
            emit(CounterIntent.AddFivePointToTeam(Team.A))
        }

        assertViewStatesReceived(
            initialState,
            initialState.copy(teamAPoints = 5)
        )
    }
}