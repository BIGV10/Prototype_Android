package com.example.prototype.activity

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.os.VibrationEffect
import android.os.Vibrator
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.prototype.adapter.EquipmentAdapter
import com.example.prototype.R
import com.example.prototype.ServiceBuilder
import com.example.prototype.model.*
import com.example.prototype.service.*
import com.google.zxing.ResultPoint
import com.journeyapps.barcodescanner.BarcodeCallback
import com.journeyapps.barcodescanner.BarcodeResult
import com.journeyapps.barcodescanner.CaptureManager
import kotlinx.android.synthetic.main.activity_add_request_activity.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class AddRequestActivity : AppCompatActivity() {
    lateinit var captureManager: CaptureManager
    var scanState: Boolean = false
    var torchState: Boolean = false
    val baseUrl = "https://bigv-postgres.herokuapp.com/api/"
    val equipmentList = ArrayList<Equipment>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_request_activity)

        recyclerView_EquipmentList.layoutManager = LinearLayoutManager(this)
        recyclerView_EquipmentList.adapter =
            EquipmentAdapter(equipmentList)

        captureManager = CaptureManager(this, barcodeView)
        captureManager.initializeFromIntent(intent, savedInstanceState)

        btn_Scan.setOnClickListener {
            txt_ScanResult.text = "Сканирование..."
            barcodeView.decodeSingle(object : BarcodeCallback {
                override fun barcodeResult(barcodeResult: BarcodeResult?) {
                    barcodeResult?.let {
                        txt_ScanResult.text = it.text
                        val vibrator = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
                        if (Build.VERSION.SDK_INT >= 26) {
                            vibrator.vibrate(
                                VibrationEffect.createOneShot(
                                    200,
                                    VibrationEffect.DEFAULT_AMPLITUDE
                                )
                            )
                        } else {
                            vibrator.vibrate(200)
                        }
                        loadEquipment(it.text)

                    }
                }

                override fun possibleResultPoints(resultPoints: MutableList<ResultPoint>?) {
                }
            })
        }

        btn_Torch.setOnClickListener {
            if (torchState) {
                torchState = false
                btn_Torch.setBackgroundResource(R.drawable.ic_flash_off)
                barcodeView.setTorchOff()
            } else {
                torchState = true
                btn_Torch.setBackgroundResource(R.drawable.ic_flash_on)
                barcodeView.setTorchOn()
            }
        }

        btn_sendRequest.setOnClickListener {
            sendNewRequest()
        }
    }

    fun loadEquipment(barcode: String) {
        val equipmentService =
            ServiceBuilder.buildService(EquipmentService::class.java)
        val requestCall = equipmentService.getEquipmentByBarcode(barcode)
        requestCall.enqueue(object : Callback<Equipment> {
            override fun onResponse(call: Call<Equipment>, response: Response<Equipment>) {
                if (response.isSuccessful) {
                    val body = response.body()
                    if (equipmentList.indexOf(body as Equipment) < 0) {
                        equipmentList.add(body as Equipment)
                    } else {
                        Toast.makeText(
                            this@AddRequestActivity,
                            "Данное оборудование уже было добавлено", Toast.LENGTH_LONG
                        ).show()
                    }

                    var adapter =
                        EquipmentAdapter(
                            equipmentList
                        )
                    recyclerView_EquipmentList.adapter = adapter

                } else { //Status code is not 200's
                    if (response.code() == 500) {
                        Toast.makeText(
                            this@AddRequestActivity,
                            "Оборудование не найдено\n" + response.code(), Toast.LENGTH_LONG
                        ).show()
                    } else {
                        Toast.makeText(
                            this@AddRequestActivity,
                            "Не удалось получить информацию" + response.code(), Toast.LENGTH_LONG
                        ).show()
                    }
                }
            }

            override fun onFailure(call: Call<Equipment>, t: Throwable) {
                Toast.makeText(this@AddRequestActivity, t.message, Toast.LENGTH_LONG)
            }
        })

//        val requestCall = equipmentService.getAllEquipment()
//        requestCall.enqueue(object : Callback<List<Equipment>> {
//            override fun onResponse(call: Call<List<Equipment>>, response: Response<List<Equipment>>) {
//                    val equipmentResponse = response.body()!!
//                    text_result.setText(equipmentResponse.toString())
//            }
//
//            override fun onFailure(call: Call<List<Equipment>>, t: Throwable) {
//                text_result.setText(t.message)
//            }
//        })
    }

    fun sendNewRequest() {
        val requestService =
            ServiceBuilder.buildService(RequestService::class.java)
        var newRequest = Request()
        newRequest.comment = text_RequestComment.text.toString()
        newRequest.status = 0
        val requestCallNewRequest = requestService.postRequest(newRequest)
        requestCallNewRequest.enqueue(object : Callback<Request> {

            override fun onResponse(call: Call<Request>, response: Response<Request>) {
                if (response.isSuccessful) {
                    var createdRequest = (response.body() as Request).id!!
                    addEquipmentToRequest(createdRequest)
                }
            }

            override fun onFailure(call: Call<Request>, t: Throwable) {
                Toast.makeText(this@AddRequestActivity, t.message, Toast.LENGTH_LONG)
            }
        })
    }

    fun addEquipmentToRequest(requestId: Int) {
        val requestService =
            ServiceBuilder.buildService(RequestService::class.java)
        var equipmentCount = equipmentList.size
        var successfulRequests = 0
        var unsuccessfulRequests = ""
        equipmentList.forEach {
            val requestCallAddEquipment = requestService.postEquipmentToRequest(requestId, it.id!!)
            requestCallAddEquipment.enqueue(object : Callback<Equipment> {
                override fun onResponse(call: Call<Equipment>, response: Response<Equipment>) {
                    successfulRequests++
                    if (successfulRequests == equipmentCount) {
                        Toast.makeText( this@AddRequestActivity, "Заявка отправлена успешно", Toast.LENGTH_LONG ).show()
                        finish()
                    }
                }

                override fun onFailure(call: Call<Equipment>, t: Throwable) {
                    unsuccessfulRequests += t.message + "\n"
                    Toast.makeText(this@AddRequestActivity, unsuccessfulRequests, Toast.LENGTH_LONG).show()
                }
            })
        }
    }

    override fun onPause() {
        super.onPause()
        captureManager.onPause()
    }

    override fun onResume() {
        super.onResume()
        captureManager.onResume()
    }

    override fun onDestroy() {
        super.onDestroy()
        captureManager.onDestroy()
    }
}