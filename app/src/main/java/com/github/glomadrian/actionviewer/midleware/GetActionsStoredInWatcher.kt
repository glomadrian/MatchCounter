package com.github.glomadrian.actionviewer.midleware

import com.github.glomadrian.actionviewer.ActionViewerAction
import com.github.glomadrian.actionviewer.ActionViewerResult
import com.github.glomadrian.architecture.Middleware
import kotlinx.coroutines.flow.Flow

class GetActionsStoredInWatcher : Middleware<ActionViewerAction, ActionViewerResult> {

    override fun bind(actions: Flow<ActionViewerAction>): Flow<ActionViewerResult> {
        TODO("Not yet implemented")
    }
}