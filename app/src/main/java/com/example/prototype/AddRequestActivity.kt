package com.example.prototype

import android.R.attr.password
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.os.VibrationEffect
import android.os.Vibrator
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.GsonBuilder
import com.google.zxing.ResultPoint
import com.journeyapps.barcodescanner.BarcodeCallback
import com.journeyapps.barcodescanner.BarcodeResult
import com.journeyapps.barcodescanner.CaptureManager
import kotlinx.android.synthetic.main.activity_add_request_activity.*
import okhttp3.FormBody
import okhttp3.HttpUrl
import okhttp3.OkHttpClient
import okhttp3.Request
import java.io.IOException


class AddRequestActivity : AppCompatActivity() {
    lateinit var captureManager: CaptureManager
    var scanState: Boolean = false
    var torchState: Boolean = false
    var baseUrl = "https://bigv-postgres.herokuapp.com/api/"

//    fun returnResult(barcode: String): Result<FuelJson, FuelError> {
//        val (request, response, result) = Fuel.get(baseUrl +"equipment", listOf("barcode" to barcode.toString())).responseJson()
//        return result
//    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_request_activity)

        captureManager = CaptureManager(this, barcodeView)
        captureManager.initializeFromIntent(intent, savedInstanceState)

        btnScan.setOnClickListener {
            txtResult.text = "Сканирование..."
            barcodeView.decodeSingle(object: BarcodeCallback {
                override fun barcodeResult(barcodeResult: BarcodeResult?) {
                    barcodeResult?.let {
                        txtResult.text = it.text

                        val vibrator = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
                        if (Build.VERSION.SDK_INT >= 26) {
                            vibrator.vibrate(VibrationEffect.createOneShot(200, VibrationEffect.DEFAULT_AMPLITUDE))
                        } else {
                            vibrator.vibrate(200)
                        }

//                        val (request, response, result) = Fuel.get(baseUrl +"equipment", listOf("barcode" to barcodeResult.toString())).responseJson()

//                        val retrofit = Retrofit.Builder()
//                            .baseUrl(baseUrl)
//                            .addConverterFactory(GsonConverterFactory.create())
//                            .build()
//                        val service = retrofit.create(ApiService::class.java)
//                        val call = service.getEquipmentbyBarcode(barcodeResult.toString())
//                        call.enqueue(object : Callback<Equipment> {
//                            override fun onResponse(
//                                call: Call<Equipment>,
//                                response: Response<Equipment>
//                            ) {
//                                if (response.code() == 200) {
//                                    val equipmentResponse = response.body()!!
//                                    text_result.setText(equipmentResponse.toString())
//                                }
//                            }
//
//                            override fun onFailure(call: Call<Equipment>, t: Throwable) {
//                                text_result.setText(t.message)
//                            }
//                        })
                    }

                    var url = baseUrl + barcodeResult
//                    val (request, response, result) = Fuel.get(base_url, listOf("barcode" to barcodeResult)).response()
//                    val q = Fuel.get(base_url, listOf("barcode" to barcodeResult))
//                    text_result.setText(result.toString())
//                    TODO() GET запрос по barcode

                    fetchJson(baseUrl + "equipment/", "?barcode=" + barcodeResult.toString())

                }
                override fun possibleResultPoints(resultPoints: MutableList<ResultPoint>?) {
                }
            })
        }





        btnTorch.setOnClickListener {
            if(torchState){
                torchState = false
                btnTorch.setBackgroundResource(R.drawable.ic_flashlight_off)
                barcodeView.setTorchOff()
            } else {
                torchState = true
                btnTorch.setBackgroundResource(R.drawable.ic_flashlight_on)
                barcodeView.setTorchOn()
            }
        }
    }

    fun fetchJson(url: String, parameters: String) {
        val request = Request.Builder().url(url + parameters).build()
        val client = OkHttpClient()
        client.newCall(request).enqueue(object: okhttp3.Callback {
            override fun onResponse(call: okhttp3.Call, response: okhttp3.Response) {
                val body = response?.body?.string()
                println(body)

                val gson = GsonBuilder().create()

                val item = gson.fromJson(body, Equipment::class.java)
                text_result.setText(item.toString())
            }
            override fun onFailure(call: okhttp3.Call, e: IOException) {
            }
        })


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
