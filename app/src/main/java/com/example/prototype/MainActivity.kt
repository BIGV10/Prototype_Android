package com.example.prototype

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        add_request_btn.setOnClickListener {
            val intentGoToActivity2 = Intent(this, add_request_activity::class.java)
            startActivity(intentGoToActivity2)
        }
    }

}
