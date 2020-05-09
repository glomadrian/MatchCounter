package com.github.glomadrian.actiondecorator

import com.github.glomadrian.architecture.Action
import com.github.glomadrian.architecture.ActionDecorator
import com.github.glomadrian.architecture.State

class ActionPassthrough : ActionDecorator() {
    override fun decorate(action: Action, state: State) = action
}