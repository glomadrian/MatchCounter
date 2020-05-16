package com.github.glomadrian.actionviewer

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.github.glomadrian.actionviewer.midleware.GetActionsStoredInWatcher
import com.github.glomadrian.architecture.Store
import com.github.glomadrian.architecture.View
import com.github.glomadrian.databinding.ActionViewerBinding
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.merge

class ActionViewerFragment : Fragment(), View<ActionViewerState, ActionViewerIntent> {

    private val actionViewerViewModel: ActionViewerViewModel by lazy {
        val store = Store(
            ActionViewerReducer(),
            listOf(GetActionsStoredInWatcher()),
            ActionViewerState.initState()
        )
        ViewModelProvider(
            this,
            ActionViewerViewModel.Factory(
                store
            )
        ).get(ActionViewerViewModel::class.java)
    }

    private lateinit var binding: ActionViewerBinding

    override fun intents() = merge(
        flowOf(ActionViewerIntent.InitView)
    )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): android.view.View {
        binding = ActionViewerBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: android.view.View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        actionViewerViewModel.processIntents(intents())
        actionViewerViewModel.state().observe(this, stateObserver())
    }

    private fun stateObserver() = Observer<ActionViewerState> {
        if (it.actions.isNotEmpty()) {
            binding.text.text = it.actions.last().name
        }
    }

    companion object {
        fun newInstance() = ActionViewerFragment()
    }
}
