package com.github.glomadrian.actionviewer.actionViewerList

import android.graphics.Color

class ColorGenerator {

    val primary = listOf(
        Color.parseColor("#ef534f"),
        Color.parseColor("#ce93d8"),
        Color.parseColor("#9fa8da"),
        Color.parseColor("#4fc3f7"),
        Color.parseColor("#4db6ac"),
        Color.parseColor("#ffc107"),
        Color.parseColor("#ff5722")
    )

    val light = listOf(
        Color.parseColor("#ff867c"),
        Color.parseColor("#ffc4ff"),
        Color.parseColor("#d1d9ff"),
        Color.parseColor("#8bf6ff"),
        Color.parseColor("#82e9de"),
        Color.parseColor("#fff350"),
        Color.parseColor("#ff8a50")
    )

    fun getColorsFromString(text: String): Pair<Int, Int> {
        val colorNumber = text.toCharArray().fold(0) { accumulator, char ->
            accumulator + char.toInt()
        } % primary.size
        return (primary[colorNumber] to light[colorNumber])
    }
}