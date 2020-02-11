package com.example.prototype

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class AddAdapter : RecyclerView.Adapter<CustomViewHolder>() {
    override fun getItemCount(): Int {
        return 6
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        val layoutInflater = LayoutInflater.from(parent?.context)
        val cellForRowId =  layoutInflater.inflate(R.layout.equipment_row, parent, false)
        return CustomViewHolder(cellForRowId)

    }

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {

    }
}

class CustomViewHolder(v: View) : RecyclerView.ViewHolder(v) {

}