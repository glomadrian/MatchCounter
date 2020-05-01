package com.github.glomadrian.feed

import android.util.Log
import com.github.glomadrian.mainThread
import com.github.glomadrian.mvi.ViewModel
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.launch

class FeedViewModel : ViewModel<FeedIntent, FeedViewState> {

    private val statesChannel = Channel<FeedViewState>()

    override fun states() = statesChannel.consumeAsFlow()

    override fun processIntents(intents: Flow<FeedIntent>) {
        GlobalScope.launch(mainThread) {
            intents.collect {
                when (it) {
                    FeedIntent.InitView -> logIntent(it)
                    FeedIntent.AddMovieActionIntent -> logIntent(it)
                    FeedIntent.RefreshIntent -> logIntent(it)
                }
            }
        }
    }

    private fun logIntent(intent: FeedIntent) {
        Log.d("Intent", intent.toString())
    }
}