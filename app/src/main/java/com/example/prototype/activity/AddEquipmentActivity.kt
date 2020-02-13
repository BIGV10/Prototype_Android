package com.example.prototype.activity

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.os.VibrationEffect
import android.os.Vibrator
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.prototype.R
import com.example.prototype.ServiceBuilder
import com.example.prototype.model.Equipment
import com.example.prototype.service.EquipmentService
import com.google.zxing.ResultPoint
import com.journeyapps.barcodescanner.BarcodeCallback
import com.journeyapps.barcodescanner.BarcodeResult
import com.journeyapps.barcodescanner.CaptureManager
import kotlinx.android.synthetic.main.activity_add_equipment_activity.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AddEquipmentActivity : AppCompatActivity() {
    lateinit var captureManager: CaptureManager
    var scanState: Boolean = false
    var torchState: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_equipment_activity)

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
                        text_Barcode_Type.setText(it.barcodeFormat.toString())
                        text_EquipmentBarcode.setText(it.result.toString())

                    }
                }

                override fun possibleResultPoints(resultPoints: MutableList<ResultPoint>?) {
                }
            })
        }

        btn_sendEquipment.setOnClickListener {
            sendNewEquipment()
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
    }

    private fun sendNewEquipment() {
        val equipmentService = ServiceBuilder.buildService(EquipmentService::class.java)
        var newEquipment = Equipment()
        newEquipment.barcode = text_EquipmentBarcode.text.toString()
        newEquipment.name = text_EquipmentName.text.toString()
        newEquipment.comment = text_EquipmentComment.text.toString()
        val requestCallNewRequest = equipmentService.postEquipment(newEquipment)
        requestCallNewRequest.enqueue(object : Callback<Equipment> {
            override fun onResponse(call: Call<Equipment>, response: Response<Equipment>) {
                if (response.isSuccessful) {
                    var createdEquipment = (response.body() as Equipment).id!!
                    Toast.makeText( this@AddEquipmentActivity, "Оборудование успешно добавлено\n ID: " + createdEquipment, Toast.LENGTH_LONG ).show()
                    finish()
                }
            }

            override fun onFailure(call: Call<Equipment>, t: Throwable) {
                Toast.makeText(this@AddEquipmentActivity, t.message, Toast.LENGTH_LONG).show()
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
