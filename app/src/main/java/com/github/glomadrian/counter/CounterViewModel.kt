package com.github.glomadrian.counter

import android.util.Log
import com.github.glomadrian.exhaustive
import com.github.glomadrian.mainThread
import com.github.glomadrian.mvi.ViewModel
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.launch

class CounterViewModel : ViewModel<CounterIntent, CounterViewState> {

    private val statesChannel = Channel<CounterViewState>()

    override fun states() = statesChannel.consumeAsFlow()

    override fun processIntents(intents: Flow<CounterIntent>) {
        GlobalScope.launch(mainThread) {
            intents.collect {
                when (it) {
                    CounterIntent.InitView -> logIntent(it)
                    is CounterIntent.AddOnePointToTeam -> logIntent(it)
                    is CounterIntent.AddThreePointToTeam -> logIntent(it)
                    is CounterIntent.AddFivePointToTeam -> logIntent(it)
                    CounterIntent.ClearCounter -> logIntent(it)
                }.exhaustive
            }
        }
    }

    private fun logIntent(intent: CounterIntent) {
        Log.d("Intent", intent.toString())
    }
}