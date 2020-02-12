package com.example.prototype

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.os.VibrationEffect
import android.os.Vibrator
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.prototype.model.Equipment
import com.example.prototype.service.EquipmentService
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

        loadEquipment("101340000123")

        recyclerView_add.layoutManager = LinearLayoutManager(this)
        recyclerView_add.adapter = EquipmentAdapter(equipmentList)

        captureManager = CaptureManager(this, barcodeView)
        captureManager.initializeFromIntent(intent, savedInstanceState)

        btnScan.setOnClickListener {
            txtResult.text = "Сканирование..."
            barcodeView.decodeSingle(object : BarcodeCallback {
                override fun barcodeResult(barcodeResult: BarcodeResult?) {
                    barcodeResult?.let {
                        txtResult.text = it.text
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
        btnTorch.setOnClickListener {
            if (torchState) {
                torchState = false
                btnTorch.setBackgroundResource(R.drawable.ic_flash_off)
                barcodeView.setTorchOff()
            } else {
                torchState = true
                btnTorch.setBackgroundResource(R.drawable.ic_flash_on)
                barcodeView.setTorchOn()
            }
        }
    }

    fun loadEquipment(barcode: String) {
        val equipmentService = ServiceBuilder.buildService(EquipmentService::class.java)
        val requestCall = equipmentService.getEquipmentByBarcode(barcode)
        requestCall.enqueue(object : Callback<Equipment> {
            override fun onResponse(call: Call<Equipment>, response: Response<Equipment>) {
                if (response.isSuccessful) {
                    val body = response.body()
                    equipmentList.add(body as Equipment)

                    var adapter = EquipmentAdapter(equipmentList)
                    recyclerView_add.adapter = adapter

                    text_result.setText(body.toString())

                } else { //Status code is not 200's
                    Toast.makeText(
                        this@AddRequestActivity,
                        "Не удалось получить информацию",
                        Toast.LENGTH_LONG
                    ).show()
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
