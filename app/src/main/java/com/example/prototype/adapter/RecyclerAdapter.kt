package com.example.prototype.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.prototype.R
import com.example.prototype.activity.AddRequestActivity
import com.example.prototype.model.Equipment
import com.example.prototype.model.Request
import kotlinx.android.synthetic.main.row_equipment.view.*
import kotlinx.android.synthetic.main.row_request.view.*
import java.text.SimpleDateFormat


class EquipmentAdapter(var equipmentList: ArrayList<Equipment>) :
    RecyclerView.Adapter<CustomViewHolder>() {
//    val equipmentList = ArrayList<Equipment>()

    override fun getItemCount(): Int {
        return equipmentList.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        val layoutInflater = LayoutInflater.from(parent?.context)
        val cellForRowId = layoutInflater.inflate(R.layout.row_equipment, parent, false)
        return CustomViewHolder(cellForRowId)

    }

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        val currentEquipment = equipmentList.get(position)
        holder?.view?.text_equipment_barcode?.text = currentEquipment.barcode
        holder?.view?.text_equipment_id?.text = currentEquipment.id.toString()
        holder?.view?.text_equipment_name?.text = currentEquipment.name
        holder?.view?.text_equipment_comment?.text = currentEquipment.comment
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
        val cellForRowId = layoutInflater.inflate(R.layout.row_request, parent, false)
        return CustomViewHolder(cellForRowId)

    }

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        val currentRequest = requestList.get(position)
        holder?.view?.text_request_comment?.text = currentRequest.comment
        val statusString: String
        when (currentRequest.status) {
            0 -> statusString = "Новая заявка"
            1 -> statusString = "Заявка принята"
            2 -> statusString = "Заявка выполнена"
            else -> statusString = "Неизвестный вид статуса"
        }
        holder?.view?.text_request_status?.text = statusString
        holder?.view?.text_request_issuedby?.text = currentRequest.issuedBy
        val parser = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.S")
        var formatter = SimpleDateFormat("dd.MM.yyyy HH:mm:ss")
        var dateTime = formatter.format(parser.parse(currentRequest.dateIssued))
        holder?.view?.text_request_time_creation?.text = dateTime
        var requestEquipment = StringBuilder()
        currentRequest.equipment?.forEach {
            requestEquipment.append(it.barcode + " " + it.name + " " + it.comment + "\n")
        }
        if (requestEquipment.isEmpty())
            requestEquipment.append("За заявкой нет закрепленного оборудования")
        else
            requestEquipment.setLength(requestEquipment.length - 1)
        holder?.view?.text_request_equipment?.text = requestEquipment

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