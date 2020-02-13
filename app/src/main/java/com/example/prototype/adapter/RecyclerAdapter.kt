package com.example.prototype.adapter

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.prototype.R
import com.example.prototype.activity.AddRequestActivity
import com.example.prototype.model.Equipment
import com.example.prototype.model.Request
import kotlinx.android.synthetic.main.equipment_row.view.*
import java.lang.StringBuilder

class EquipmentAdapter(var equipmentList: ArrayList<Equipment>) :
    RecyclerView.Adapter<CustomViewHolder>() {
//    val equipmentList = ArrayList<Equipment>()

    override fun getItemCount(): Int {
        return equipmentList.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        val layoutInflater = LayoutInflater.from(parent?.context)
        val cellForRowId = layoutInflater.inflate(R.layout.equipment_row, parent, false)
        return CustomViewHolder(cellForRowId)

    }

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        val currentEquipment = equipmentList.get(position)
        holder?.view?.textRequest_Comment?.text = currentEquipment.barcode
        holder?.view?.textEquipment_ID?.text = currentEquipment.id.toString()
        holder?.view?.textRequest_Status?.text = currentEquipment.name
        holder?.view?.textRequest_Equipment?.text = currentEquipment.comment
    }
}

class RequestAdapter(var requestList: ArrayList<Request>) :
    RecyclerView.Adapter<CustomViewHolder>() {
//    val equipmentList = ArrayList<Equipment>()

    override fun getItemCount(): Int {
        return requestList.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        val layoutInflater = LayoutInflater.from(parent?.context)
        val cellForRowId = layoutInflater.inflate(R.layout.request_row, parent, false)
        return CustomViewHolder(cellForRowId)

    }

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        val currentRequest = requestList.get(position)
        holder?.view?.textRequest_Comment?.text = currentRequest.comment
        val statusString: String
        when (currentRequest.status) {
            0 -> statusString = "Новая заявка"
            1 -> statusString = "Заявка принята"
            2 -> statusString = "Заявка выполнена"
            else -> statusString = "Неизвестный вид статуса"
        }
        holder?.view?.textRequest_Status?.text = statusString
        var requestEquipment = StringBuilder()
        currentRequest.equipment?.forEach {
            requestEquipment.append(it.barcode + " " + it.name + " " + it.comment + "\n")
        }
        if (requestEquipment.isEmpty())
            requestEquipment.append("За заявкой нет закрепленного оборудования")
        else
            requestEquipment.setLength(requestEquipment.length - 1)
        holder?.view?.textRequest_Equipment?.text = requestEquipment

        holder?.view.setOnClickListener{

        }
    }
}

class CustomViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
    init {
        view.setOnClickListener{
            val intent = Intent(view.context, AddRequestActivity::class.java)
        }
    }
}