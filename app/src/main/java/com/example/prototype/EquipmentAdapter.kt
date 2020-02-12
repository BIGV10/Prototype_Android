package com.example.prototype

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.prototype.model.Equipment
import kotlinx.android.synthetic.main.equipment_row.view.*

class EquipmentAdapter(var equipmentList: ArrayList<Equipment>) : RecyclerView.Adapter<CustomViewHolder>() {
//    val equipmentList = ArrayList<Equipment>()

    override fun getItemCount(): Int {
        return equipmentList.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        val layoutInflater = LayoutInflater.from(parent?.context)
        val cellForRowId =  layoutInflater.inflate(R.layout.equipment_row, parent, false)
        return CustomViewHolder(cellForRowId)

    }

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        val currentEquipment = equipmentList.get(position)
        holder?.view?.textEquipment_Barcode?.text = currentEquipment.barcode
        holder?.view?.textEquipment_ID?.text = currentEquipment.id.toString()
        holder?.view?.textEquipment_Name?.text = currentEquipment.name
        holder?.view?.textEquipment_Comment?.text = currentEquipment.comment
    }
}

class CustomViewHolder(val view: View) : RecyclerView.ViewHolder(view) {

}