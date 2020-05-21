package com.github.glomadrian.actionviewer.actionViewerList

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.github.glomadrian.actionviewer.ActionViewModel

class ActionViewerAdapter : RecyclerView.Adapter<ActionViewerAdapter.ActionViewHolder>() {

    private val actionLis = mutableListOf<ActionViewModel>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ActionViewHolder(
        ActionViewerRenderer(parent)
    )

    override fun getItemCount() = actionLis.size

    override fun onBindViewHolder(holder: ActionViewHolder, position: Int) {
        holder.render(actionLis[position])
    }

    fun renderActions(actions: List<ActionViewModel>) {
        actionLis.addAll(actions)
        notifyDataSetChanged()
    }

    class ActionViewHolder(val renderer: ActionViewerRenderer) :
        RecyclerView.ViewHolder(renderer.view) {
        fun render(action: ActionViewModel) {
            renderer.render(action)
        }
    }
}