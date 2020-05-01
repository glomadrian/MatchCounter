package com.github.glomadrian.counter

import com.github.glomadrian.domain.Team
import com.github.glomadrian.mvi.Intent

sealed class CounterIntent : Intent{
    object InitView : CounterIntent()
    data class AddOnePointToTeam(val team: Team) : CounterIntent()
    data class AddThreePointToTeam(val team: Team) : CounterIntent()
    data class AddFivePointToTeam(val team: Team) : CounterIntent()
    object ClearCounter: CounterIntent()
}