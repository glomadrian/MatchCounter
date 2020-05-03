package com.github.glomadrian.counter

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.github.glomadrian.CounterMemoryDataSource
import com.github.glomadrian.CounterRepository
import com.github.glomadrian.clicks
import com.github.glomadrian.databinding.ActivityMainBinding
import com.github.glomadrian.domain.AddPointsToTeam
import com.github.glomadrian.domain.Team
import com.github.glomadrian.mvi.View
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.merge

class CounterActivity : AppCompatActivity(), View<CounterViewState, CounterIntent> {
    private val feedViewModel: CounterViewModel by lazy {
        ViewModelProvider(
            this,
            CounterViewModel.Factory(
                AddPointsToTeam(
                    CounterRepository(CounterMemoryDataSource)
                )
            )
        ).get(CounterViewModel::class.java)
    }

    private lateinit var binding: ActivityMainBinding

    override fun intents() = merge(
        flowOf(CounterIntent.InitView),
        binding.addMovie.clicks().map { CounterIntent.ClearCounter },
        binding.addOnePointTeamA.clicks().map { CounterIntent.AddOnePointToTeam(Team.A) },
        binding.addThreePointTeamA.clicks().map { CounterIntent.AddThreePointToTeam(Team.A) },
        binding.addFivePointTeamA.clicks().map { CounterIntent.AddFivePointToTeam(Team.A) },
        binding.addOnePointTeamB.clicks().map { CounterIntent.AddOnePointToTeam(Team.B) },
        binding.addThreePointTeamB.clicks().map { CounterIntent.AddThreePointToTeam(Team.B) },
        binding.addFivePointTeamB.clicks().map { CounterIntent.AddFivePointToTeam(Team.B) }
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        feedViewModel.processIntents(intents())
        feedViewModel.state().observe(this, stateObserver())
    }

    private fun stateObserver() = Observer<CounterViewState> {
        binding.teamAPoints.text = it.teamAPoints.toString()
        binding.teamBPoints.text = it.teamBPoints.toString()
    }
}
