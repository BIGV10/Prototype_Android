package com.example.prototype.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.prototype.AuthHelper
import com.example.prototype.R
import com.example.prototype.ServiceBuilder
import com.example.prototype.activity.menu.MainAdminActivity
import com.example.prototype.activity.menu.MainTechActivity
import com.example.prototype.activity.menu.MainUserActivity
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
                    val userGet = response.body()
//                    Старый способ записи токена
//                    val preferences: SharedPreferences =
//                        PreferenceManager.getDefaultSharedPreferences(this@LoginActivity)
//                    val editor = preferences.edit()
//                    editor.putString("tokenJWT", userGet!!.accessToken)
//                    editor.apply()
                    AuthHelper(this@LoginActivity).setIdToken(userGet!!)
                    val roles = AuthHelper(this@LoginActivity).getRoles()
                    val enabledUser = AuthHelper(this@LoginActivity).getEnabled()
                    if (enabledUser) {
                        activityByRole(roles!!)
                        editTextLogin.setText("")
                        editTextPassword.setText("")
                    } else {
                        Toast.makeText(
                            this@LoginActivity,
                            "Учетная запись данного пользователя отключена",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                } else { //Код статуса не в 200
                    when {
                        response.code() == 404 -> {
                            Toast.makeText(
                                this@LoginActivity,
                                "Пользователь не найден\n" + response.code(),
                                Toast.LENGTH_LONG
                            ).show()
                        }
                        response.code() == 400 -> {
                            Toast.makeText(
                                this@LoginActivity,
                                "Неверный логин\n" + response.body(),
                                Toast.LENGTH_LONG
                            ).show()
                        }
                        else -> {
                            Toast.makeText(
                                this@LoginActivity,
                                "Неожиданная ошибка\n" + response.code() + "\n" + response.body(),
                                Toast.LENGTH_LONG
                            ).show()
                        }
                    }
                }
            }

            override fun onFailure(call: Call<UserLogin>, t: Throwable) {
                Toast.makeText(this@LoginActivity, t.message, Toast.LENGTH_LONG).show()
            }
        })
    }

    fun activityByRole(roles: List<String>) {
        when {
            roles.any { it == "ROLE_ADMIN" || it == "ROLE_MODERATOR" } -> {
                val adminActivity = Intent(this@LoginActivity, MainAdminActivity::class.java)
                startActivity(adminActivity)
            }
            roles.any { it == "ROLE_TECHNICIAN" } -> {
                val techActivity = Intent(this@LoginActivity, MainTechActivity::class.java)
                startActivity(techActivity)
            }
            roles.any { it == "ROLE_USER" } -> {
                val userActivity = Intent(this@LoginActivity, MainUserActivity::class.java)
                startActivity(userActivity)
            }
            else -> {
                Toast.makeText(this@LoginActivity, "У пользователя нет ролей", Toast.LENGTH_LONG)
                    .show()
            }
        }
    }
}
