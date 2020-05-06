package com.github.glomadrian

import kotlinx.coroutines.Dispatchers
import kotlin.coroutines.CoroutineContext

var background: CoroutineContext = Dispatchers.Default
var mainThread: CoroutineContext = Dispatchers.Main