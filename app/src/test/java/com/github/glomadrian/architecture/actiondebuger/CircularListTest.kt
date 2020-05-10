package com.github.glomadrian.architecture.actiondebuger

import junit.framework.Assert.assertEquals
import junit.framework.TestCase.assertTrue
import org.junit.Test

class CircularListTest {

    @Test
    fun `should override the first element when a second element is added`() {
        val list = CircularList<Int>(1)

        list.add(1)
        list.add(2)

        assertTrue(list.size == 1)
        assertEquals(2, list[0])
    }

    @Test
    fun `should remove first and add the last one and the end`() {
        val expected = listOf(2,3,4,5)
        val circularList = CircularList<Int>(4)

        circularList.add(1)
        circularList.add(2)
        circularList.add(3)
        circularList.add(4)
        circularList.add(5)

        assertEquals(expected, circularList)

    }
}