package com.github.glomadrian.counter

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import com.github.glomadrian.R
import com.github.glomadrian.domain.Team
import com.github.glomadrian.mainThread
import com.github.glomadrian.mvi.View
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.launch

class CounterActivity : AppCompatActivity(), View<CounterViewState, CounterIntent> {

    private val resetCounterButton by lazy {
        findViewById<AppCompatButton>(R.id.addMovie)
    }
    private val feedViewModel by lazy {
        CounterViewModel()
    }
    private val addOnePointTeamA by lazy {
        findViewById<AppCompatButton>(R.id.addOnePointTeamA)
    }
    private val addThreePointTeamA by lazy {
        findViewById<AppCompatButton>(R.id.addThreePointTeamA)
    }
    private val addFivePointTeamA by lazy {
        findViewById<AppCompatButton>(R.id.addFivePointTeamA)
    }
    private val addOnePointTeamB by lazy {
        findViewById<AppCompatButton>(R.id.addOnePointTeamB)
    }
    private val addThreePointTeamB by lazy {
        findViewById<AppCompatButton>(R.id.addThreePointTeamB)
    }
    private val addFivePointTeamB by lazy {
        findViewById<AppCompatButton>(R.id.addFivePointTeamB)
    }
    private val intentChannel = Channel<CounterIntent>()

    /**
     *  Channel that emits all intent as a flow
     *  this could be abstract for every view
     */
    override fun intents() = intentChannel.consumeAsFlow()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        bind()
    }

    private fun bind() {
        feedViewModel.processIntents(intents())
        onViewReady()
        listeners()
    }

    private fun onViewReady() {
        sendIntent(CounterIntent.InitView)
    }

    private fun listeners() {
        resetCounterButton.setOnClickListener {
            sendIntent(CounterIntent.ClearCounter)
        }
        addOnePointTeamA.setOnClickListener {
            sendIntent(CounterIntent.AddOnePointToTeam(Team.A))
        }
        addThreePointTeamA.setOnClickListener {
            sendIntent(CounterIntent.AddThreePointToTeam(Team.A))
        }
        addFivePointTeamA.setOnClickListener {
            sendIntent(CounterIntent.AddFivePointToTeam(Team.A))
        }
        addOnePointTeamB.setOnClickListener {
            sendIntent(CounterIntent.AddOnePointToTeam(Team.B))
        }
        addThreePointTeamB.setOnClickListener {
            sendIntent(CounterIntent.AddThreePointToTeam(Team.B))
        }
        addFivePointTeamB.setOnClickListener {
            sendIntent(CounterIntent.AddFivePointToTeam(Team.B))
        }
    }


    override fun render(state: CounterViewState) {
        TODO("Not yet implemented")
    }

    /**
     * This could be abstract
     */
    private fun sendIntent(intent: CounterIntent) {
        GlobalScope.launch(mainThread) {
            intentChannel.send(intent)
        }
    }
}
