package com.github.glomadrian.actionviewer.actionViewerList

import android.content.res.ColorStateList
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.github.glomadrian.actionviewer.ActionViewModel
import com.github.glomadrian.databinding.ActionViewerRowBinding

class ActionViewerRenderer(parent: ViewGroup) {
    lateinit var view: View
    private lateinit var binding: ActionViewerRowBinding

    init {
        initializeView(parent)
    }

    private fun initializeView(parent: ViewGroup) {
        val layoutInflater = LayoutInflater.from(parent.context)
        view = provideView(parent, layoutInflater)
    }

    fun provideView(viewGroup: ViewGroup, layoutInflater: LayoutInflater): View {
        binding = ActionViewerRowBinding.inflate(layoutInflater, viewGroup, false)
        return binding.root
    }


    fun render(actionViewModel: ActionViewModel) {
        binding.actionName.text = actionViewModel.name
        binding.line.backgroundTintList = ColorStateList.valueOf(actionViewModel.lineColor)
        binding.circle.backgroundTintList = ColorStateList.valueOf(actionViewModel.circleColor)
        binding.time.text = actionViewModel.time.toString()
        binding.actionData.text = actionViewModel.actionData
    }
}