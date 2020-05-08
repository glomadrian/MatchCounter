package com.github.glomadrian

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map

inline fun <reified T> Flow<*>.ofType() = this.map {
        it as T
}.catch {  }