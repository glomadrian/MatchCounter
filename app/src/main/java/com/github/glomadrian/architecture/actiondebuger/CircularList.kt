package com.github.glomadrian.architecture.actiondebuger

class CircularList<T>(size: Int) : ArrayList<T>(size) {
    private val circularListSize = size

    override fun add(element: T): Boolean {
        if (circularListSize == size) {
            remove(first())
        }
        return super.add(element)
    }
}