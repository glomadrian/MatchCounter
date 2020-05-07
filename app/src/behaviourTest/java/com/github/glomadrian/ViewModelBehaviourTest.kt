package com.github.glomadrian

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.github.glomadrian.architecture.Action
import com.github.glomadrian.architecture.Intent
import com.github.glomadrian.architecture.Result
import com.github.glomadrian.architecture.State
import com.github.glomadrian.architecture.ViewModel
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.setMain
import org.junit.Before
import org.junit.Rule

abstract class ViewModelBehaviourTest<I : Intent, S : State, A : Action, R : Result> {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    abstract val viewModel: ViewModel<I, S, A, R>
    var viewModelReceivedStates = mutableListOf<S>()

    init {
        mainThread = TestCoroutineDispatcher()
        background = TestCoroutineDispatcher()
        Dispatchers.setMain(TestCoroutineDispatcher())
    }

    @Before
    fun before() {
        viewModel.state().observeForever { state ->
            viewModelReceivedStates.add(state)
        }
    }

    fun getReceivedStates(): List<S> = viewModelReceivedStates

    fun whenViewEmitIntents(block: suspend FlowCollector<I>.() -> Unit) {
        viewModel.processIntents(flow {
            block(this)
        })
    }

    fun assertViewStatesReceived(vararg states: S) {
        assertEquals(states.asList(), getReceivedStates())
    }
}