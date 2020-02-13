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

        add_request_btn.setOnClickListener {
            val intentGoToActivity2 = Intent(this, AddRequestActivity::class.java)
            startActivity(intentGoToActivity2)
        }

        current_request_btn.setOnClickListener {
            val intentGoToActivity2 = Intent(this, LastRequestsActivity::class.java)
            startActivity(intentGoToActivity2)
        }


        exit_btn.setOnClickListener {
            moveTaskToBack(true)
            exitProcess(-1)
        }
    }

}
