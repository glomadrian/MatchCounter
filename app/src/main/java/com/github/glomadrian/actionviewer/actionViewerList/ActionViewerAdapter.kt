package com.github.glomadrian.actionviewer

import androidx.recyclerview.widget.RecyclerView

class ActionViewerAdapter : RecyclerView.Adapter {


    class VehicleViewHolder(val vehicleRenderer: VehicleRenderer) : RecyclerView.ViewHolder(vehicleRenderer.view) {
        fun render(vehicle: ListableVehicleViewModel) {
            vehicleRenderer.render(vehicle)
        }
    }
    class ActionViewmodelViewHolder : RecyclerView.ViewHolder() {

    }
}