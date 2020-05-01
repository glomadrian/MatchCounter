package com.github.glomadrian.feed

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.github.glomadrian.R
import com.github.glomadrian.mainThread
import com.github.glomadrian.mvi.View
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.launch

class FeedActivity : AppCompatActivity(), View<FeedViewState, FeedIntent> {

    private val addMovieButton by lazy {
        findViewById<AppCompatButton>(R.id.addMovie)
    }
    private val swipeToRefresh by lazy {
        findViewById<SwipeRefreshLayout>(R.id.swiperefresh)
    }
    private val feedViewModel by lazy {
        FeedViewModel()
    }
    private val intentChannel = Channel<FeedIntent>()

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
        sendIntent(FeedIntent.InitView)
    }

    private fun listeners() {
        addMovieButton.setOnClickListener {
            sendIntent(FeedIntent.AddMovieActionIntent)
        }
        swipeToRefresh.setOnRefreshListener {
            sendIntent(FeedIntent.RefreshIntent)
        }
    }


    override fun render(state: FeedViewState) {
        TODO("Not yet implemented")
    }

    //This could be abstract
    private fun sendIntent(intent: FeedIntent) {
        GlobalScope.launch(mainThread) {
            intentChannel.send(intent)
        }
    }
}
