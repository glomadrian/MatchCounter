package com.github.glomadrian.actionviewer.midleware

import com.github.glomadrian.actionviewer.ActionViewerAction
import com.github.glomadrian.actionviewer.ActionViewerResult
import com.github.glomadrian.architecture.Middleware
import com.github.glomadrian.architecture.actionwatcher.ActionDebugger
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.flow.map

class GetActionsStoredInWatcher : Middleware<ActionViewerAction, ActionViewerResult> {

    override fun bind(actions: Flow<ActionViewerAction>) = actions
        .filterIsInstance<ActionViewerAction.ShowActionList>()
        .map {
            ActionViewerResult.ActionList(ActionDebugger.getActions())
        }.catch {
            it
        }
}