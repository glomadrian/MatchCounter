package com.github.glomadrian.actionviewer

import androidx.lifecycle.ViewModelProvider
import com.github.glomadrian.architecture.Store
import com.github.glomadrian.architecture.ViewModel

class ActionViewerViewModel(
    store: Store<ActionViewerAction, ActionViewerResult, ActionViewerState>
) :
    ViewModel<ActionViewerIntent, ActionViewerState, ActionViewerAction, ActionViewerResult>(store) {

    override suspend fun handleIntent(intent: ActionViewerIntent) =
        when (intent) {
            ActionViewerIntent.InitView -> ActionViewerAction.ShowActionList
        }

    class Factory(
        private val store: Store<ActionViewerAction, ActionViewerResult, ActionViewerState>
    ) : ViewModelProvider.Factory {
        override fun <T : androidx.lifecycle.ViewModel> create(modelClass: Class<T>): T =
            ActionViewerViewModel(store) as T
    }
}