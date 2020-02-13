package com.example.prototype.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.prototype.R
import kotlinx.android.synthetic.main.activity_main.*
import kotlin.system.exitProcess

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btn_Add_request.setOnClickListener {
            val goToActivity = Intent(this, AddRequestActivity::class.java)
            startActivity(goToActivity)
        }

        btn_Current_request.setOnClickListener {
            val goToActivity = Intent(this, LastRequestsActivity::class.java)
            startActivity(goToActivity)
        }

        btn_Hidden.setOnClickListener {
            val goToActivity = Intent(this, AddEquipmentActivity::class.java)
            startActivity(goToActivity)
        }


        btn_exit.setOnClickListener {
            moveTaskToBack(true)
            exitProcess(-1)
        }
    }

}
