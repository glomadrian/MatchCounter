package com.github.glomadrian.counter

import com.github.glomadrian.background
import com.github.glomadrian.domain.AddPointsToTeam
import com.github.glomadrian.domain.Team
import com.github.glomadrian.exhaustive
import com.github.glomadrian.mvi.ViewModel
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.scan
import kotlinx.coroutines.flow.transform

class CounterViewModel(
    private val addPointsToTeam: AddPointsToTeam
) : ViewModel<CounterIntent, CounterViewState> {

    private val statesChannel = Channel<CounterViewState>()
    private var state = CounterViewState(false, 0, 0, null)

    override fun states() = statesChannel.consumeAsFlow()

    override fun processIntents(intents: Flow<CounterIntent>) {
        intents.transform {
            when (it) {
                CounterIntent.InitView -> {
                }
                is CounterIntent.AddOnePointToTeam -> emit(
                    CounterAction.AddPointsToTeam(
                        1,
                        it.team
                    )
                )
                is CounterIntent.AddThreePointToTeam -> emit(
                    CounterAction.AddPointsToTeam(
                        3,
                        it.team
                    )
                )
                is CounterIntent.AddFivePointToTeam -> emit(
                    CounterAction.AddPointsToTeam(
                        5,
                        it.team
                    )
                )
                CounterIntent.ClearCounter -> emit(CounterAction.ClearTeamPoints)
            }.exhaustive
        }.flatMapConcat {
            when (it) {
                is CounterAction.AddPointsToTeam -> addPointsToTeam(it.points, it.team)
                CounterAction.ClearTeamPoints -> addPointsToTeam(2, Team.A)
            }
        }.scan(state) { acc, value ->
            reduce(acc, value)
        }.distinctUntilChanged()
            .flowOn(background)
            .onEach { statesChannel.send(it) }
            .launchIn(GlobalScope)
    }

    private fun reduce(previousState: CounterViewState, result: CounterResult) =
        when (result) {
            is CounterResult.AddPointsResult.PointsAdded -> reduceAddPointToTeam(
                previousState,
                result
            )
            CounterResult.AddPointsResult.PointNotAddedError -> previousState
        }

    private fun reduceAddPointToTeam(
        previousState: CounterViewState,
        result: CounterResult.AddPointsResult.PointsAdded
    ) =
        when (result.team) {
            is Team.A -> previousState.copy(
                isLoading = false,
                teamAPoints = previousState.teamAPoints + result.points
            )
            is Team.B -> previousState.copy(
                isLoading = false,
                teamBPoints = previousState.teamBPoints + result.points
            )
        }
}