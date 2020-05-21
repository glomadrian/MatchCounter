package com.github.glomadrian.actionviewer

import com.github.glomadrian.actionviewer.actionViewerList.ColorGenerator
import com.github.glomadrian.architecture.Reducer

class ActionViewerReducer : Reducer<ActionViewerResult, ActionViewerState> {
    val colorGenerator by lazy { ColorGenerator() }

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
        val parts = actionInfo.action.javaClass.canonicalName!!.split(".")
        val featureAndAction = parts.subList(parts.size - 2, parts.size)
        val actionName = featureAndAction[1]
        val color = colorGenerator.getColorsFromString(featureAndAction[0])
        val actionTime = actionInfo.timestamp
        val actionData = actionInfo.action.toString().replace(featureAndAction[1], "")
            .replace("(", "")
            .replace(")", "")
            .replace(actionInfo.action.javaClass.`package`!!.name + ".", "")
        actionList.add(
            ActionViewModel(
                actionTime,
                actionName,
                actionData,
                color.second,
                color.first
            )
        )
        actionList
    }.run {
        previousState.copy(actions = this)
    }

    override fun reduceUnexpectedError(
        previousState: ActionViewerState,
        error: Throwable
    ) = previousState
}