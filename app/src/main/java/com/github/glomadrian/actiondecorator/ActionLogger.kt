package com.github.glomadrian.actiondecorator

import android.util.Log
import com.github.glomadrian.architecture.Action
import com.github.glomadrian.architecture.ActionDecorator
import com.github.glomadrian.architecture.State

class ActionLogger : ActionDecorator() {

    override fun decorate(action: Action, state: State) = action.apply {
        Log.d("Action Logger", "Action -> $action \n State: $state")
    }
}