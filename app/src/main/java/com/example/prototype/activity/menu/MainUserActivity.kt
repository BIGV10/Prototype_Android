package com.example.prototype.activity.menu

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.prototype.AuthHelper
import com.example.prototype.R
import com.example.prototype.activity.AddRequestActivity
import com.example.prototype.activity.LastRequestsActivity
import kotlinx.android.synthetic.main.activity_main_user.*

class MainUserActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_user)

        textUsername.setText("Пользователь: " + AuthHelper(this).getUsername())

        btn_add_request.setOnClickListener {
            val goToActivity = Intent(this, AddRequestActivity::class.java)
            startActivity(goToActivity)
        }

        btn_my_current_requests.setOnClickListener {
            val goToActivity = Intent(this, LastRequestsActivity::class.java)
            startActivity(goToActivity)
        }

        btn_exit.setOnClickListener {
            finish()
        }
    }
}
