package com.github.glomadrian.actionviewer

import android.graphics.Color
import com.github.glomadrian.architecture.Reducer

class ActionViewerReducer : Reducer<ActionViewerResult, ActionViewerState> {
    override fun reduce(
        previousState: ActionViewerState,
        result: ActionViewerResult
    ) = when (result) {
        ActionViewerResult.NoResult -> previousState
        is ActionViewerResult.ActionList -> reduceActionList(previousState, result)
    }

    private fun reduceActionList(
        previousState: ActionViewerState,
        result: ActionViewerResult.ActionList
    ) = result.actionInfo.fold(
        mutableListOf<ActionViewModel>()
    ) { actionList, actionInfo ->
        val actionName = actionInfo.action.toString()
        val actionTime = actionInfo.timestamp
        actionList.add(ActionViewModel(actionTime, actionName, Color.RED))
        actionList
    }.run {
        previousState.copy(actions = this)
    }

    override fun reduceUnexpectedError(
        previousState: ActionViewerState,
        error: Throwable
    ): ActionViewerState {
        TODO("Not yet implemented")
    }
}