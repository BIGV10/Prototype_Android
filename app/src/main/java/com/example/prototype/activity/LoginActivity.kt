package com.example.prototype.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.prototype.AuthHelper
import com.example.prototype.R
import com.example.prototype.ServiceBuilder
import com.example.prototype.model.UserLogin
import com.example.prototype.service.AuthService
import com.google.gson.JsonObject
import kotlinx.android.synthetic.main.activity_login.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.system.exitProcess


class LoginActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        buttonUserLogin.setOnClickListener {
            userLogin()
        }

        buttonAppExit.setOnClickListener {
            moveTaskToBack(true)
            exitProcess(-1)
        }


    }

    fun userLogin() {
        val authService =
            ServiceBuilder.buildService(AuthService::class.java)
        val json = JsonObject()
        json.addProperty("username", editTextLogin.text.toString())
        json.addProperty("password", editTextPassword.text.toString())
        val requestCall = authService.signIn(json)
        requestCall.enqueue(object : Callback<UserLogin> {
            override fun onResponse(call: Call<UserLogin>, response: Response<UserLogin>) {
                if (response.isSuccessful) {
                    var userGet = response.body()
//                    Старый способ записи токена
//                    val preferences: SharedPreferences =
//                        PreferenceManager.getDefaultSharedPreferences(this@LoginActivity)
//                    val editor = preferences.edit()
//                    editor.putString("tokenJWT", userGet!!.accessToken)
//                    editor.apply()
                    AuthHelper(this@LoginActivity).setIdToken(userGet!!)
                    editTextLogin.setText("")
                    editTextPassword.setText("")
                    val goToActivity = Intent(this@LoginActivity, MainActivity::class.java)
                    startActivity(goToActivity)
                } else { //Код статуса не в 200
                    if (response.code() == 404) {
                        Toast.makeText(
                            this@LoginActivity,
                            "Пользователь не найден\n" + response.code(),
                            Toast.LENGTH_LONG
                        ).show()
                    } else {
                        Toast.makeText(
                            this@LoginActivity,
                            "Не удалось получить информацию\n" + response.code() + "\n" + response.body(),
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }
            }

            override fun onFailure(call: Call<UserLogin>, t: Throwable) {
                Toast.makeText(this@LoginActivity, t.message, Toast.LENGTH_LONG).show()
            }
        })
    }
}
