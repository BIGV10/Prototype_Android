package com.example.prototype

import android.content.Context
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.VibrationEffect
import android.os.Vibrator
//import com.github.kittinunf.fuel.Fuel
//import com.github.kittinunf.fuel.httpGet
import com.google.zxing.ResultPoint
import com.journeyapps.barcodescanner.BarcodeCallback
import com.journeyapps.barcodescanner.BarcodeResult
import com.journeyapps.barcodescanner.CaptureManager
import kotlinx.android.synthetic.main.activity_add_request_activity.*

class AddRequestActivity : AppCompatActivity() {
    lateinit var captureManager: CaptureManager
    var scanState: Boolean = false
    var torchState: Boolean = false

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
                    }
                    var base_url = "https://bigv-postgres.herokuapp.com/api/equipment/"
                    var url = base_url + barcodeResult
//                    val (request, response, result) = Fuel.get(base_url, listOf("barcode" to barcodeResult)).response()
//                    val q = Fuel.get(base_url, listOf("barcode" to barcodeResult))
//                    text_result.setText(result.toString())
//                    TODO() GET запрос по barcode
                    var x = 0
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
