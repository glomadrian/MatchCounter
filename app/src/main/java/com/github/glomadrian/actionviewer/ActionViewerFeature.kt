package com.github.glomadrian.actionviewer

import com.github.glomadrian.architecture.Action
import com.github.glomadrian.architecture.Intent
import com.github.glomadrian.architecture.Result
import com.github.glomadrian.architecture.State
import com.github.glomadrian.architecture.actionwatcher.ActionInfo

sealed class ActionViewerIntent : Intent {
    object InitView : ActionViewerIntent()
}

sealed class ActionViewerAction : Action {
    object ShowActionList : ActionViewerAction()
}

data class ActionViewerState(
    val actions: List<ActionViewModel> = emptyList()
) : State {
    companion object {
        fun initState() = ActionViewerState()
    }
}


sealed class ActionViewerResult : Result {
    object NoResult : ActionViewerResult()
    data class ActionList(val actionInfo: List<ActionInfo>) : ActionViewerResult()
}

data class ActionViewModel(
    val time: Long,
    val name: String,
    val actionData: String,
    val lineColor: Int,
    val circleColor: Int
)